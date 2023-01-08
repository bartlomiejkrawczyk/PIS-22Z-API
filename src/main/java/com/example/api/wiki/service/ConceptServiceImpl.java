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
// wszystkie pola jako final zostaną wstrzyknięte do komponentu gdy zostaną stworzone Biny
public class ConceptServiceImpl implements ConceptService {

	private final ConceptRepository conceptRepository;
	private final ConceptMapper conceptMapper;
	private final Scheduler scheduler;
	private final ParagraphService paragraphService;

	public Mono<Definition> getDefinitionById(int id) {
		return Mono.defer(
						() -> Mono.justOrEmpty(conceptRepository.findById(id)) // lazy fetch - nie wyciągana lista
				)
				.map(conceptMapper::entityToDefinition) // dlatego korzystamy z Def nie z konceptu
				.subscribeOn(scheduler);
	}

	public Mono<Concept> getConceptById(int id) {
		return Mono.defer(
						() -> Mono.justOrEmpty(conceptRepository.findConceptById(id)) // to samo
				)
				.subscribeOn(scheduler)
				.map(conceptMapper::entityToConcept);
	}

	@Override
	public Flux<Concept> getConceptsBySectionId(int sectionId) {
		return Flux.defer(
						() -> Flux.fromIterable(conceptRepository.findAllBySectionId(sectionId))
				)
				.subscribeOn(scheduler)
				.map(conceptMapper::entityToConcept);
	}

	@Transactional
	public Mono<Concept> saveConcept(Concept concept, int sectionId) {
		return Mono.justOrEmpty(concept)
				.map(dto -> conceptMapper.dtoToEntity(dto, sectionId))// mapuję na możliwy do zapisania
				.doOnNext(entity -> log.info("Saving entity to database: {}", entity))
				.map(conceptRepository::save) // zapisuję
				.doOnNext(entity -> log.info("Entity saved: {}", entity))
				.subscribeOn(scheduler)
				.map(conceptMapper::entityToConcept) // mapuję z tego w bazie na ten w api
				.flatMap(saved -> Flux.fromIterable(concept.getParagraphs())// iteracja po wszystkich elementach w koncepcie
						.flatMap(p -> paragraphService.saveParagraph(p, saved.getId()))// każdy z nich jest iterowany i zapisywany
						.collectList() // zbieram listę tych elementów (mono lista)
						.map(paragraphs -> {
							saved.setParagraphs(paragraphs); // przypisuję te paragrafy do save'a
							return saved;
						})
				);
	}
}
