package com.example.api.wiki.service;

import com.example.model.Paragraph;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ParagraphService {

	Flux<Paragraph> getParagraphsByConceptId(int conceptId);

	Mono<Paragraph> saveParagraph(Paragraph paragraph, int conceptId);
}
