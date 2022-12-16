package com.example.api.wiki.mapper;

import com.example.api.config.MapstructConfig;
import com.example.api.wiki.entity.ParagraphEntity;
import com.example.model.Paragraph;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class)
public interface ParagraphMapper {
	// jak chcę zignorować jakies pole, to po ptostu ignore = true
	@Mapping(target = "number")
	@Mapping(target = "sequentialNumber")
	@Mapping(target = "header")
	@Mapping(target = "description")
	@Mapping(target = "multimedia", ignore = true)
	Paragraph entityToDto(ParagraphEntity entity);

	@Mapping(target = "conceptId", expression = "java(conceptId)")
	@Mapping(target = "number")
	@Mapping(target = "sequentialNumber")
	@Mapping(target = "header")
	@Mapping(target = "description")
	ParagraphEntity dtoToEntity(Paragraph dto, int conceptId);
}
