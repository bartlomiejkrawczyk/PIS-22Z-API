package com.example.api.wiki.controller

import com.example.api.wiki.entity.ConceptEntity
import com.example.api.wiki.entity.ParagraphEntity
import com.example.api.wiki.repository.ConceptRepository
import com.example.api.wiki.repository.ParagraphRepository
import com.example.model.Concept
import com.example.model.Paragraph
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@AutoConfigureMockMvc
class ConceptControllerIntegrationTest extends Specification {

	@Autowired
	MockMvc mvc
	@Autowired
	ConceptRepository conceptRepository
	@Autowired
	ParagraphRepository paragraphRepository

	def 'cleanup'() {
		paragraphRepository.deleteAll()
		conceptRepository.deleteAll()
	}

	def 'When get is called then the response has status 200 and content is Hello, phrase!'() {
		given:
		conceptRepository.save(entity)
		for (def paragraph : entity.getParagraphs()) {
			paragraphRepository.save(paragraph)
		}
		expect:
		mvc.perform(get(url))
				.andExpect(status().isOk())
				.andReturn()
				.asyncResult == response
		where:
		url          | entity                                                                                                     || response
		'/concept/1' | new ConceptEntity(1, 1, "Key Phrase", "summary", [])                                                       || new Concept(1, "Key Phrase", "summary", [])
		'/concept/2' | new ConceptEntity(2, 1, "Test Phrase", "summary", [new ParagraphEntity(2, 1, 1, "Header", "Description")]) || new Concept(2, "Test Phrase", "summary", [new Paragraph(1, 1, "Header", "Description", [])])
	}
}
