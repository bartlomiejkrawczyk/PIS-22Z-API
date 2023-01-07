package com.example.api.users.service;

import com.example.api.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
// wszystkie pola jako final zostaną wstrzyknięte do komponentu gdy zostaną stworzone Biny
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    public Mono<String> getPasswordById(int id) {
        return Mono.justOrEmpty(userRepository.findPasswordById(id));
                // Mono.defer(
            //() -> Mono.justOrEmpty(userRepository.findPasswordById(id)) // lazy fetch - nie wyciągana lista
            // ).subscribeOn(scheduler);
            //.map(conceptMapper::entityToDefinition) // dlatego korzystamy z Def nie z konceptu

    }
}
