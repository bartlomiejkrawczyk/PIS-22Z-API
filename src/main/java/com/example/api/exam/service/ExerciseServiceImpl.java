package com.example.api.exam.service;

import com.example.api.exam.mappers.ExerciseMapper;
import com.example.api.exam.model.ExerciseDto;
import com.example.api.exam.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ExerciseServiceImpl implements ExerciseService {

	private final ExerciseRepository exerciseRepository;
	private final Scheduler scheduler;

	public Flux<ExerciseDto> getExerciseBySectionId(int sectionId) {
		// zmienna - znaleźć wszystkie ExID do sekcji -> zmapować ExEnt do Fluxa Ex
		return Flux.defer(
						exerciseRepository.findById(sectionId)
								.map(ExerciseMapper::EntityToDto)
				)
				.subscribeOn(scheduler);
	}
}
