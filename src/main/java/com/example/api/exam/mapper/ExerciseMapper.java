package com.example.api.exam.mapper;

import com.example.api.exam.entity.AnswerEntity;
import com.example.api.exam.entity.ExerciseEntity;
import com.example.api.exam.entity.SubAnswerEntity;
import com.example.model.exam.ExerciseDto;
import com.example.model.exam.ExerciseType;
import com.example.model.exam.answer.BlankAnswer;
import com.example.model.exam.answer.ChoiceAnswer;
import com.example.model.exam.answer.ListAnswer;
import java.util.ArrayList;
import java.util.Comparator;
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
			ExerciseType.MULTIPLE_CHOICE, this::toMultipleChoice,
			ExerciseType.TRUTH_OR_FALSE, this::toTruthOrFalse,
			ExerciseType.MULTIPLE_TRUTH_OR_FALSE, this::toMultipleChoice,
			ExerciseType.FILL_BLANKS, this::toFillBlanks,
			ExerciseType.SELECT_FROM_LIST, this::toSelectFromList
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

	private void toTruthOrFalse(ExerciseEntity entity, ExerciseDto.ExerciseDtoBuilder builder) {
		var answers = entity.getEntities();
		if (!answers.isEmpty()) {
			builder.correct(answers.get(0).isCorrect());
		}
	}

	private void toMultipleChoice(ExerciseEntity entity, ExerciseDto.ExerciseDtoBuilder builder) {
		var answers = entity.getEntities()
				.stream()
				.sorted(Comparator.comparing(AnswerEntity::getSequentialNumber))
				.map(it -> ChoiceAnswer.builder()
						.number(it.getNumber())
						.content(it.getContent())
						.correct(it.isCorrect())
						.build())
				.toList();
		builder.choiceAnswers(answers);
	}

	private void toFillBlanks(ExerciseEntity entity, ExerciseDto.ExerciseDtoBuilder builder) {
		var answers = entity.getEntities()
				.stream()
				.sorted(Comparator.comparing(AnswerEntity::getSequentialNumber))
				.filter(it -> !it.getSubAnswers().isEmpty())
				.map(it -> BlankAnswer.builder()
						.start(it.getContent())
						.answer(it.getSubAnswers().get(0).getContent())
						.build()
				)
				.toList();
		builder.blankAnswers(answers);
	}

	private void toSelectFromList(ExerciseEntity entity, ExerciseDto.ExerciseDtoBuilder builder) {
		var answers = entity.getEntities()
				.stream()
				.sorted(Comparator.comparing(AnswerEntity::getSequentialNumber))
				.filter(it -> !it.getSubAnswers().isEmpty())
				.map(it -> {
					var subAnswers = it.getSubAnswers();
					var correct = subAnswers.stream()
							.filter(SubAnswerEntity::isCorrect)
							.findFirst()
							.map(SubAnswerEntity::getContent)
							.orElse(null);

					return ListAnswer.builder()
							.start(it.getContent())
							.possibleAnswers(subAnswers.stream().map(SubAnswerEntity::getContent).toList())
							.correctAnswer(correct)
							.build();
				})
				.toList();
		builder.listAnswers(answers);
	}

}
