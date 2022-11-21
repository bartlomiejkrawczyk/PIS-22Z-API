package com.example.api.wiki.service

import com.example.api.wiki.entity.ParagraphEntity
import com.example.api.wiki.mapper.ParagraphMapperImpl
import com.example.api.wiki.repository.ParagraphRepository
import com.example.model.Paragraph
import reactor.core.scheduler.Schedulers
import spock.lang.Specification
import spock.lang.Subject

import java.util.concurrent.Executors

class ParagraphServiceTest extends Specification {

	def repository = Mock(ParagraphRepository)
	def mapper = new ParagraphMapperImpl()
	def scheduler = Schedulers.fromExecutor(Executors.newFixedThreadPool(1))

	@Subject
	def service = new ParagraphServiceImpl(repository, mapper, scheduler)

	def 'Should retrieve paragraphs by concept id correctly'() {
		given:
		repository.findAllByConceptId(conceptId) >> entities

		expect:
		service.getParagraphsByConceptId(conceptId).collectList().block() == paragraphs

		where:
		conceptId | entities                                       | paragraphs
		1         | []                                             | []
		1         | [new ParagraphEntity(1, 1, 1, "test", "test")] | [new Paragraph(1, 1, "test", "test", [])]
	}

	def 'Should save paragraph correctly'() {
		given:
		repository.save(entity) >> entity

		expect:
		service.saveParagraph(paragraph, id).block() == paragraph

		where:
		id | paragraph                               | entity
		1  | new Paragraph(1, 1, "test", "test", []) | new ParagraphEntity(1, 1, 1, "test", "test")
		1  | null                                    | null
	}
}
