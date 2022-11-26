package com.example.api.web

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

@SpringBootTest
@AutoConfigureMockMvc
class WebControllerTest extends Specification {

	@Autowired
	MockMvc mvc

	def 'When get is called then the response has status 200 and content is Hello, phrase!'() {
		expect:
		mvc.perform(get(url))
				.andExpect(status().isOk())
				.andReturn()
				.asyncResult == response
		where:
		url         || response
		'/ping'     || 'Hello, World!'
		'/ping/'    || 'Hello, World!'
		'/ping/Abc' || 'Hello, Abc!'
	}

}
