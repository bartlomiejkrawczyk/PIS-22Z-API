package com.example.api.wiki.service;

import com.example.model.Paragraph;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ParagraphService {

	Flux<Paragraph> getParagraphsByConceptId(int conceptId);

	// zapisuje paragraf i zwraca zapisany paragraf jako pojedynczy element
	// powinno się wyciągać z bazy jak do niej zapisujesz (tam gdzie jest generowane ID - adnotacja Generated iD)
	Mono<Paragraph> saveParagraph(Paragraph paragraph, int conceptId);
}
