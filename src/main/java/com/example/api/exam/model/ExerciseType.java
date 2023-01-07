package com.example.api.exam.model;

import com.example.model.exam.Choice;
import com.example.model.exam.Exercise;
import com.example.model.exam.FillBlanks;
import com.example.model.exam.FlashCard;
import com.example.model.exam.MultipleChoice;
import com.example.model.exam.MultipleTruthOrFalse;
import com.example.model.exam.SelectFromList;
import com.example.model.exam.TruthOrFalse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExerciseType {
	CHOICE(1, Choice.class),
	FILL_BLANKS(2, FillBlanks.class),
	FLASH_CARD(3, FlashCard.class),
	MULTIPLE_CHOICE(4, MultipleChoice.class),
	MULTIPLE_TRUTH_OR_FALSE(5, MultipleTruthOrFalse.class),
	SELECT_FROM_LIST(6, SelectFromList.class),
	TRUTH_OR_FALSE(7, TruthOrFalse.class),
	;

	private final int type;
	private final Class<? extends Exercise> clazz;
}
