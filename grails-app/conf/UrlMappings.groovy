class UrlMappings {

	static mappings = {
		"/generateGraph"(controller:"calculator",action:"generateGraph")
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}
		"/"(controller:"calculator")

		
		
		"500"(view:'/error')
	}
}
