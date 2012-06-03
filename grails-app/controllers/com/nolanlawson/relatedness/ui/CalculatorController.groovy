package com.nolanlawson.relatedness.ui

import java.util.TreeMap

class CalculatorController {

	def calculatorService
	
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
	
	def exampleRelationMappings
	
    def index() { 
	
		if (!params.q) {
			return [exampleRelations: createExampleRelationMappings()]
		} else {
			return [exampleRelations: createExampleRelationMappings(), 
				result : calculatorService.calculate(params.q) ]
		}
	}
	
	private createExampleRelationMappings() {
		
		if (!exampleRelationMappings) {
		
			exampleRelationMappings = new TreeMap<String,Double>()
			exampleRelations.each(){ relation ->
				exampleRelationMappings[relation] = calculatorService.calculate(relation).coefficient
			}
		}
		return exampleRelationMappings
	}
	
}
