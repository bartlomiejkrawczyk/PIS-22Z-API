package com.example.api.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@RequestMapping("/ping")
public interface WebController {

    @GetMapping({"{phrase}", ""})
    Mono<String> ping(@PathVariable(required = false) String phrase);
}
