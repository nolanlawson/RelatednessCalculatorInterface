package com.nolanlawson.relatedness.ui

class CalculatorService {

	/**
	 * Call the JAR library and pass in the query string unmodified
	 * @param query
	 * @return
	 */
    def calculate(String query) {
		def relation = RelativeNameParser.parse(query);
    }
}
