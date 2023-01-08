package com.example.api.users.service;

import com.example.model.User;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<String> getPasswordById(int id);
    Mono<User> saveUser(User user);
}
