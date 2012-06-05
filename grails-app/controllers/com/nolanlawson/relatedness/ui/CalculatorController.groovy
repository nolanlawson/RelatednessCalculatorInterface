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
	
	def version() {
		render text: grailsApplication.metadata['app.version'], contentType: 'text/plain'
	}
	
	/**
	 * ShowInformation on the graph cache
	 * @return
	 */
	def cacheReport() {
		render text : calculatorService.cacheReport(), contentType: 'text/plain'
	}
	
	/**
	 * 
	 * Call the Relatedness Calculator library and ask it to generate a DOT graph for the given query.
	 * @return
	 */
	def generateGraph() {
		String text = calculatorService.generateGraph(params.q.replace('+',' '))
		render text: text, contentType: 'text/plain', template: null
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
