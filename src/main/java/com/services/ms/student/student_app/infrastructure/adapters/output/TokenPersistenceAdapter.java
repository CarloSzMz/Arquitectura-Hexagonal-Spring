package com.services.ms.student.student_app.infrastructure.adapters.output;

import com.services.ms.student.student_app.application.ports.output.TokenPersistencePort;
import com.services.ms.student.student_app.infrastructure.adapters.output.persistence.entity.TokenEntity;
import com.services.ms.student.student_app.infrastructure.adapters.output.persistence.repository.TokenJpaRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional
public class TokenPersistenceAdapter implements TokenPersistencePort {

    private final TokenJpaRepository tokenJpaRepository;

    @Override
    public void saveToken(String username, String token, String refreshToken) {
        // First invalidate any existing tokens for this user
        invalidateAllUserTokens(username);
        
        // Save new token
        TokenEntity tokenEntity = TokenEntity.builder()
                .username(username)
                .token(token)
                .refreshToken(refreshToken)
                .expiresAt(LocalDateTime.now().plusHours(24)) // 24 hours expiration
                .valid(true)
                .build();
                
        tokenJpaRepository.save(tokenEntity);
    }

    @Override
    public void invalidateToken(String token) {
        tokenJpaRepository.invalidateToken(token);
    }

    @Override
    public void invalidateAllUserTokens(String username) {
        tokenJpaRepository.invalidateAllUserTokens(username);
    }

    @Override
    public Optional<String> findTokenByUsername(String username) {
        return tokenJpaRepository.findByUsernameAndValidTrue(username)
                .map(TokenEntity::getToken);
    }

    @Override
    public boolean isTokenValid(String token) {
        return tokenJpaRepository.findByTokenAndValidTrue(token)
                .map(tokenEntity -> tokenEntity.getExpiresAt().isAfter(LocalDateTime.now()))
                .orElse(false);
    }

    @Override
    public void cleanExpiredTokens() {
        tokenJpaRepository.deleteExpiredTokens(LocalDateTime.now());
    }
}