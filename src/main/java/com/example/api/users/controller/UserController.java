package com.example.api.users.controller;

import com.example.api.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserController {

    private final UserService userService;

    @GetMapping("/login/{id}")
    public Mono<String> getPasswordById(@PathVariable int id) {
        return userService.getPasswordById(id);
    }

//    @GetMapping("{id}")
//    public Mono<Concept> getConceptById(@PathVariable int id) {
//        return conceptService.getConceptById(id);
//    }
//
//    @PostMapping("{sectionId}")
//    public Mono<Concept> saveConcept(@RequestBody Concept concept, @PathVariable int sectionId) {
//        return conceptService.saveConcept(concept, sectionId);
    }
