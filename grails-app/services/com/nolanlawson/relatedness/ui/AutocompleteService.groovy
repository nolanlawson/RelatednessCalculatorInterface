package com.nolanlawson.relatedness.ui

import com.nolanlawson.relatedness.autosuggest.RelationSuggester

class AutocompleteService {

	static final int MAX_NUM_SUGGESTIONS = 10;
        static transactional = false;
	
	RelationSuggester relationSuggester =  new RelationSuggester();
	
	
	
    def suggest(String input) {

		return relationSuggester.suggest(input, MAX_NUM_SUGGESTIONS)
		
    }
}
