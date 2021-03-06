package com.nolanlawson.relatedness.ui

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit

import javax.annotation.PreDestroy;

import com.google.common.base.Function
import com.google.common.collect.MapMaker
import com.nolanlawson.relatedness.parser.RelativeNameParser
import com.nolanlawson.relatedness.RelatednessCalculator
import com.nolanlawson.relatedness.parser.ParseError
import com.nolanlawson.relatedness.parser.RelationParseResult

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
	   
	/**
	 * Use a small fixed-size Java thread pool for input/output on the remote 'dot' process.
	 */
	def threadPool = Executors.newFixedThreadPool(8);
	
	@PreDestroy
	public void cleanUp() throws Exception {
		// to be called when the webapp is shut down, so that we can clean up the threads
		log.info("shutting down threadpool...");
		threadPool.shutdown();
	}
	
	// return everything but the graph
    def calculate(String query) {
		def result = graphCache.get(query.trim().toLowerCase());
		return result.cloneWithoutGraph();
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
		RelationParseResult relationParseResult;
		try {
			relationParseResult = RelativeNameParser.parse(query, true)
		} catch (Exception e) { // relation exception
			return new RelatednessResult(
				failed : true,
				errorMessage : e.message);
		}
		
		// if there is ambiguity, give the user a chance to recover
		if (relationParseResult.parseError == ParseError.Ambiguity) {
			System.out.println("ambiguous!!");
			return new RelatednessResult(
				failed : true,
				parseError : relationParseResult.parseError.toString(),
				alternateQueries : relationParseResult.ambiguityResolutions
				)
		} else if (relationParseResult.parseError == ParseError.StepRelation) {
			return new RelatednessResult(
				failed : true,
				parseError :  relationParseResult.parseError.toString(),
				)
		}
		
		// convert to xdot format
		def graph = convertGraphToXdotFormat(relationParseResult.graph.drawGraph());
		
		// find the pixel width
		def graphWidth = Double.parseDouble((graph =~ ~/b="0,0,([0-9.]+),/)[0][1])
		
		// calculate the Relatedness from the Relation
		def relatedness = RelatednessCalculator.calculate(relationParseResult.relation);
		
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
	
}