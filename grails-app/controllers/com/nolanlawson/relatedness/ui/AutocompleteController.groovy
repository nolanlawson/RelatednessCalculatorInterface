package com.nolanlawson.relatedness.ui

import java.util.concurrent.TimeUnit

class AutocompleteController {

	def autocompleteService
	
    def index() {
        cache shared:true, validFor: TimeUnit.DAYS.toSeconds(1) // cache for a day

		def query = params.query?:params.q;
		
		def suggestions = (!query || query == '') ?
			[] :
			autocompleteService.suggest(QueryUtils.cleanQuery(query));
		
		render (contentType: "text/xml") {
			
			results() {
				suggestions.each { suggestion ->
					result(){
						name(suggestion)
					}
			    }
		    }
		};
	}
}
