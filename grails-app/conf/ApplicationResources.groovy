modules = {
    application {
	defaultBundle 'core'
        resource url:'js/application.js'
    }
	
	drawcanviz {
		defaultBundle 'canviz'
		resource url:'js/drawcanviz.js'
	}
	
	canviz {
		defaultBundle 'canviz'
		dependsOn 'excanvas,path,prototype'
		resource url:'js/canviz/canviz.js'
		resource url:'js/canviz/brewercolors.js'
	}
	
	excanvas {
		defaultBundle 'canviz'
		resource url:'js/canviz/excanvas/excanvas.js'
	}
	
	path {
		defaultBundle 'canviz'
		resource url:'js/canviz/path/path.js'
	}
	
	prototype {
		defaultBundle 'canviz'
		resource url:'js/canviz/prototype/prototype.js'
	}
}
