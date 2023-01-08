package com.example.api.exam.controller;

import com.example.api.exam.service.ExerciseService;
import com.example.model.exam.ExerciseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public Flux<ExerciseDto> getExerciseBySectionId(@PathVariable int sectionId) {
		return service.getExerciseBySectionId(sectionId);
	}

	@PostMapping("{sectionId}")
	public Mono<ExerciseDto> saveExercise(@PathVariable int sectionId, @RequestBody ExerciseDto exercise) {
		log.info("Received: " + exercise.toString());
		return service.saveExercise(sectionId, exercise);
	}
}
