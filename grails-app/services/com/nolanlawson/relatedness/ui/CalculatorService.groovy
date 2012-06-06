package com.nolanlawson.relatedness.ui

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit

import com.google.common.base.Function
import com.google.common.collect.MapMaker
import com.nolanlawson.relatedness.parser.RelativeNameParser
import com.nolanlawson.relatedness.RelatednessCalculator
import com.nolanlawson.relatedness.RelationAndGraph

class CalculatorService {

	/**
	 * Use a super small in-memory soft reference map to hold the graph data
	 */
	def graphCache = new MapMaker()
       .concurrencyLevel(16)
       .softValues()
       .maximumSize(1000)
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
	def threadPool = Executors.newFixedThreadPool(32);
	
	  // return everything but the graph
    def calculate(String query) {
		def result = graphCache.get(query);
		println result;
		return [
			failed : result.failed,
			errorMessage : result.errorMessage,
			coefficient : result.coefficient,
			degree : result.degree
			];
    }

	// return just the graph	
	def generateGraph(String query) {
		def result =  graphCache.get(query)
		println result
		return result.graph;
	}
	
	def cacheReport() {
		return graphCache.toString();
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
		
		// calculate the Relatedness from the Relation
		def relatedness = RelatednessCalculator.calculate(relationAndGraph.relation);
		
		return new RelatednessResult(
				graph: graph,
				coefficient : relatedness.coefficient,
				degree : relatedness.averageDegree);
		
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