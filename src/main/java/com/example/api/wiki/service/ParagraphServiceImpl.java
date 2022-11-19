package com.example.api.wiki.service;

import com.example.api.wiki.mapper.ParagraphMapper;
import com.example.api.wiki.repository.ParagraphRepository;
import com.example.model.Paragraph;
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
public class ParagraphServiceImpl implements ParagraphService {

	private final ParagraphRepository paragraphRepository;
	private final ParagraphMapper paragraphMapper;
	private final Scheduler scheduler;

	public Flux<Paragraph> getParagraphsByConceptId(int conceptId) {
		return Flux.defer(
						() -> Flux.fromIterable(paragraphRepository.findAllByConceptId(conceptId))
				)
				.subscribeOn(scheduler)
				.map(paragraphMapper::entityToDto);
	}

	@Transactional
	public Mono<Paragraph> saveParagraph(Paragraph paragraph, int conceptId) {
		return Mono.justOrEmpty(paragraph)
				.map(p -> paragraphMapper.dtoToEntity(p, conceptId))
				.doOnNext(entity -> log.info("Saving entity to database: {}", entity))
				.map(paragraphRepository::save)
				.doOnNext(entity -> log.info("Entity saved: {}", entity))
				.subscribeOn(scheduler)
				.map(paragraphMapper::entityToDto);
	}
}
