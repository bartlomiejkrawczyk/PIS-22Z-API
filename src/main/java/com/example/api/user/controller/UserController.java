package com.example.api.user.controller;

import com.example.api.auth.TokenProvider;
import com.example.api.auth.model.Authorization;
import com.example.api.user.service.UserService;
import com.example.model.User;
import java.io.IOException;
import java.util.NoSuchElementException;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserController {

	private final TokenProvider tokenProvider;
	private final UserService userService;
	private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	public boolean passwordsMatches(String password, String salt, String encodedPassword) {
		return encoder.matches(password + salt, encodedPassword);
	}

	@PostMapping("/login")
	public Mono<Authorization> getToken(@RequestBody User requestUser) {
		return userService.getUserByLogin(requestUser.getLogin())
				.filter(user -> passwordsMatches(requestUser.getPassword(), user.getSalt(), user.getPassword()))
				.map(user -> new Authorization(tokenProvider.createToken(user.getLogin(), user.getType())))
				.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED)));
	}

	@PostMapping("/register")
	public Mono<Authorization> addUser(@RequestBody User requestUser) {
		return userService.saveUser(requestUser)
				.map(user -> new Authorization(tokenProvider.createToken(user.getLogin(), user.getType())));
	}

	@PatchMapping("/set_role/{role}")
	public Mono<User> setRole(@PathVariable("role") String roleString, @RequestBody User user) {
		return userService.setRole(user, roleString);
	}

	@ExceptionHandler({ConstraintViolationException.class})
	public void handleConstraintViolationException(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value(), "User with this login or email already exists.");
	}

	@ExceptionHandler({NoSuchElementException.class, IllegalArgumentException.class})
	public void handleNoSuchElementException(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.NOT_FOUND.value(), "User or Role does not exists.");
	}
}
