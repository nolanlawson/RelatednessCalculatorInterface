package com.nolanlawson.relatedness.ui;

import java.util.regex.Pattern;

/**
 * Utils for fixing user "errors" in their input.  I'm keeping this in the frontend because I think it's out
 * of the scope of the backend app.  The backend app assumes certain formatting in the input
 * (no plurals, no "my" or "your"), etc., whereas the frontend is more open-ended.  
 * 
 * @author nolan
 *
 */
public class QueryUtils {
	
	// I assume people will be tempted to add "my", "your", etc.
	static final Pattern DETERMINER = ~/^(?:my|your|the)\s+/
	
	// I assume people will be tempted to enter reciprocal relations as plurals, e.g.
	// "cousins" instead of "cousin"
	static final Pattern RECIPROCAL_PLURALS = ~/(cousin|twin|brother|sister)s/
	
	static final Pattern SPACES = ~/\s+/
	
	public static cleanQuery(query) {
		query = query ?: "";
		query = query.trim().replace('+', ' ').toLowerCase()
		
		query = SPACES.matcher(query).replaceAll(' ');
		
		query = RECIPROCAL_PLURALS.matcher(query).replaceAll('\$1')
		
		return DETERMINER.matcher(query).replaceAll('')
	}
}
