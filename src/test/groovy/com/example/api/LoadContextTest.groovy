package com.example.api

import com.example.api.web.WebController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class LoadContextTest extends Specification {

    @Autowired
    WebController webController

    def 'Should create beans when context is loaded'() {
        expect: 'the WebController is created'
        webController
    }

}
