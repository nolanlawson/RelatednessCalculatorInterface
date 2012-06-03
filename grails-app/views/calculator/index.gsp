<!doctype html>
<html>
<head>
<meta name="layout" content="main" />
<title>Relatedness Calculator</title>
<style type="text/css" media="screen">
#sidebar {
	background-color: #eee;
	border: .2em solid #fff;
	margin: 2em 2em 1em;
	padding: 1em;
	width: 12em;
	float: left;
	-moz-box-shadow: 0px 0px 1.25em #ccc;
	-webkit-box-shadow: 0px 0px 1.25em #ccc;
	box-shadow: 0px 0px 1.25em #ccc;
	-moz-border-radius: 0.6em;
	-webkit-border-radius: 0.6em;
	border-radius: 0.6em;
}

.ie6 #sidebar {
	display: inline;
	/* float double margin fix http://www.positioniseverything.net/explorer/doubled-margin.html */
}

#sidebar ul {
	font-size: 0.9em;
	list-style-type: none;
	margin-bottom: 0.6em;
	padding: 0;
}

#sidebar li {
	line-height: 1.3;
}

#sidebar h1 {
	text-transform: uppercase;
	font-size: 1.1em;
	margin: 0 0 0.3em;
}

#page-body {
	margin: 2em 1em 1.25em 18em;
}

h2 {
	margin-top: 1em;
	margin-bottom: 0.3em;
	font-size: 1em;
}

p {
	line-height: 1.5;
	margin: 0.25em 0;
}

#calculator-display {
	margin: 2em 1em 1.25em 18em;
}

#result-display {
	margin: 2em 0em 0em 1em;
}

@media screen and (max-width: 480px) {
	#sidebar {
		display: none;
	}
	#page-body {
		margin: 0 1em 1em;
	}
	#page-body h1 {
		margin-top: 0;
	}
}
</style>
</head>
<body>
	<a href="#page-body" class="skip"><g:message
			code="default.link.skip.label" default="Skip to content&hellip;" /></a>
	<div id="sidebar" role="complementary">
		<h1>Examples</h1>
		<ul>
			<li>Sister</li>
			<li>Father</li>
			<li>Grandmother</li>
			<li>Cousin</li>
			<li>Uncle</li>
			<li>Second cousin</li>
			<li>Niece</li>
		</ul>
	</div>
	<div id="page-body" role="main">
		<h1>Welcome to the Relatedness Calculator</h1>
		<p>
			Wondering how likely your nephew is to "take after" you? Or if would
			be weird to date that cute second cousin you met at the last family
			reunion? Look no further than the <strong>relatedness
				calculator</strong>.
		</p>
		<p>To use the calculator, enter the name of a relative using plain
			English. For instance, you can type "sister," "dad's cousin," or
			"grandpa's cousin's daughter."</p>
	</div>
	<div id="calculator-display" role="main">
		<h2>Enter relative:</h2>
		<g:form action="index" method="GET">
		<g:textField name="q" value="${params.q}"/>
		<g:submitButton name="Calculate!" />
		</g:form>
		<div id="result-display">
			<span id="result">${result}</span>
		</div>
	</div>
</body>
</html>
