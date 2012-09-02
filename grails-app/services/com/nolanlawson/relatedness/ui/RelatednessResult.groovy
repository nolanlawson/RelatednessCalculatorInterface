package com.nolanlawson.relatedness.ui;

import java.util.List;

public class RelatednessResult {

	boolean failed;
	String parseError;
	String errorMessage;
	List<String> alternateQueries;
	int degree;
	double coefficient;
	String graph;
	int graphWidth;
	
	/**
	 * Creates a new instance of this RelatednessResult without the graph.  Usually we don't need to send that
	 * to the client unless we're drawing it.
	 */
	def cloneWithoutGraph() {
		def newProperties = properties.clone();
		newProperties.remove("class")
		newProperties.remove("graph")
		return RelatednessResult.newInstance(newProperties)
	}
	
}
