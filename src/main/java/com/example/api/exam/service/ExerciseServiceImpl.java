package com.example.api.exam.service;

import com.example.api.exam.entity.ExerciseEntity;
import com.example.api.exam.mapper.AnswerMapper;
import com.example.api.exam.mapper.ExerciseMapper;
import com.example.api.exam.repository.AnswerRepository;
import com.example.api.exam.repository.ExerciseRepository;
import com.example.model.exam.ExerciseDto;
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
public class ExerciseServiceImpl implements ExerciseService {

	private final ExerciseMapper exerciseMapper;
	private final AnswerMapper answerMapper;

	private final ExerciseRepository exerciseRepository;
	private final AnswerRepository answerRepository;

	private final Scheduler scheduler;

	@Override
	public Flux<ExerciseDto> getExerciseBySectionId(int sectionId) {
		return Flux.defer(
						() -> Flux.fromIterable(exerciseRepository.findAllBySectionId(sectionId))
				)
				.doOnNext(it -> log.info("In database: " + it.toString()))
				.map(exerciseMapper::entityToDto)
				.doOnNext(it -> log.info("After mapping: " + it.toString()))
				.subscribeOn(scheduler);
	}

	@Override
	public Mono<ExerciseDto> saveExercise(int sectionId, ExerciseDto exerciseDto) {
		return Mono.justOrEmpty(exerciseDto)
				.map(it -> exerciseMapper.dtoToEntity(it, sectionId))
				.doOnNext(entity -> log.info("Saving entity to database: {}", entity))
				.map(exerciseRepository::save)
				.flatMap(entity -> saveAnswers(exerciseDto, entity))
				.doOnNext(entity -> log.info("Entity saved: {}", entity))
				.map(exerciseMapper::entityToDto)
				.subscribeOn(scheduler);
	}

	private Mono<ExerciseEntity> saveAnswers(ExerciseDto exerciseDto, ExerciseEntity entity) {
		return Flux.fromIterable(answerMapper.dtoToEntities(exerciseDto))
				.map(answer -> {
					answer.setExerciseId(entity.getId());
					answer.getSubAnswers().forEach(it -> it.setExerciseId(entity.getId()));
					return answer;
				})
				.doOnNext(it -> log.info("Saving entity to database: {}", it))
				.map(answerRepository::save)
				.doOnNext(it -> log.info("Entity saved: {}", it))
				.collectList()
				.map(answers -> {
					entity.setEntities(answers);
					return entity;
				});
	}
}
