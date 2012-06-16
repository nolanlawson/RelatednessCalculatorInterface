// assorted javascript functions for interacting with canviz

function drawCanviz(actualGraphWidth, escapedQuery) {
	var canviz = new Canviz('graph_container');
	// fudge factor of 40 for margins on both side (which should only be 8, but whatever)
	var canvasWidth = document.getElementById('page-body').offsetWidth - (40);
	// canviz automatically scales everything to 96/72
	var graphWidth = actualGraphWidth * 96 / 72;
	// use 0.9 as a nice default graph size
	var scale = graphWidth > canvasWidth ? canvasWidth / graphWidth : 0.9;
	canviz.setScale(scale);
	canviz.load("generateGraph?q=" + escapedQuery);
}