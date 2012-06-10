package com.nolanlawson.relatedness.ui

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit

import javax.annotation.PreDestroy;

import com.google.common.base.Function
import com.google.common.collect.MapMaker
import com.nolanlawson.relatedness.parser.RelativeNameParser
import com.nolanlawson.relatedness.RelatednessCalculator
import com.nolanlawson.relatedness.RelationAndGraph

class CalculatorService {

	static transactional = false;
	
	/**
	 * Use a super small in-memory soft reference map to hold the graph data
	 */
	def graphCache = new MapMaker()
       .concurrencyLevel(16)
       .softValues()
       .maximumSize(100)
       .expireAfterAccess(60, TimeUnit.MINUTES)
       .makeComputingMap(
           new Function<String,Object> () {
             public Object apply(String key) {
               return generateResultWithoutCaching(key);
             }
           });
	   
	// example relations shown in the sidebar
	def exampleRelations = [
		   'Sister',
		   'Father',
		   'Grandmother',
		   'Brother',
		   'Cousin',
		   'Second Cousin',
		   'Mother',
		   'Grandfather',
		   'Great-grandfather',
		   'Great-grandmother',
		   'Uncle',
		   'Aunt',
		   'Nephew',
		   'Niece',
		   'Son',
		   'Daughter',
		   'Grandson',
		   'Granddaughter',
		   'Great-grandson',
		   'Great-granddaughter',
		   'Half-brother',
		   'Half-sister']
	
	// cache of the example relations
	def exampleRelationMappings
	   
	/**
	 * Use a small fixed-size Java thread pool for input/output on the remote 'dot' process.
	 */
	def threadPool = Executors.newFixedThreadPool(16);
	
	@PreDestroy
	public void cleanUp() throws Exception {
		// to be called when the webapp is shut down, so that we can clean up the threads
		log.info("shutting down threadpool...");
		threadPool.shutdown();
	}
	
	// return everything but the graph
    def calculate(String query) {
		def result = graphCache.get(query.trim().toLowerCase());
		return [
			failed : result.failed,
			errorMessage : result.errorMessage,
			coefficient : result.coefficient,
			degree : result.degree,
			graphWidth : result.graphWidth
			];
    }

	// return just the graph	
	def generateGraph(String query) {
		graphCache.get(query.trim().toLowerCase()).graph
	}
	
	def cacheReport() {
		"size: $graphCache.size $graphCache"
	}
	
	/**
	 * Draw a graph and calculate for the given query
	 */
	def generateResultWithoutCaching(String query) {
		RelationAndGraph relationAndGraph;
		try {
			relationAndGraph = RelativeNameParser.parse(query, true)
		} catch (Exception e) { // relation exception
			return new RelatednessResult(
				failed : true,
				errorMessage : e.message);
		}
		
		// convert to xdot format
		def graph = convertGraphToXdotFormat(relationAndGraph.graph.drawGraph());
		
		// find the pixel width
		def graphWidth = Integer.parseInt((graph =~ ~/b="0,0,(\d+),/)[0][1])
		
		// calculate the Relatedness from the Relation
		def relatedness = RelatednessCalculator.calculate(relationAndGraph.relation);
		
		return new RelatednessResult(
				graph: graph,
				coefficient : relatedness.coefficient,
				degree : relatedness.averageDegree,
				graphWidth : graphWidth);
		
	}
	
	// the raw text must be converted by xdot itself into a nicer format
	def convertGraphToXdotFormat(rawText) {
		
		// write to process in separate thread
		def process = "dot -Txdot".execute()
		threadPool.submit({
			process.out << rawText
			process.out.close()
		});
	
		process.waitFor()
		
		return process.in.text;
	}
	
	private createExampleRelationMappings() {
		
		if (!exampleRelationMappings) {
			exampleRelationMappings = new TreeMap<String,Double>()
			exampleRelations.each(){ relation ->
				exampleRelationMappings[relation] = calculate(relation).coefficient
			}
		}
		return exampleRelationMappings
	}
}