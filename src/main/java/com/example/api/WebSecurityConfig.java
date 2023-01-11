package com.example.api;

import com.example.api.auth.JwtFilter;
import com.example.api.auth.Roles;
import com.example.api.auth.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class WebSecurityConfig {

	private final TokenProvider tokenProvider;

	@Bean
	public SecurityFilterChain configure(final HttpSecurity http) throws Exception {

		http.csrf().disable()
				.httpBasic().disable()
				.cors()
				.and()
				.authorizeHttpRequests()
				.antMatchers("/ping/**").permitAll()
				.antMatchers("/error/**").permitAll()
				.antMatchers("/user/login").permitAll()
				.antMatchers("/user/register").permitAll()
				.antMatchers("/user/set_role/**").hasRole(Roles.ADMIN.toString())
				.antMatchers("/hello/**").hasRole(Roles.MODERATOR.toString())
//				.antMatchers("/concept/**").hasRole(Roles.USER.toString())
				.anyRequest().permitAll()
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		JwtFilter jwtFilter = new JwtFilter(tokenProvider);

		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class).exceptionHandling()
				.authenticationEntryPoint((request, response, e) -> response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized"));

		return http.build();

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
