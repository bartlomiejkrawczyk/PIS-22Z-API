package com.example.api.wiki.service;

import com.example.model.Concept;
import com.example.model.Definition;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ConceptService {

	Mono<Definition> getDefinitionById(int id);

	Mono<Concept> getConceptById(int id);

	Flux<Definition> getConceptsBySectionId(int sectionId);

	Mono<Concept> saveConcept(Concept concept, int sectionId);
}
