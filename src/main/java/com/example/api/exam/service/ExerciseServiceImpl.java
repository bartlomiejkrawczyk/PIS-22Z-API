package com.example.api.exam.service;

import com.example.api.exam.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.scheduler.Scheduler;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ExerciseServiceImpl implements ExerciseService {

	private final ExerciseRepository exerciseRepository;
	private final Scheduler scheduler;
}
