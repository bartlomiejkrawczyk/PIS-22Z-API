package com.example.api.user.mapper;

import com.example.api.config.MapstructConfig;
import com.example.api.user.entity.UserEntity;
import com.example.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class)
public interface UserMapper {

	@Mapping(target = "id")
	@Mapping(target = "login")
	@Mapping(target = "email")
	@Mapping(target = "password")
	@Mapping(target = "salt")
	@Mapping(target = "type")
	User entityToDto(UserEntity entity);

	@Mapping(target = "login")
	@Mapping(target = "email")
	@Mapping(target = "password")
	@Mapping(target = "salt")
	@Mapping(target = "type")
	UserEntity dtoToEntity(User dto);
}
