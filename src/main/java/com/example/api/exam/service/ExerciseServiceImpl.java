package com.example.api.exam.service;

import com.example.api.exam.mapper.ExerciseMapper;
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
	private final ExerciseRepository exerciseRepository;
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
				.map(exerciseMapper::dtoToEntity)
				.doOnNext(entity -> log.info("Saving entity to database: {}", entity))
				.map(exerciseRepository::save)
				.doOnNext(entity -> log.info("Entity saved: {}", entity))
				.map(exerciseMapper::entityToDto)
				.subscribeOn(scheduler);
	}
}
