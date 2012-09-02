modules = {
    application {
        resource url:'js/application.js'
    }
	
	drawcanviz {
		resource url:'js/drawcanviz.js'
	}
	
	canviz {
		dependsOn 'excanvas,path,prototype'
		resource url:'js/canviz/canviz.js'
		resource url:'js/canviz/brewercolors.js'
	}
	
	excanvas {
		resource url:'js/canviz/excanvas/excanvas.js'
	}
	
	path {
		resource url:'js/canviz/path/path.js'
	}
	
	prototype {
		resource url:'js/canviz/prototype/prototype.js'
	}
}