package com.nolanlawson.relatedness.ui;

public class RelatednessResult {

	boolean failed;
	String errorMessage;
	int degree;
	double coefficient;
	String graph;
	
	@Override
	public String toString() {
		return "RelatednessResult [failed=" + failed + ", errorMessage="
				+ errorMessage + ", degree=" + degree + ", coefficient="
				+ coefficient + ", graph=" + graph + "]";
	}
}
