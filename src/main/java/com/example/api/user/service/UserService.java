package com.example.api.user.service;

import com.example.model.User;
import reactor.core.publisher.Mono;

public interface UserService {

	Mono<User> getUserByLogin(String login);

	Mono<User> saveUser(User user);

	Mono<User> setRole(User user, String roleString);
}
