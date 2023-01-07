package com.example.api.wiki.service;

import com.example.api.wiki.mapper.SectionMapper;
import com.example.api.wiki.repository.SectionRepository;
import com.example.model.Section;
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
public class SectionServiceImpl implements SectionService {

	private final SectionRepository repository;
	private final SectionMapper mapper;
	private final Scheduler scheduler;

	@Override
	public Flux<Section> getRootSections() {
		return Flux.defer(
						() -> Flux.fromIterable(repository.findAllBySuperSectionIdIsNull())
				)
				.map(mapper::entityToSection)
				.subscribeOn(scheduler);
	}

	@Override
	public Flux<Section> getSectionByParentId(int parentId) {
		return Flux.defer(
						() -> Flux.fromIterable(repository.findAllBySuperSectionIdEquals(parentId))
				)
				.map(mapper::entityToSection)
				.subscribeOn(scheduler);
	}

	@Override
	public Mono<Section> getSectionById(int id) {
		return Mono.defer(
						() -> Mono.justOrEmpty(repository.findById(id))
				)
				.map(mapper::entityToSection)
				.subscribeOn(scheduler);
	}

	@Override
	public Mono<Integer> deleteSection(int id) {
		repository.deleteById(id);
		return Mono.just(id);
	}

	@Override
	public Mono<Section> saveSection(Integer parentId, Section section) {
		return Mono.justOrEmpty(section)
				.map(it -> mapper.dtoToEntity(it, parentId))
				.doOnNext(entity -> log.info("Saving entity to database: {}", entity))
				.map(repository::save)
				.doOnNext(entity -> log.info("Entity saved: {}", entity))
				.map(mapper::entityToSection)
				.subscribeOn(scheduler);
	}
}
