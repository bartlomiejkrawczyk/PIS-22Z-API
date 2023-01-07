package com.example.api.wiki.mapper;

import com.example.api.config.MapstructConfig;
import com.example.api.wiki.entity.SectionEntity;
import com.example.model.Section;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class)
public interface SectionMapper {

	@Mapping(target = "id")
	@Mapping(target = "name")
	@Mapping(target = "subSections", ignore = true)
	Section entityToSection(SectionEntity entity);

	@Mapping(target = "id")
	@Mapping(target = "name")
	@Mapping(target = "superSectionId", expression = "java(parentId)")
	SectionEntity dtoToEntity(Section section, Integer parentId);
}
