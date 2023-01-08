package com.example.api.wiki.controller;

import com.example.api.wiki.service.ConceptService;
import com.example.model.Concept;
import com.example.model.Definition;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/concept")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ConceptController {

	private final ConceptService conceptService;

	@GetMapping("/definition/{id}")
	public Mono<Definition> getDefinitionById(@PathVariable int id) {
		return conceptService.getDefinitionById(id);
	}

	@GetMapping("{id}")
	public Mono<Concept> getConceptById(@PathVariable int id) {
		return conceptService.getConceptById(id);
	}

	@GetMapping("/section/{sectionId}")
	public Flux<Definition> getConceptsBySectionId(@PathVariable int sectionId) {
		return conceptService.getConceptsBySectionId(sectionId);
	}

	@PostMapping("{sectionId}")
	public Mono<Concept> saveConcept(@RequestBody Concept concept, @PathVariable int sectionId) {
		return conceptService.saveConcept(concept, sectionId);
	}
}
