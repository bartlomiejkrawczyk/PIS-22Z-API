package com.example.api.exam.mapper;

import com.example.api.exam.entity.AnswerEntity;
import com.example.model.exam.ExerciseDto;
import com.example.model.exam.ExerciseType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnswerMapper {

	public List<AnswerEntity> dtoToEntities(ExerciseDto exercise) {

		if (exercise.getType() == ExerciseType.CHOICE) {
			var answers = exercise.getPossibleAnswers();
			var entities = new ArrayList<AnswerEntity>();
			for (int i = 0; i < answers.size(); i++) {
				var answer = answers.get(i);
				entities.add(
						AnswerEntity.builder()
								.exerciseId(0)
								.number(i)
								.sequentialNumber(i)
								.content(answer)
								.correct(Objects.equals(answer, exercise.getCorrectAnswer()))
								.build()
				);
			}
			return entities;

		}
		return null;
	}

}
