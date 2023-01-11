package com.example.api.auth;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService {

	private static final String ROLES = "roles";
	private static final String ROLE_PREFIX = "ROLE_";

	public UserDetails getUserDetails(String token, String secretKey) throws JwtException {
		var parsedJwt = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		var username = parsedJwt.getSubject();

		var authorities = new ArrayList<SimpleGrantedAuthority>();

		if (parsedJwt.get(ROLES) != null) {
			var roles = (List<?>) parsedJwt.get(ROLES);

			for (var role : roles) {
				authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + role));
			}

		}

		return new User(username, "", authorities);
	}
}
