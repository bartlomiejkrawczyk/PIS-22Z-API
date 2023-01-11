package com.example.api.user.service;

import com.example.api.auth.Roles;
import com.example.api.user.mapper.UserMapper;
import com.example.api.user.repository.UserRepository;
import com.example.model.User;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final Scheduler scheduler;
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public Mono<User> getUserByLogin(String login) {
		return Mono.defer(
						() -> Mono.justOrEmpty(userRepository.findUserByLogin(login)))
				.subscribeOn(scheduler)
				.map(userMapper::entityToDto);
	}

	@Transactional
	public Mono<User> saveUser(User user) {
		var salt = BCrypt.gensalt();
		var encodedPassword = passwordEncoder.encode(user.getPassword() + salt);

		user.setId(0);
		user.setPassword(encodedPassword);
		user.setSalt(salt);
		user.setType(Roles.USER.ordinal());

		return Mono.justOrEmpty(user)
				.map(userMapper::dtoToEntity)
				.doOnNext(entity -> log.info("Saving user to database: {}", entity.getLogin()))
				.map(userRepository::save)
				.doOnNext(entity -> log.info("User saved: {}", entity.getLogin()))
				.subscribeOn(scheduler)
				.map(userMapper::entityToDto);
	}

	@Transactional
	public Mono<User> setRole(User user, String roleString) {
		var role = Roles.valueOf(roleString.toUpperCase());

		return Mono.defer(
						() -> Mono.justOrEmpty(userRepository.findUserByLogin(user.getLogin()))
				)
				.doOnNext(it -> log.info("Updating role on user with login: {}", user.getLogin()))
				.map(it -> {
					it.setType(role.ordinal());
					return it;
				})
				.map(userRepository::save)
				.doOnNext(it -> log.info("Role set to: {}", role))
				.map(userMapper::entityToDto)
				.map(it -> {
					it.setPassword(null);
					it.setSalt(null);
					return it;
				})
				.subscribeOn(scheduler);
	}
}
