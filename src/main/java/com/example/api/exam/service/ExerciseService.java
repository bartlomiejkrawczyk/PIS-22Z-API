package com.example.api.exam.service;

import com.example.model.exam.ExerciseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ExerciseService {

	Flux<ExerciseDto> getExerciseBySectionId(int sectionId);

	Mono<ExerciseDto> saveExercise(int sectionId, ExerciseDto exerciseDto);
}
