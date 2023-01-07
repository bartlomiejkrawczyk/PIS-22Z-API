package com.example.api.exam.model;

import com.example.model.Definition;
import com.example.model.exam.answer.BlankAnswer;
import com.example.model.exam.answer.ChoiceAnswer;
import com.example.model.exam.answer.ListAnswer;
import com.example.model.multimedia.DisplayableFile;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExerciseDto {

	int id;
	String question;
	DisplayableFile content;

	@Builder.Default
	List<Definition> definitions = new ArrayList<>();

	ExerciseType type;

	@Builder.Default
	List<String> possibleAnswers = new ArrayList<>(); // Choice
	String correctAnswer; // Flash Card, Choice

	@Builder.Default
	List<BlankAnswer> blankAnswers = new ArrayList<>(); // FillBlanks

	@Builder.Default
	List<ChoiceAnswer> choiceAnswers = new ArrayList<>(); // MultipleChoice, MultipleTruthOrFalse


	@Builder.Default
	List<ListAnswer> listAnswers = new ArrayList<>(); // SelectFromList

	Boolean correct; // TruthOrFalse
}

