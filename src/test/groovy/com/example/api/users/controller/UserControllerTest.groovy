package com.example.api.users.controller

import com.example.api.users.controller.UserController
import com.example.api.users.service.UserService
import com.example.model.User
import reactor.core.publisher.Mono
import spock.lang.Specification
import spock.lang.Subject

class UserControllerTest extends Specification {

	def service = Mock(UserService)

	@Subject
	def controller = new UserController(service)

	def 'When getting password by id, the response should be triggered'() {
		given:
		service.getPasswordById(_ as Integer) >> Mono.justOrEmpty(response) // załadając że zwróci taką odpowiedź

		expect: // co oczekujesz po wyjściu
		controller.getPasswordById(id).block() == response // mono nie zwróci wartości póki nie wwoła się na nim block

		where: // tabelka parametrów (każdy kolejny jest innym wierszem)
		id || response
		0  || 'haslo'
		1  || 'haslo2'
		2  || 'haslo3'
	}

	def 'When saving user, controller should propagate method call to service'() {
		given:
		service.saveUser(_ as User) >> Mono.justOrEmpty(user)
		service.saveUser(null) >> Mono.empty()

		expect:
		controller.saveUser(user).block() == user

		where:
		id | user
		0 | null
		1 | new User()
	}
}
