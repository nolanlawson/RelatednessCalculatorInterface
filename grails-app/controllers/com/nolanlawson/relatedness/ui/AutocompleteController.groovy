package com.nolanlawson.relatedness.ui

import grails.converters.JSON

import java.util.concurrent.TimeUnit

class AutocompleteController {

	def autocompleteService
	
    def index() {
        cache shared:true, validFor: TimeUnit.DAYS.toSeconds(1) // cache for a day

		def query = params.query?:params.term?:params.q;
		
		def suggestions = (!query || query == '') ?
			[] :
			autocompleteService.suggest(QueryUtils.cleanQuery(query));
		
		render suggestions as JSON;
	}
}
