package com.example.api.wiki.controller

import com.example.api.wiki.entity.SectionEntity
import com.example.api.wiki.repository.SectionRepository
import com.example.model.Section
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@AutoConfigureMockMvc
class SectionControllerIntegrationTest extends Specification {

	@Autowired
	MockMvc mvc
	@Autowired
	SectionRepository repository

	def 'cleanup'() {
		repository.deleteAll()
	}

	def 'When get root sections is called should return 200 and correct response'() {
		given:
		repository.save(new SectionEntity(0, "test", null))
		var generatedId = repository.findAllBySuperSectionIdIsNull().stream().findFirst().map(SectionEntity::getId).orElse(0)
		var expected = List.of(Section.builder().id(generatedId).name("test").build())
		expect:
		mvc.perform(get("/section/"))
				.andExpect(status().isOk())
				.andReturn()
				.asyncResult == expected
	}

	def 'When get sections by parent id is called should return 200 and correct response'() {
		given:
		repository.save(new SectionEntity(0, "test", 1))
		var generatedId = repository.findAllBySuperSectionIdEquals(1).stream().findFirst().map(SectionEntity::getId).orElse(0)
		var expected = List.of(Section.builder().id(generatedId).name("test").build())
		expect:
		mvc.perform(get("/section/parent/1"))
				.andExpect(status().isOk())
				.andReturn()
				.asyncResult == expected
	}

	def 'When get section by id is called should return 200 and correct response'() {
		given:
		repository.save(new SectionEntity(0, "test", 1))
		var generatedId = repository.findAll().iterator().next().getId()
		var expected = Section.builder().id(generatedId).name("test").build()
		expect:
		mvc.perform(get("/section/id/" + generatedId))
				.andExpect(status().isOk())
				.andReturn()
				.asyncResult == expected
	}

	def 'When delete section by id is called should return 200 and correct response'() {
		given:
		repository.save(new SectionEntity(0, "test", 1))
		var generatedId = repository.findAll().iterator().next().getId()
		expect:
		mvc.perform(delete("/section/" + generatedId))
				.andExpect(status().isOk())
				.andReturn()
				.asyncResult == generatedId

		!repository.findAll().iterator().hasNext()
	}


	def 'When save sections is called should return 200 and correct response'() {
		given:
		var toSave = new Section(0, "test", List.of())
		expect:
		var result = mvc.perform(
				post("/section/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(toSave))
		)
				.andExpect(status().isOk())
				.andReturn()
				.asyncResult

		var generatedId = repository.findAll().iterator().next().getId()
		toSave.setId(generatedId)
		result == toSave

		repository.findAll().iterator().hasNext()
	}

}
