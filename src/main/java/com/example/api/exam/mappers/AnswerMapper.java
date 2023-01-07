package com.example.api.exam.mappers;

import com.example.api.config.MapstructConfig;
import com.example.api.exam.entity.AnswerEntity;
import com.example.api.exam.model.ExerciseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class)
public interface AnswerMapper {
	@Mapping(target = "exerciseId", source = "id")
	@Mapping(target = "number", source = "answer_no") //ANSWER_NO
	@Mapping(target = "content") //CONTENT
	@Mapping(target = "correct") //CORRECT
	@Mapping(target = "sequentialNumber", ignore = true) //TODO: SEQUENTIAL_NUMBER - what is it?
	AnswerEntity dtoToEntity(ExerciseDto entity, int answer_no);

	@Mapping(target = "id", source = "exerciseId")
	@Mapping(target = "answer_no", source = "number") //ANSWER_NO
	@Mapping(target = "content") //CONTENT
	@Mapping(target = "correct") //CORRECT
	@Mapping(target = "----idk----", source = "sequentialNumber", ignore = true) //TODO: SEQUENTIAL_NUMBER
	ExerciseDto EntityToExerciseDto(AnswerEntity entity);
}
