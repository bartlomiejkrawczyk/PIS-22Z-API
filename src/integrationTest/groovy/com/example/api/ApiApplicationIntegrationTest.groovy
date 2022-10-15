package com.example.api

import spock.lang.Specification

class ApiApplicationIntegrationTest extends Specification {

    def 'Should start spring application when main is called'() {
        expect:
        ApiApplication.main()
    }

}
