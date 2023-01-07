package com.example.api.exam.controller;

import com.example.api.exam.model.ExerciseDto;
import com.example.api.exam.service.ExerciseService;
import com.example.model.exam.Exercise;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/exercise")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ExerciseController {

	private final ExerciseService service;

	@GetMapping("{sectionId}")
	public Flux<ExerciseDto> getExerciseBySectionId(int sectionId) {
		return Flux.empty();
	}

	@PostMapping
	public Mono<ExerciseDto> saveExercise(ExerciseDto exercise) {
		log.info(exercise.toString());
		return Mono.justOrEmpty(exercise);
	}
}
