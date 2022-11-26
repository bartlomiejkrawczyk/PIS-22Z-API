package com.example.api.config;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.MapperConfig;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@MapperConfig(
		componentModel = "spring",
		unmappedTargetPolicy = ReportingPolicy.IGNORE,
		injectionStrategy = InjectionStrategy.CONSTRUCTOR,
		nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface MapstructConfig {
}
