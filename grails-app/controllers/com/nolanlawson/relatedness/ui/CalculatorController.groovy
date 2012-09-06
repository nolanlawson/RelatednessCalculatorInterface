package com.nolanlawson.relatedness.ui

import grails.converters.*;

import groovy.json.StringEscapeUtils;

import java.util.TreeMap

import org.springframework.web.context.request.RequestContextHolder



class CalculatorController {

	def calculatorService
	
    def index() { 
	
		logUserQuery();
		
		if (!params.q) {
			return [exampleRelations: calculatorService.createExampleRelationMappings()]
		} else {
			return [exampleRelations: calculatorService.createExampleRelationMappings(), 
				result : calculatorService.calculate(QueryUtils.cleanQuery(params.q)),
				escapedQuery : URLEncoder.encode(params.q,'UTF-8') ]
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
		String text = calculatorService.generateGraph(QueryUtils.cleanQuery(params.q)) ?: 'error'
		render text: text, contentType: 'text/plain', template: null
	}
	
	def logUserQuery() {
		// log the query just in case I want to grep through it later
		def info = [
			remoteAddr : request.getRemoteAddr(),
			forwardedFor : request.getHeader("X-Forwarded-For"),
			userAgent : request.getHeader("User-Agent"),
			clientIp : request.getHeader("Client-IP"),
			sessionId : RequestContextHolder.getRequestAttributes()?.getSessionId(),
			referer : request.getHeader('referer'),
			'params' : params
		]
		log.info(info as JSON)
	}
	
}
