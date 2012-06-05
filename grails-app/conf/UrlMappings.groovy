class UrlMappings {

	static mappings = {
		
		"/$action"(controller:"calculator")
		
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}
		"/"(controller:"calculator")

		
		
		"500"(view:'/error')
	}
}
