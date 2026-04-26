package com.aj.travel.auth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String jwtSecret;
    private final long jwtExpirationInMs;

    private SecretKey secretKey;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String jwtSecret,
            @Value("${jwt.expiration:3600000}") long jwtExpirationInMs // default 1 hour
    ) {
        this.jwtSecret = jwtSecret;
        this.jwtExpirationInMs = jwtExpirationInMs;
    }

    @PostConstruct
    public void init() {
        if (jwtSecret == null || jwtSecret.length() < 32) {
            throw new IllegalArgumentException("JWT secret must be at least 32 characters long");
        }

        this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    // 🔐 Generate token
    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // 📥 Extract username
    public String getUsernameFromToken(String token) {
        return extractClaims(token).getSubject();
    }

    // ✅ Validate token
    public boolean validateToken(String token) {
        try {
            Claims claims = extractClaims(token);
            return !claims.getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    // 🔍 Extract claims (centralized parsing)
    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}