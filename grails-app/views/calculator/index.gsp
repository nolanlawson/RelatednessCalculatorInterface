<!doctype html>
<html>
<head>
<meta name="layout" content="main" />
<title>
<g:if test="${result && !result.failed}">${params.q} - </g:if>Relatedness Calculator
</title>

<resource:include components="autoComplete" autoComplete="[skin: 'default']" />
</head>
<body>
	<a href="#page-body" class="skip"><g:message
			code="default.link.skip.label" default="Skip to content&hellip;" /></a>
	<div id="sidebar" role="complementary">
		<h1>Examples</h1>
		<ul>
			<g:each in="${exampleRelations}">
				<li><g:link params="[q: it.key, example: true]">
						${it.key} (${new java.text.DecimalFormat('0.##').format(it.value*100)}%)
					</g:link></li>
			</g:each>
		</ul>
	</div>

	<div id="page-body" role="main">
		<h1>Welcome to the Relatedness Calculator</h1>
		     <g:if test="${!result || result.failed }">
		<p>
			Ever wondered how you're related to your 
			<g:link params="[q: 'half-cousin', example: true]">half-cousin</g:link>?
			How about your <g:link params="[q: 'great uncle', example: true]">great uncle</g:link>?  
			And what is a 
			<g:link params="[q: 'second cousin twice removed', example: true]">second cousin twice removed</g:link>, anyway?
			Ask the <strong>relatedness calculator</strong>.
		</p>
		<p>Just enter the name of a relative in plain
			English. For instance, you can enter <strong>brother</strong>, <strong>mom's cousin</strong>, or even
			<strong>grandpa's cousin's daughter</strong>.</p>

        </g:if>
        <div id="input-box-area">
		<div id="entry-form"">
		    <h2>Enter relative:</h2><br/>
			<g:form id="mainform" action="index" method="GET">
				<g:if test="${params.example}">
				    <richui:autoComplete id="relation-input" name="q" 
				        action="${createLinkTo('dir': 'autocomplete')}" 
				        onItemSelect="document.forms[0].submit();"/>
				</g:if>
				<g:else>
				    <richui:autoComplete id="relation-input" name="q" 
                        action="${createLinkTo('dir': 'autocomplete')}" 
                        onItemSelect="document.forms[0].submit();"
                        value="${params.q }"/>
                        <!-- value="${params.q }" -->
				</g:else>
				<input type="submit" value="Calculate" />
			</g:form>
			</div>
		<div id="result"> <g:if test="${result}">
				<g:if test="${result.failed}">
				    <g:if test="${result.parseError == 'Ambiguity' }">
				        <p><strong>${params.q}</strong> is ambiguous. <strong>It can mean...</strong>
				        <ul>
				            <g:each in="${result.alternateQueries}">
				                <li><g:link params="[q: it]">
				                        ${it}
				                    </g:link>
				                </li>
				            </g:each>
				        </ul>
				        </p>
				    </g:if>
				    <g:elseif test="${result.parseError == 'StepRelation'}">
                        <p>You are not biologically related to <strong>in-laws</strong> and <strong>step-relations</strong>.</p>
				    </g:elseif>
				    <g:else>
                        <p>Whoops! Nothing found for <strong> ${params.q}</strong>. Could you try re-phrasing it?</p>
                        <p>(Error: ${result.errorMessage})</p>				    
				    </g:else>
				</g:if>
				<g:else>
						<p>Result for <strong> ${params.q}</strong>
					<br />Relatedness coefficient: <b>${new java.text.DecimalFormat('0.############').format(result.coefficient * 100)}%</b>
						<br />Degree of relation: <b>${result.degree}</b>
						</p>
					<br />
				</g:else>
			</g:if>
		</div>
		</div>
		<g:if test="${result && !result.failed}">
			<div id="graph_container_outer">
				<div id="spinner">
					<g:img dir="images" file="spinner.gif" width="16" height="16" />
				</div>
				<div id="graph_container"></div>
			</div>
		</g:if>
		<div id="debug_output"></div>
		<div id="explanation">
		<g:if test="${result && !result.failed}">
			<h2>Explanation:</h2>
			<p>
				The relatedness between two people is expressed with two measures:
			<ul>
				<li><b>Relatedness coefficient:</b> what percentage of your
					genes you share.</li>
				<li><b>Degree of relation:</b> how far you are from that person
					in your family tree.</li>
			</p>
			</ul>
			<h2>Fun Facts:</h2>
			<ul>
				<li>The curious case of <g:link params='[q:"double cousin"]'>double cousins</g:link>
					occurs when two siblings from one family each marry two siblings
					from another family. Double cousins share 25% of their genes
					&mdash; the same as grandparents/grandchildren and half-siblings.
					No incest involved, but it's still kind of weird!
				</li>
				<li>Although it is illegal in many places, <a target="_blank"
					href='http://www.nytimes.com/2009/11/26/garden/26cousins.html'>over
						10% of marriages worldwide</a> are between cousins. Famous
					cousin-marriers include <a target="_blank"
					href='http://en.wikipedia.org/wiki/Charles_Darwin'>Charles
						Darwin</a>, <a target="_blank" href='http://en.wikipedia.org/wiki/Jerry_Lee_Lewis'>Jerry
						Lee Lewis</a>, and <a target="_blank"
					href='http://www.imdb.com/title/tt0757018/quotes?qt0285958'>Shelbyville
						Manhattan</a>.
				</li>
				<li>In terms of relatedness, you are just as likely to "take
					after" your uncle (25%) as your grandfather (25%).</li>
				<li>"Once removed," "twice removed," etc. refers to the distance up or down
				    your family tree relative to a cousin.  For instance, a "second cousin,
				    once removed" could be either your 
				    <g:link url="?q=second cousin's child&example=true">second cousin's child</g:link>
				    or your <g:link url="?q=parent's second cousin&example=true">parent's second cousin</g:link>.
				<li>Fraternal twins have the same relatedness coefficient as
					regular siblings (50%), but identical twins share 100% of their
					genes. So you're as closely related to your identical twin's
					children (50%) as to your own children (50%)!</li>
				<li>If there has been incest in your ancestry, you can
					calculate your relatedness coefficient to other family members by
					adding in any extra coefficients due to the incest. For instance,
					if your parents are cousins, then your relatedness to your father
					is 56.25%, because he's your <g:link
						params="[q: 'father', example: true]">father</g:link> (50%), as
					well as your <g:link url="?q=mother's cousin&example=true">mother's cousin</g:link>
					(6.25%).
				<li>Despite what you may have seen on <a
					href='http://en.wikipedia.org/wiki/Futurama' target="_blank">Futurama</a>, it is
					not possible for <a
					href='http://en.wikipedia.org/wiki/Fry_(Futurama)' target="_blank">Fry</a> to be
					his own grandfather. He would need to share 125% of his genes with
					himself.
				</li>
			</ul>
			<h2>Nerdy Stuff:</h2>
				<p>Richard Dawkins gives a great explanation of how to calculate 
				relatedness coefficients in <i>The
				Selfish Gene</i>:</p>

			<div id="quoted-text">

				<p>First identify all the common ancestors of A and B. For
					instance, the common ancestors of a pair of first cousins are their
					shared grandfather and grandmother. Once you have found a common
					ancestor, it is of course logically true that all his ancestors are
					common to A and B as well. However, we ignore all but the most
					recent common ancestors. In this sense, first cousins have only two
					common ancestors. If B is a lineal descendant of A, for instance
					his great grandson, then A himself is the ‘common ancestor’ we are
					looking for.</p>
				<p>Having located the common ancestor(s) of A and B, count the
					generation distance as follows. Starting at A, climb up the family
					tree until you hit a common ancestor, and then climb down again to
					B. The total number of steps up the tree and then down again is the
					generation distance. For instance, if A is B’s uncle, the
					generation distance is 3. The common ancestor is A’s father (say)
					and B’s grandfather. Starting at A you have to climb up one
					generation in order to hit the common ancestor. Then to get down to
					B you have to descend two generations on the other side. Therefore
					the generation distance is 1 + 2 = 3.</p>
				<p>Having found the generation distance between A and B via a
					particular common ancestor, calculate that part of their
					relatedness for which that ancestor is responsible. To do this,
					multiply 1/2 by itself once for each step of the generation
					distance. If the generation distance is 3, this means calculate 1/2
					x 1/2 x 1/2 or (1/2)^3. If the generation distance via a particular
					ancestor is equal to g steps, the portion of relatedness due to
					that ancestor is (1/2)^g.</p>
				<p>But this is only part of the relatedness between A and B. If
					they have more than one common ancestor we have to add on the
					equivalent figure for each ancestor. It is usually the case that
					the generation distance is the same for all common ancestors of a
					pair of individuals. Therefore, having worked out the relatedness
					between A and B due to any one of the ancestors, all you have to do
					in practice is to multiply by the number of ancestors. First
					cousins, for instance, have two common ancestors, and the
					generation distance via each one is 4. Therefore their relatedness
					is 2 x (1/2)^4 = 1/8. If A is B’s great-grandchild, the generation
					distance is 3 and the number of common ‘ancestors’ is 1 (B
					himself), so the relatedness is 1 x (1/2)^3 = 1/8.</p>
			</div>
		</g:if>
	</div>
	</div>

	<div id="github_ribbon">
		<a href="http://github.com/nolanlawson/RelatednessCalculatorInterface" target="_blank">
			<img style="position: absolute; top: 0; right: 0; border: 0;" 
			src="https://s3.amazonaws.com/github/ribbons/forkme_right_green_007200.png" alt="Fork me on GitHub">
		</a>
	</div>

	<!--  execute all the graph-drawing code only after the page has loaded,
	and only if necessary -->
	<g:if test="${result && !result.failed}">
		<g:javascript library="path" />
		<g:javascript library="prototype" />
		<g:javascript library="excanvas" />
		<g:javascript library="canviz" />
		<g:javascript library="drawcanviz"/>
		<g:javascript>
			function drawGraph() {
				drawCanviz(${result.graphWidth}, "${escapedQuery}");
			}
			window.onload=drawGraph;
		</g:javascript>
	</g:if>
</body>
</html>
