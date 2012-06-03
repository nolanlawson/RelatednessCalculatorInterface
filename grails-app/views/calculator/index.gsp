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
	margin-bottom: 0.3em;
}

#sidebar a:link,a:visited,a:hover {
	color: #000000
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

#relatedness-explanation-content {
	margin: 2em 0em 0em 1em;
}

#relatedness-explanation-content ul {
	list-style-type: none;
	margin-bottom: 0.6em;
	margin-left: 20em;
	padding: 0;
}

#relatedness-explanation-content li {
	line-height: 1.3;
	margin-bottom: 0.3em;
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
			<g:each in="${exampleRelations}">
				<li><a href="?q=${it.key}"> ${it.key} (${it.value})
				</a></li>
			</g:each>
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
		<div class="fieldcontain">
			<g:form action="index" method="GET">
				<br />
				<g:textField name="q" value="${params.q}" />
				<g:submitButton name="Calculate" id="go" />
			</g:form>
		</div>
		<div id="result-display">
			<span id="result"> <g:if test="${result}">
					<g:if test="${result.failed}">
						Nothing found for <strong> ${params.q}
						</strong>
					</g:if>
					<g:else>
						Result for <strong> ${params.q}
						</strong>
						<br />Relatedness coefficient: ${result.coefficient}
						<br />Degree of relation: ${result.degree}
					</g:else>
				</g:if>
			</span>
		</div>
	</div>
	<g:if test="${!result?.failed}">
		<div id="relatedness-explanation" role="main">
			<h2>Explanation:</h2>
			<div id="relatedness-explanation-content">
				<p>The relatedness between two people is calculated by finding the <em>common ancestors</em>
				they share.  Once you've found the common ancestors, this gives you two measurements:</p>
				<ul>
					<li><b>Degree of relation:</b> how far you are from that person in your family tree.</li>
					<li><b>Relatedness coefficient:</b> what percentage of your genes you share.</li>
				</ul>
			</div>
		</div>
	</g:if>
</body>
</html>
