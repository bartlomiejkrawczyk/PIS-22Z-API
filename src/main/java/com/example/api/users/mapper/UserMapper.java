package com.example.api.users.mapper;

import com.example.api.config.MapstructConfig;
import com.example.api.users.entity.UserEntity;

import com.example.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class)
public interface UserMapper {

	// po prawo nazwy z ConceptEntity a po lewo sa te które wychodzą
	//@Mapping(target = "salt")
	@Mapping(target = "id")
	@Mapping(target = "login")
	@Mapping(target = "email")
	@Mapping(target = "password")
	@Mapping(target = "type")
	UserEntity dtoToEntity(User entity);

	//	@Mapping(target = "salt")
	@Mapping(target = "id")
	@Mapping(target = "login")
	@Mapping(target = "email")
	@Mapping(target = "password")
	@Mapping(target = "type")
	User entityToUser(UserEntity entity);
}
