package com.example.api


import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class ApiApplicationTest extends Specification {

    def 'Should load context correctly'() {
        expect:
        true
    }

}
