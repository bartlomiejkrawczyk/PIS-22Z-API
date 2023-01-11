package com.example.api.user.service

import com.example.api.auth.Roles
import com.example.api.user.entity.UserEntity
import com.example.api.user.mapper.UserMapperImpl
import com.example.api.user.repository.UserRepository
import com.example.model.User
import reactor.core.scheduler.Schedulers
import spock.lang.Specification
import spock.lang.Subject

import java.util.concurrent.Executors

class UserServiceTest extends Specification {

	def repository = Mock(UserRepository.class)
	def mapper = new UserMapperImpl()
	def scheduler = Schedulers.fromExecutor(Executors.newFixedThreadPool(1))

	@Subject
	def service = new UserServiceImpl(repository, mapper, scheduler)

	def 'Should retrieve user by login correctly'() {
		given:
		repository.findUserByLogin(login) >> Optional.ofNullable(entity)

		expect:
		service.getUserByLogin(login).block() == user

		where:
		login      | entity                                                           | user
		"login"    | new UserEntity(1, "login", "email@pw.edu.pl", "pass", "salt", 2) | new User(1, "login", "email@pw.edu.pl", "pass", "salt", 2)
		"not_user" | null                                                             | null
	}


	def 'Should save user correctly'() {
		given:
		repository.save(_ as UserEntity) >> { UserEntity entity -> entity }

		expect:
		service.saveUser(user).block().getLogin() == login
		service.saveUser(user).block().getEmail() == email
		service.saveUser(user).block().getType() == Roles.USER.ordinal()

		service.saveUser(user).block().getId() != id
		service.saveUser(user).block().getPassword() != password
		service.saveUser(user).block().getSalt() != salt


		where:
		id   | login   | email             | password | salt   | user
		9999 | "login" | "email@pw.edu.pl" | "pass"   | "salt" | new User(9999, "login", "email@pw.edu.pl", "pass", "salt", Roles.USER.ordinal())
		1000 | "login" | "email@pw.edu.pl" | "qwerty" | null   | new User(1000, "login", "email@pw.edu.pl", "qwerty", null, Roles.ADMIN.ordinal())
		1223 | "login" | "email@pw.edu.pl" | "qwerty" | null   | new User(1223, "login", "email@pw.edu.pl", "qwerty", null, Roles.MODERATOR.ordinal())

	}

	def 'Should update user correctly'() {
		given:
		repository.save(_ as UserEntity) >> { UserEntity entity -> entity }
		repository.findUserByLogin(_ as String) >> { String login -> Optional.ofNullable(new UserEntity(1, login, "email", "pass", "salt", role)) }

		expect:
		def result = service.setRole(user, roleString).block()
		result.getType() == role
		result.getPassword() == null
		result.getSalt() == null


		where:
		roleString  | role                      | user
		"admin"     | Roles.ADMIN.ordinal()     | new User(9999, "login", "email@pw.edu.pl", "pass", "salt", Roles.USER.ordinal())
		"user"      | Roles.USER.ordinal()      | new User(1000, "login", null, null, null, Roles.ADMIN.ordinal())
		"moderator" | Roles.MODERATOR.ordinal() | new User(1223, "login", null, null, null, Roles.MODERATOR.ordinal())

	}

	def 'Should fail update not existing user'() {
		given:
		repository.save(_ as UserEntity) >> { UserEntity entity -> entity }
		repository.findUserByLogin(_ as String) >> Optional.empty()

		expect:
		service.setRole(new User(9999, "login", "email@pw.edu.pl", "pass", "salt", Roles.USER.ordinal()), "admin").blockOptional().isEmpty()
	}

	def 'Should fail update with not existing role'() {
		given:
		repository.save(_ as UserEntity) >> { UserEntity entity -> entity }
		repository.findUserByLogin(_ as String) >> { String login -> Optional.ofNullable(new UserEntity(1, login, "email", "pass", "salt", null)) }

		when:
		service.setRole(new User(9999, "login", "email@pw.edu.pl", "pass", "salt", Roles.USER.ordinal()), "not_role")

		then:
		thrown(IllegalArgumentException)
	}
}
