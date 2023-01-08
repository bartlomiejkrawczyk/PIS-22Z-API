package com.example.api.exam.service;

import com.example.model.exam.ExerciseDto;
import reactor.core.publisher.Flux;

public interface ExerciseService {
    Flux<ExerciseDto> getExerciseBySectionId(int sectionId);
}
