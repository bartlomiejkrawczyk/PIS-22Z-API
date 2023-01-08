package com.example.api.exam.mapper;

import com.example.api.config.MapstructConfig;
import com.example.api.exam.entity.ExerciseEntity;
import com.example.model.exam.ExerciseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class)
public interface ExerciseMapper {

	@Mapping(target = "id", source = "id") //EXERCISE_ID, QUESTION, TYPE, SECTION_ID
	@Mapping(target = "question")
	@Mapping(target = "type")
	ExerciseEntity dtoToEntity(ExerciseDto entity);

	@Mapping(target = "id", source = "id") //EXERCISE_ID, QUESTION, TYPE, SECTION_ID
	@Mapping(target = "question")
	@Mapping(target = "type")
	ExerciseDto EntityToDto(ExerciseEntity entity);


}
