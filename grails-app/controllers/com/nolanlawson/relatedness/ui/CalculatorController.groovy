package com.nolanlawson.relatedness.ui

import java.util.TreeMap

import com.nolanlawson.relatedness.parser.RelativeNameParser;

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
	
	/**
	 * 
	 * Call the Relatedness Calculator library and ask it to generate a DOT graph for the given query.
	 * @return
	 */
	def generateGraph() {
		def graph = RelativeNameParser.parseGraph(params.q.replace('+', ' '))
		
		// the raw text must be converted by xdot itself into a nicer format
		def rawText = graph.drawGraph();
		
		File tempFile = File.createTempFile(Long.toHexString(new Random().nextLong()),".txt")
		tempFile.deleteOnExit();
		tempFile.write(rawText)
		
		// generate xdot format
		def process = "dot -Txdot $tempFile.absolutePath".execute()
		process.waitFor()
		
		def text = process.in.text;
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
