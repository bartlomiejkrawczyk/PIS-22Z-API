package com.example.api.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.ArrayList;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TokenProvider {

	@Value("${spring.auth.expiration-time}")
	private Long expirationTime;

	@Value("${spring.auth.secret}")
	private String secret;

	@Value("${spring.auth.issuer}")
	private String issuer;

	private final JwtUserDetailsService userService;

	public String createToken(String username, int userType) {

		Claims claims = Jwts.claims().setSubject(username);

		var rolesList = new ArrayList<String>();

		var allRoles = Roles.values();

		for (int i = userType; i <= Roles.USER.ordinal(); i++) {
			rolesList.add(allRoles[i].toString());
		}

		claims.put("roles", rolesList);

		var now = System.currentTimeMillis();

		return Jwts.builder().setClaims(claims)
				.setSubject(username)
				.setIssuer(issuer)
				.setIssuedAt(new Date(now))
				.setExpiration(new Date(now + expirationTime))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public Authentication getAuthentication(String token) throws JwtException {
		var userDetails = userService.getUserDetails(token, secret);
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}
}
