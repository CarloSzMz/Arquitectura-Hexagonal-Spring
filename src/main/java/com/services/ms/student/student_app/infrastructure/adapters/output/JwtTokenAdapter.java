package com.services.ms.student.student_app.infrastructure.adapters.output;

import com.services.ms.student.student_app.application.ports.output.JwtTokenPort;
import com.services.ms.student.student_app.domain.model.AuthToken;
import com.services.ms.student.student_app.domain.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtTokenAdapter implements JwtTokenPort {

    @Value("${security.jwt.secret:mySecretKey}")
    private String secretKey;

    @Value("${security.jwt.expiration:86400000}") // 24 hours in milliseconds
    private long jwtExpiration;

    @Value("${security.jwt.refresh-expiration:604800000}") // 7 days in milliseconds
    private long refreshExpiration;

    @Override
    public AuthToken generateToken(User user) {
        String accessToken = generateAccessToken(user);
        String refreshToken = generateRefreshToken(user);
        
        LocalDateTime expiresAt = LocalDateTime.now().plusSeconds(jwtExpiration / 1000);
        LocalDateTime refreshExpiresAt = LocalDateTime.now().plusSeconds(refreshExpiration / 1000);
        
        return AuthToken.builder()
                .token(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresAt(expiresAt)
                .refreshExpiresAt(refreshExpiresAt)
                .user(user)
                .build();
    }

    @Override
    public String generateAccessToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("email", user.getEmail());
        
        if (user.getRoles() != null) {
            claims.put("roles", user.getRoles().stream()
                    .map(role -> role.getName())
                    .toList());
        }
        
        return createToken(claims, user.getUsername(), jwtExpiration);
    }

    @Override
    public String generateRefreshToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("tokenType", "refresh");
        return createToken(claims, user.getUsername(), refreshExpiration);
    }

    @Override
    public boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private String createToken(Map<String, Object> claims, String subject, long expiration) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}