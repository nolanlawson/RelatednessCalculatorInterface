package com.nolanlawson.relatedness.ui

class AutocompleteController {

	def autocompleteService
	
    def index() { 
		
		def suggestions = autocompleteService.suggest(params.query?:params.q);
		
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
