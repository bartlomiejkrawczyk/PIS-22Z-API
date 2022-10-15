package com.example.api.web;

import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@NoArgsConstructor
public class WebControllerImpl implements WebController {

    @Override
    public Mono<String> ping(String phrase) {
        return Mono.justOrEmpty(phrase)
                .map(name -> String.format("Hello, %s!", name))
                .defaultIfEmpty("Hello, World!");
    }

}
