package com.nolanlawson.relatedness.ui

class AutocompleteController {

	def autocompleteService
	
    def index() { 
		
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
