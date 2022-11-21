package com.example.api.wiki.service

import com.example.api.wiki.entity.ConceptEntity
import com.example.api.wiki.entity.ParagraphEntity
import com.example.api.wiki.mapper.ConceptMapperImpl
import com.example.api.wiki.repository.ConceptRepository
import com.example.model.Concept
import com.example.model.Definition
import com.example.model.Paragraph
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import spock.lang.Specification

import java.util.concurrent.Executors

class ConceptServiceTest extends Specification {

	def repository = Mock(ConceptRepository)
	def mapper = new ConceptMapperImpl()
	def scheduler = Schedulers.fromExecutor(Executors.newFixedThreadPool(1))
	def paragraphService = Mock(ParagraphService)

	def service = new ConceptServiceImpl(repository, mapper, scheduler, paragraphService)


	def 'Should retrieve and map definitions correctly'() {
		given:
		repository.findById(id) >> Optional.ofNullable(entity)

		expect:
		service.getDefinitionById(id).block() == result

		where:
		id | entity                                      || result
		1  | new ConceptEntity(1, 1, "test", "test", []) || new Definition(1, "test", "test")
		1  | null                                        || null
	}

	def 'Should retrieve and map concepts correctly'() {
		given:
		repository.findConceptById(id) >> Optional.ofNullable(entity)

		expect:
		service.getConceptById(id).block() == result

		where:
		id | entity                                                                                  || result
		1  | new ConceptEntity(1, 1, "test", "test", [new ParagraphEntity(1, 1, 1, "test", "test")]) || new Concept(1, "test", "test", [new Paragraph(1, 1, "test", "test", [])])
		1  | new ConceptEntity(1, 1, "test", "test", [])                                             || new Concept(1, "test", "test", [])
		1  | null                                                                                    || null
	}


	def 'Should save concept and nested paragraphs correctly'() {
		given:
		repository.save(_ as ConceptEntity) >> entity
		for (Paragraph paragraph : paragraphs) {
			paragraphService.saveParagraph(paragraph, 1) >> Mono.justOrEmpty(paragraph)
		}

		expect:
		service.saveConcept(object, id).block() == object

		where:
		id | object                                                                    | entity                                      | paragraphs
		1  | new Concept(1, "test", "test", [new Paragraph(1, 1, "test", "test", [])]) | new ConceptEntity(1, 1, "test", "test", []) | [new Paragraph(1, 1, "test", "test", [])]
		1  | new Concept(1, "test", "test", [])                                        | new ConceptEntity(1, 1, "test", "test", []) | []
		1  | null                                                                      | null                                        | []
	}
}
