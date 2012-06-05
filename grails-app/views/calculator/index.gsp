<!doctype html>
<html>
<head>
<meta name="layout" content="main" />
<title>Relatedness Calculator</title>
<r:require modules="canviz,path,prototype,excanvas" />
<r:script>
// TODO: there is probably a grails-ier way of getting the URL params from javascript...
function getURLParameter(name) {
    return decodeURI(
        (RegExp(name + '=' + '(.+?)(&|$)').exec(location.search)||[,null])[1]
    );
}
function drawCanviz(query) {
	var canviz = new Canviz('graph_container');
	canviz.setImagePath('graphs/images/');
	canviz.setScale(0.9);
	canviz.load("generateGraph?q=" + encodeURIComponent(getURLParameter('q')));
}

</r:script>

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

#relatedness-explanation {
	margin: 2em 1em 1.25em 18em;
}

#relatedness-explanation-content {
	margin: 2em 0em 0em 1em;
}

#relatedness-explanation-content ul {
	list-style-type: none;
	margin: 2em 4em 2em 4em;
	padding: 0;
}

#relatedness-explanation-content li {
	line-height: 1.3;
	margin-bottom: 0.3em;
}

#quoted-text {
	margin: 2em 4em 2em 4em;
	font-size: 0.9em;
}

#relation-input {
	width: 20em;	
}

#fun-facts {
	margin: 2em 1em 1.25em 18em;
}

#fun-facts ul {
	list-style-type: none;
	margin: 2em 4em 2em 4em;
	padding: 0;
}

#fun-facts li {
	line-height: 1.3;
	margin-bottom: 0.7em;
}

#graph_container_outer {
	margin: auto;
	overflow: auto;
	overflow-y: hidden;
	padding-bottom:1em;
	padding-top:1em;
}

#graph_container {
	margin: auto	
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
				<li>
					<g:link params="[q: it.key, example: true]">
						${it.key} (${it.value})
					</g:link>
				</li>
			</g:each>
		</ul>
	</div>
	<div id="page-body" role="main">
		<h1>Welcome to the Relatedness Calculator</h1>
		<p>
			Wondering how likely your nephew is to "take after" you? Or if it would
			be weird to date that cute second cousin you met at the family
			reunion? Ask the <strong>relatedness
				calculator</strong>.
		</p>
		<p>To use the calculator, enter the name of a relative in plain
			English. For instance, you can type "sister," "dad's cousin," or
			"grandpa's cousin's daughter."</p>
	</div>
	<div id="calculator-display" role="main">
		<h2>Enter relative:</h2>
		<div class="fieldcontain">
			<g:form action="index" method="GET">
				<br />
				<g:if test="${params.example}">
					<g:textField id="relation-input" name="q" value=""/>
				</g:if>
				<g:else>
					<input id="relation-input" name="q" value="${params.q}"/>
				</g:else>
				<input type="submit" value="Calculate" />
			</g:form>
		</div>
		<div id="result-display">
			<span id="result"> <g:if test="${result}">
					<g:if test="${result.failed}">
						Nothing found for <strong> ${params.q}</strong>
						<br/>Error: ${result.errorMessage}
					</g:if>
					<g:else>
						Result for <strong> ${params.q}
						</strong>
						<br />Relatedness coefficient: ${result.coefficient}
						<br />Degree of relation: ${result.degree}
						<br/><div id="graph_container_outer"><div id="graph_container"></div></div>
						<div id="debug_output"></div>
						<r:script>
							drawCanviz();
						</r:script>
					</g:else>
				</g:if>
			</span>
		</div>
	</div>
	<g:if test="${result && !result.failed}">
		<div id="relatedness-explanation" role="main">
			<h2>Explanation:</h2>
			<div id="relatedness-explanation-content">
				<p>
					The relatedness between two people is calculated by finding the <em>common
						ancestors</em> they share. Once you've found the common ancestors, this
					gives you two measures:
				</p>
				<ul>
					<li><b>Relatedness coefficient:</b> what percentage of your
						genes you share.</li>
					<li><b>Degree of relation:</b> how far you are from that
						person in your family tree.</li>

				</ul>
				Richard Dawkins gives a great explanation of this calculation in <i>The
					Selfish Gene</i>:

				<div id="quoted-text">

					<p>First identify all the common ancestors of A and B. For
						instance, the common ancestors of a pair of first cousins are
						their shared grandfather and grandmother. Once you have found a
						common ancestor, it is of course logically true that all his
						ancestors are common to A and B as well. However, we ignore all
						but the most recent common ancestors. In this sense, first cousins
						have only two common ancestors. If B is a lineal descendant of A,
						for instance his great grandson, then A himself is the ‘common
						ancestor’ we are looking for.</p>
					<p>Having located the common ancestor(s) of A and B, count the
						generation distance as follows. Starting at A, climb up the family
						tree until you hit a common ancestor, and then climb down again to
						B. The total number of steps up the tree and then down again is
						the generation distance. For instance, if A is B’s uncle, the
						generation distance is 3. The common ancestor is A’s father (say)
						and B’s grandfather. Starting at A you have to climb up one
						generation in order to hit the common ancestor. Then to get down
						to B you have to descend two generations on the other side.
						Therefore the generation distance is 1 + 2 = 3.</p>
					<p>Having found the generation distance between A and B via a
						particular common ancestor, calculate that part of their
						relatedness for which that ancestor is responsible. To do this,
						multiply 1/2 by itself once for each step of the generation
						distance. If the generation distance is 3, this means calculate
						1/2 x 1/2 x 1/2 or (1/2)^3. If the generation distance via a
						particular ancestor is equal to g steps, the portion of
						relatedness due to that ancestor is (1/2)^g.</p>
					<p>But this is only part of the relatedness between A and B. If
						they have more than one common ancestor we have to add on the
						equivalent figure for each ancestor. It is usually the case that
						the generation distance is the same for all common ancestors of a
						pair of individuals. Therefore, having worked out the relatedness
						between A and B due to any one of the ancestors, all you have to
						do in practice is to multiply by the number of ancestors. First
						cousins, for instance, have two common ancestors, and the
						generation distance via each one is 4. Therefore their relatedness
						is 2 x (1/2)^4 = 1/8. If A is B’s great-grandchild, the generation
						distance is 3 and the number of common ‘ancestors’ is 1 (B
						himself), so the relatedness is 1 x (1/2)^3 = 1/8.</p>
				</div>
			</div>
		</div>
		<div id="fun-facts" role="main">
				<h2>Fun Facts:</h2>
				<ul>
				<li>The curious case of <g:link params='[q:"double cousin"]'>double cousins</g:link>
				occurs when two siblings from one family each marry two siblings from another family. Double cousins
				share 25% of their genes &mdash; the same as grandparents/grandchildren and half-siblings. No incest
				involved, but it's still kind of weird!</li>
				<li>Although it is illegal in many places,
				<a href='http://www.nytimes.com/2009/11/26/garden/26cousins.html'>over 10% of marriages worldwide</a>
				 are between cousins.  Famous cousin-marriers include 
				<a href='http://en.wikipedia.org/wiki/Charles_Darwin'>Charles Darwin</a>, 
				<a href='http://en.wikipedia.org/wiki/Jerry_Lee_Lewis'>Jerry Lee Lewis</a>, 
				and <a href='http://www.imdb.com/title/tt0757018/quotes?qt0285958'>Shelbyville Manhattan</a>.
				</li>
				<li>
				In terms of relatedness, you are just as likely to "take after" your uncle (0.25) as
				your grandfather (0.25).
				</li>
				<li>
				Identical twins share 100% of their genes, but fraternal twins share the same as regular siblings
				(50%).  So you're as closely related to your identical twin's children (0.5) 
				as to your own children (0.5)! 
				</li>
				<li>
				If there has already been incest in your family, you can calculate your relatedness coefficient to
				other family members
				by adding in any extra coefficients due to the incest.  For instance, if your parents
				are cousins, then your relatedness to your father is 0.5625, because he's your 
				<g:link params="[q: 'father', example: true]">father</g:link> (0.5), as
				well as your <g:link url="?q=mother's cousin&example=true">mother's cousin</g:link> (0.0625).
				And your relatedness to your sister would be 0.5625, because she's simultaneously your 
				<g:link params="[q: 'sister', example: true]">sister</g:link> (0.5), your 
				<g:link url="?q=father's cousin's daughter&example=true">father's cousin's daughter</g:link>
				(0.03125), and your 
				<g:link url="?q=mother's cousin's daughter&example=true">mother's cousin's daughter</g:link> (0.03125).
				</li>
				<li>
				Despite what you may have seen on <a href='http://en.wikipedia.org/wiki/Futurama'>Futurama</a>, it is not possible for
			    <a href='http://en.wikipedia.org/wiki/Fry_(Futurama)'>Fry</a> to be his own grandfather.
			    He would need to share 125% of his genes with himself.
				</li>				
				</ul>
		</div>
	</g:if>
</body>
</html>
