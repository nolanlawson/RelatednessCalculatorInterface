package com.nolanlawson.relatedness.ui

class CalculatorController {

	def calculatorService
	
    def index() { 
	
		if (!params.q) {
			return []
		} else {
			return [result : calculatorService.calculate(params.q) ]
		}
	}
	
}
