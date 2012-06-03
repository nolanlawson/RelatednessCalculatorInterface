package com.nolanlawson.relatedness.ui

import com.nolanlawson.relatedness.Relatedness
import com.nolanlawson.relatedness.RelatednessCalculator;
import com.nolanlawson.relatedness.Relation;
import com.nolanlawson.relatedness.UnknownRelationException;
import com.nolanlawson.relatedness.parser.RelativeNameParser

class CalculatorService {

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
}
