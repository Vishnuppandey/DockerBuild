package com.docker.security;



import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	@Value("${jwt.secret}")
	private String secretKey;

	
	public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }
	
	public String extractUsername(String token) {
		SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes()); // Use a consistent key
	    Claims claims = Jwts.parser()
	        .verifyWith(key)
	        .build()
	        .parseSignedClaims(token)
	        .getPayload();
	    return claims.getSubject();
	}
	
	public boolean isTokenValid(String token, UserDetails userDetails) {
	    final String username = extractUsername(token);
	    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	
	private boolean isTokenExpired(String token) {
	    SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
	    Claims claims = Jwts.parser()
	        .verifyWith(key)
	        .build()
	        .parseSignedClaims(token)
	        .getPayload();
	    return claims.getExpiration().before(new Date());
	}


}
