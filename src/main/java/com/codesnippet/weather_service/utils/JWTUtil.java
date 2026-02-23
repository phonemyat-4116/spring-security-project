package com.codesnippet.weather_service.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey getKey(){
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // 1️⃣ Generate JWT token for authenticated user
    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 2️⃣ Extract username (subject) from JWT
    public String extractUsername(String token){

        return extractClaims(token).getSubject();
    }

    // 3️⃣ Parse JWT and extract all claims
    private Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 4️⃣ Validate token against user details
    public boolean validateToken(String username, UserDetails userDetails, String token){
        // TODO - check if the username is same as username in UserDetails
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // 5️⃣ Check if token expiration time has passed
    private boolean isTokenExpired(String token) {
        // TODO - check if token is not expired
        return extractClaims(token).getExpiration().before(new Date());
    }


}
