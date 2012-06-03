package com.nolanlawson.relatedness.ui

class CalculatorController {

    def index() { 
	
		if (!params.q) {
			return [];
		} else {
			return [result : "myFunResult"]
		}
		
	}
	
}
