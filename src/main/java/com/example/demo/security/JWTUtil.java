package com.example.demo.security;

import java.util.Collections;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JWTUtil {

	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private Long expiration;
	
	public String generateToken(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
	}
	
	static Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		
		if (token != null) {
			// faz parse do token
			String user = Jwts.parser()
					.setSigningKey("Serratec".getBytes())
					.parseClaimsJws(token.replace("Bearer", ""))
					.getBody()
					.getSubject();
			
			System.out.println("Oi"+user);
			if (user != null) {
				return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
			}
		
		}
		return null;
	}
}

