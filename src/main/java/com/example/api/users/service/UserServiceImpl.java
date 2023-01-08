package com.example.api.users.service;

import com.example.api.users.repository.UserRepository;
import com.example.api.users.mapper.UserMapper;
import com.example.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
// wszystkie pola jako final zostaną wstrzyknięte do komponentu gdy zostaną stworzone Biny
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final Scheduler scheduler;
    public Mono<String> getPasswordById(int id) {
        return Mono.justOrEmpty(userRepository.findPasswordById(id));
                // Mono.defer(
            //() -> Mono.justOrEmpty(userRepository.findPasswordById(id)) // lazy fetch - nie wyciągana lista
            // ).subscribeOn(scheduler);
            //.map(conceptMapper::entityToDefinition) // dlatego korzystamy z Def nie z konceptu

    }
    public Mono<User> saveUser(User user) {
        return Mono.justOrEmpty(user)
                .map(userMapper::dtoToEntity)// mapuję na możliwy do zapisania
                .doOnNext(entity -> log.info("Saving entity to database: {}", entity))
                .map(userRepository::save) // zapisuję
                .doOnNext(entity -> log.info("Entity saved: {}", entity))
                .subscribeOn(scheduler)
                .map(userMapper::entityToUser); // mapuję z tego w bazie na ten w api
    }

}
