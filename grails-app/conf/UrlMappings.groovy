class UrlMappings {

	static mappings = {

		"/"(controller:"calculator")
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		
		"500"(view:'/error')
	}
}
