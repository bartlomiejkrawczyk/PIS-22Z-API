package com.example.api.exam.mapper;

import com.example.api.exam.entity.ExerciseEntity;
import com.example.model.exam.ExerciseDto;
import com.example.model.exam.ExerciseType;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Map;
import java.util.function.BiConsumer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExerciseMapper {

	public ExerciseEntity dtoToEntity(ExerciseDto exercise, int sectionId) {
		return ExerciseEntity.builder()
				.id(exercise.getId())
				.question(exercise.getQuestion())
				.type(exercise.getType().getType())
				.sectionId(sectionId)
				.build();
	}

	private final Map<ExerciseType, BiConsumer<ExerciseEntity, ExerciseDto.ExerciseDtoBuilder>> mappers = Map.of(
			ExerciseType.CHOICE, this::toChoice,
			ExerciseType.FLASH_CARD, this::toFlashCard,
			ExerciseType.MULTIPLE_CHOICE, this::toChoice
	);

	public ExerciseDto entityToDto(ExerciseEntity entity) {
		var type = intToType(entity.getType());

		var builder = ExerciseDto.builder()
				.id(entity.getId())
				.question(entity.getQuestion())
				.type(type);

		mappers.get(type).accept(entity, builder);

		return builder.build();
	}

	private ExerciseType intToType(int type) {
		return EnumSet.allOf(ExerciseType.class)
				.stream()
				.filter(exerciseType -> exerciseType.getType() == type)
				.findFirst()
				.orElse(ExerciseType.TRUTH_OR_FALSE);
	}

	private void toChoice(ExerciseEntity entity, ExerciseDto.ExerciseDtoBuilder builder) {
		String correctAnswer = null;
		var answers = new ArrayList<String>();
		for (var answer : entity.getEntities()) {
			var value = answer.getContent();
			if (answer.isCorrect()) {
				correctAnswer = value;
			}
			answers.add(value);
		}
		builder.possibleAnswers(answers);
		builder.correctAnswer(correctAnswer);
	}

	private void toFlashCard(ExerciseEntity entity, ExerciseDto.ExerciseDtoBuilder builder) {
		var answers = entity.getEntities();
		if (!answers.isEmpty()) {
			builder.correctAnswer(answers.get(0).getContent());
		}
	}



//		builder.possibleAnswers(List.of());
//		builder.blankAnswers(List.of());
//		builder.choiceAnswers(List.of());
//		builder.correctAnswer(null);
//		builder.listAnswers(List.of());
//		builder.correct(true);

}
