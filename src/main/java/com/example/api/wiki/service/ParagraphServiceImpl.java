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
				) //pobiram z bazy danyhch za pomocą custom funkcji
				.subscribeOn(scheduler) // bookuję scheda
				.map(paragraphMapper::entityToDto); // zaciągam z powrotem do API
	}

	@Transactional
	public Mono<Paragraph> saveParagraph(Paragraph paragraph, int conceptId) {
		return Mono.justOrEmpty(paragraph) // jak się okaże że mono nie jest puste
				.map(p -> paragraphMapper.dtoToEntity(p, conceptId)) // przerób paragraf do entity
				.doOnNext(entity -> log.info("Saving entity to database: {}", entity))
				.map(paragraphRepository::save) // zapisz mój paragraf do entity; da się przpisać na p -> paragraphRepository.save(p)
				.doOnNext(entity -> log.info("Entity saved: {}", entity))
				.subscribeOn(scheduler) // tak aby połączenia do bazy były ograniczone
				.map(paragraphMapper::entityToDto); // mapuję to coo dostałem z bazy na klasę zewnętrzna
	}
}
