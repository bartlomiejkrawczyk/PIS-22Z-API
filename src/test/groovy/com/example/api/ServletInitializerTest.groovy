package com.example.api

import org.springframework.boot.builder.SpringApplicationBuilder
import spock.lang.Specification

class ServletInitializerTest extends Specification {

	def springApplicationBuilder = Mock(SpringApplicationBuilder)

	def 'Should call sources with the correct class'() {
		given:
		def servletInitializer = new ServletInitializer()
		springApplicationBuilder.sources(ApiApplication.class) >> springApplicationBuilder
		when:
		def result = servletInitializer.configure(springApplicationBuilder)
		then:
		springApplicationBuilder == result
	}
}
