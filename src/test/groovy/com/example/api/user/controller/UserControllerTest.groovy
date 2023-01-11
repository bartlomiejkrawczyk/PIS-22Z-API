package com.example.api.user.controller

import com.example.api.auth.JwtUserDetailsService
import com.example.api.auth.Roles
import com.example.api.auth.TokenProvider
import com.example.api.user.entity.UserEntity
import com.example.api.user.mapper.UserMapperImpl
import com.example.api.user.repository.UserRepository
import com.example.api.user.service.UserServiceImpl
import com.example.model.User
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureException
import org.springframework.web.server.ResponseStatusException
import reactor.core.scheduler.Schedulers
import spock.lang.Specification
import spock.lang.Subject

import java.util.concurrent.Executors

class UserControllerTest extends Specification {


	def repository = Mock(UserRepository.class)
	def mapper = new UserMapperImpl()
	def scheduler = Schedulers.fromExecutor(Executors.newFixedThreadPool(1))
	def service = new UserServiceImpl(repository, mapper, scheduler)

	def tokenProvider = new TokenProvider(new JwtUserDetailsService())
	def anotherTokenProvider = new TokenProvider(new JwtUserDetailsService())


	@Subject
	def controller = new UserController(tokenProvider, service) {
		@Override
		boolean passwordsMatches(String password, String salt, String encodedPassword) {
			return true
		}
	}

	def 'Login endpoint should return valid token'() {
		given:
		repository.save(_ as UserEntity) >> { UserEntity entity -> entity }
		repository.findUserByLogin(_ as String) >> { String login -> Optional.ofNullable(new UserEntity(1, login, "email", "pass", "salt", Roles.USER.ordinal())) }

		tokenProvider.expirationTime = 600000
		tokenProvider.secret = "secret"
		tokenProvider.issuer = "issuer"

		expect:
		tokenProvider.getAuthentication(controller.getToken(user).block().getToken()) != null

		where:
		id || user
		1  || new User(1, "login", "email", "pass", "salt", Roles.USER.ordinal())
	}

	def 'Login not exists user should fail'() {
		given:
		repository.save(_ as UserEntity) >> { UserEntity entity -> entity }
		repository.findUserByLogin(_ as String) >> { String login -> Optional.ofNullable(null) }


		when:
		controller.getToken(new User(1, "login", "email", "pass", "salt", Roles.USER.ordinal())).block()

		then:
		thrown(ResponseStatusException)
	}

	def 'Authentication using invalid token should fail'() {
		given:
		repository.save(_ as UserEntity) >> { UserEntity entity -> entity }
		repository.findUserByLogin(_ as String) >> { String login -> Optional.ofNullable(new UserEntity(1, login, "email", "pass", "salt", Roles.USER.ordinal())) }

		tokenProvider.expirationTime = expTime
		tokenProvider.secret = tokenSecret
		tokenProvider.issuer = "issuer"

		anotherTokenProvider.expirationTime = expTime
		anotherTokenProvider.secret = verifySecret
		anotherTokenProvider.issuer = "issuer"

		when:
		anotherTokenProvider.getAuthentication(controller.getToken(user).block().getToken())

		then:
		thrown(exc)

		where:
		expTime | tokenSecret | verifySecret    | exc                 | user
		60000   | "secret"    | "anotherSecret" | SignatureException  | new User(1, "login", "email", "pass", "salt", Roles.USER.ordinal())
		0       | "secret"    | "secret"        | ExpiredJwtException | new User(1, "login", "email", "pass", "salt", Roles.USER.ordinal())
	}

	def 'Authentication using malformed token should fail'() {
		given:
		repository.save(_ as UserEntity) >> { UserEntity entity -> entity }
		repository.findUserByLogin(_ as String) >> { String login -> Optional.ofNullable(new UserEntity(1, login, "email", "pass", "salt", Roles.USER.ordinal())) }

		tokenProvider.expirationTime = 60000
		tokenProvider.secret = "secret"
		tokenProvider.issuer = "issuer"

		when:
		tokenProvider.getAuthentication("TOKEN")

		then:
		thrown(MalformedJwtException)
	}


	def 'Set role not exists user should fail'() {
		given:
		repository.save(_ as UserEntity) >> { UserEntity entity -> entity }
		repository.findUserByLogin(_ as String) >> Optional.empty()

		expect:
		controller.setRole(role, new User(1, "login", "email", "pass", "salt", Roles.USER.ordinal())).blockOptional().isEmpty()

		where:
		id | role
		1  | "admin"
		2  | "user"
		3  | "moderator"
		4  | "ADMIN"
		5  | "aDmin"
	}

	def 'Set not exist role should fail'() {
		given:
		repository.save(_ as UserEntity) >> { UserEntity entity -> entity }
		repository.findUserByLogin(_ as String) >> { String login -> Optional.ofNullable(user) }

		when:
		controller.setRole(role, new User(1, "login", "email", "pass", "salt", Roles.USER.ordinal())).block()

		then:
		thrown(IllegalArgumentException)

		where:
		user                                                                      | role
		new UserEntity(1, "login", "email", "pass", "salt", Roles.USER.ordinal()) | "not_role"
		null                                                                      | "not_role"
	}
}

