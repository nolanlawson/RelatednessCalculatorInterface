package com.nolanlawson.relatedness.ui

import java.util.concurrent.TimeUnit

import com.google.common.base.Function
import com.google.common.collect.MapMaker
import com.nolanlawson.relatedness.Relatedness
import com.nolanlawson.relatedness.RelatednessCalculator;
import com.nolanlawson.relatedness.Relation;
import com.nolanlawson.relatedness.UnknownRelationException;
import com.nolanlawson.relatedness.parser.RelativeNameParser
import com.sun.corba.se.impl.orbutil.graph.Graph

class CalculatorService {

	/**
	 * Use a super small in-memory soft reference map to hold the graph data
	 */
	def cache = new MapMaker()
       .concurrencyLevel(16)
       .softValues()
       .maximumSize(1000)
       .expireAfterAccess(60, TimeUnit.MINUTES)
       .makeComputingMap(
           new Function<String, String>() {
             public String apply(String key) {
               return generateGraphWithoutCaching(key);
             }
           });
	
	/**
	 * Call the JAR library and pass in the query string unmodified
	 * @param query
	 * @return
	 */
    def calculate(String query) {
		
		try {
			Relation relation = RelativeNameParser.parse(query)
			
			Relatedness relatedness = RelatednessCalculator.calculate(relation)
			
			return new RelatednessResult(
				degree: relatedness.averageDegree, 
				coefficient: relatedness.coefficient)
		} catch (UnknownRelationException e) {
			log.warn("Unknown relation",e)
			return new RelatednessResult(
				failed : true,
				errorMessage : e.message)
		}
    }
	
	def generateGraph(String query) {
		return cache.get(query.toLowerCase().trim())
	}
	
	def cacheReport() {
		return cache.toString();
	}
	
	/**
	 * Draw a graph for the given query
	 */
	def generateGraphWithoutCaching(String query) {
		
		def graph = RelativeNameParser.parseGraph(query)
		
		// the raw text must be converted by xdot itself into a nicer format
		def rawText = graph.drawGraph();
		
		File tempFile = File.createTempFile(Long.toHexString(new Random().nextLong()),".txt")
		tempFile.deleteOnExit();
		tempFile.write(rawText)
		
		// generate xdot format
		def process = "dot -Txdot $tempFile.absolutePath".execute()
		process.waitFor()
		
		return process.in.text;
	}
}
