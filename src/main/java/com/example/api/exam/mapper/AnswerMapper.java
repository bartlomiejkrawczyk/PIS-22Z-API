package com.example.api.exam.mapper;

import com.example.api.exam.entity.AnswerEntity;
import com.example.api.exam.entity.SubAnswerEntity;
import com.example.model.exam.ExerciseDto;
import com.example.model.exam.ExerciseType;
import com.example.model.exam.answer.ListAnswer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnswerMapper {

	private final Map<ExerciseType, Function<ExerciseDto, List<AnswerEntity>>> mappers = Map.of(
			ExerciseType.CHOICE, this::fromChoice,
			ExerciseType.FLASH_CARD, this::fromFlashCard,
			ExerciseType.TRUTH_OR_FALSE, this::fromTruthOrFalse,
			ExerciseType.MULTIPLE_CHOICE, this::fromMultipleChoice,
			ExerciseType.MULTIPLE_TRUTH_OR_FALSE, this::fromMultipleChoice,
			ExerciseType.FILL_BLANKS, this::fromFillBlanks,
			ExerciseType.SELECT_FROM_LIST, this::fromSelectFromList
	);

	public List<AnswerEntity> dtoToEntities(ExerciseDto exercise) {
		return mappers.get(exercise.getType()).apply(exercise);
	}

	private List<AnswerEntity> fromChoice(ExerciseDto exercise) {
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

	private List<AnswerEntity> fromFlashCard(ExerciseDto exercise) {
		return List.of(
				AnswerEntity.builder()
						.exerciseId(0)
						.number(1)
						.sequentialNumber(1)
						.content(exercise.getCorrectAnswer())
						.correct(true)
						.build()
		);
	}

	private List<AnswerEntity> fromTruthOrFalse(ExerciseDto exercise) {
		return List.of(
				AnswerEntity.builder()
						.exerciseId(0)
						.number(1)
						.sequentialNumber(1)
						.correct(exercise.getCorrect())
						.build()
		);
	}

	private List<AnswerEntity> fromMultipleChoice(ExerciseDto exercise) {
		var answers = exercise.getChoiceAnswers();
		var entities = new ArrayList<AnswerEntity>();
		for (int i = 0; i < answers.size(); i++) {
			var answer = answers.get(i);
			entities.add(
					AnswerEntity.builder()
							.exerciseId(0)
							.number(i)
							.sequentialNumber(i)
							.content(answer.getContent())
							.correct(answer.isCorrect())
							.build()
			);
		}
		return entities;
	}

	private List<AnswerEntity> fromFillBlanks(ExerciseDto exercise) {
		var answers = exercise.getBlankAnswers();
		var entities = new ArrayList<AnswerEntity>();
		for (int i = 0; i < answers.size(); i++) {
			var answer = answers.get(i);
			entities.add(
					AnswerEntity.builder()
							.exerciseId(0)
							.number(i)
							.sequentialNumber(i)
							.content(answer.getStart())
							.subAnswers(List.of(new SubAnswerEntity(0, 1, 1, answer.getAnswer(), true, 1)))
							.build()
			);
		}
		return entities;
	}

	private List<AnswerEntity> fromSelectFromList(ExerciseDto exercise) {
		var answers = exercise.getListAnswers();
		var entities = new ArrayList<AnswerEntity>();
		for (int i = 0; i < answers.size(); i++) {
			var answer = answers.get(i);
			entities.add(
					AnswerEntity.builder()
							.exerciseId(0)
							.number(i)
							.sequentialNumber(i)
							.content(answer.getStart())
							.subAnswers(fromListAnswer(i, answer))
							.build()
			);
		}
		return entities;
	}

	private List<SubAnswerEntity> fromListAnswer(int answerNumber, ListAnswer answer) {
		var answers = answer.getPossibleAnswers();
		var entities = new ArrayList<SubAnswerEntity>();
		for (int i = 0; i < answers.size(); i++) {
			var value = answers.get(i);
			entities.add(
					SubAnswerEntity.builder()
							.exerciseId(0)
							.answerNumber(answerNumber)
							.subAnswerNumber(i)
							.sequentialNumber(i)
							.content(value)
							.correct(value.equalsIgnoreCase(answer.getCorrectAnswer()))
							.build()
			);
		}
		return entities;
	}

}
