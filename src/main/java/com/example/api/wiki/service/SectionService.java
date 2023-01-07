package com.example.api.wiki.service;

import com.example.model.Section;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SectionService {

	Flux<Section> getRootSections();

	Flux<Section> getSectionByParentId(int parentId);

	Mono<Section> getSectionById(int id);

	Mono<Integer> deleteSection(int id);

	Mono<Section> saveSection(Integer parentId, Section section);
}
