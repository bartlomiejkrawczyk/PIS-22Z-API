package com.example.api.exam.service;

import com.example.api.exam.model.ExerciseDto;
import reactor.core.publisher.Flux;

public interface ExerciseService {
    Flux<ExerciseDto> getExerciseBySectionId(int sectionId);
}
