// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }


grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
                      xml: ['text/xml', 'application/xml'],
                      text: 'text/plain',
                      js: 'text/javascript',
                      rss: 'application/rss+xml',
                      atom: 'application/atom+xml',
                      css: 'text/css',
                      csv: 'text/csv',
                      all: '*/*',
                      json: ['application/json','text/json'],
                      form: 'application/x-www-form-urlencoded',
                      multipartForm: 'multipart/form-data'
                    ]

//The following options apply to YUI JS Minifier only
grails.resources.mappers.yuicssminify.excludes = ['**/*-min.css']
grails.resources.mappers.yuijsminify.excludes = ['**/*-min.js']

richui.serve.resource.files.remote=true

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']


// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

//
// YUI Minify Resources plugin config
//
grails.resources.mappers.yuijsminify.noMunge = true // this is actually backwards, and should be called "munge"
grails.resources.mappers.yuijsminify.preserveAllSemicolons = false
grails.resources.mappers.yuijsminify.disableOptimizations = false

// set per-environment serverURL stem for creating absolute links
def logDir = "../logs" // points to TOMCAT log directory.
environments {
   production {
       logDir = "./logs" // points to TOMCAT log directory.
       grails.serverURL = "http://localhost:8080/${appName}"
  }
   development {
       grails.resources.debug = true
       logDir = "./logs" // points to Eclipse project log directory.
       grails.serverURL = "http://localhost:8080/${appName}"
  }
   test {
      grails.serverURL = "http://localhost:8080/${appName}"
  }
}

// log4j configuration
log4j = {
	appenders {
        console (name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n'))
		appender new org.apache.log4j.DailyRollingFileAppender(name: "infoLog", datePattern: "'.'yyyy-MM-dd", file: logDir+'/relatedness_calculator.log', threshold: org.apache.log4j.Level.INFO, layout: pattern(conversionPattern: '[%d{yyyy-MM-dd hh:mm:ss.SSS}] %p %c{5} %m%n'))
	}

	error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
			'org.codehaus.groovy.grails',
			'org.springframework'
	warn   'org.mortbay.log'
	info 'com.nolanlawson',
		'grails.app'

	root {
		info 'infoLog','stdout'
		error()
		additivity = true
	}
}
