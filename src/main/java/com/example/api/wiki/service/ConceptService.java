package com.example.api.wiki.service;

import com.example.api.wiki.mapper.ConceptMapper;
import com.example.api.wiki.repository.ConceptRepository;
import com.example.model.Concept;
import com.example.model.Definition;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ConceptService {

	private final ConceptRepository conceptRepository;
	private final ConceptMapper conceptMapper;
	private final Scheduler scheduler;
	private final ParagraphService paragraphService;

	public Mono<Definition> getDefinitionById(int id) {
		return Mono.defer(
						() -> Mono.justOrEmpty(conceptRepository.findById(id))
				)
				.map(conceptMapper::entityToDefinition)
				.subscribeOn(scheduler);
	}

	public Mono<Concept> getConceptById(int id) {
		return Mono.defer(
						() -> Mono.justOrEmpty(conceptRepository.findConceptById(id))
				)
				.subscribeOn(scheduler)
				.map(conceptMapper::entityToConcept);
	}

	@Transactional
	public Mono<Concept> saveConcept(Concept concept) {
		log.info(concept.toString());
		return Mono.justOrEmpty(concept)
				.map(conceptMapper::dtoToEntity)
				.doOnNext(entity -> log.info("Saving entity to database: {}", entity))
				.map(conceptRepository::save)
				.doOnNext(entity -> log.info("Entity saved: {}", entity))
				.subscribeOn(scheduler)
				.map(conceptMapper::entityToConcept)
				.flatMap(saved -> Flux.fromIterable(concept.getParagraphs())
						.flatMap(p -> paragraphService.saveParagraph(p, saved.getId()))
						.collectList()
						.map(paragraphs -> {
							saved.setParagraphs(paragraphs);
							return saved;
						})
				);
	}
}
