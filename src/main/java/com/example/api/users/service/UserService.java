package com.example.api.users.service;

import reactor.core.publisher.Mono;

public interface UserService {

    Mono<String> getPasswordById(int id);
}
