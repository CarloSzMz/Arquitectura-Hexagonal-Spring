package com.services.ms.student.student_app.application.ports.output;

import java.util.Optional;

public interface TokenPersistencePort {

    void saveToken(String username, String token, String refreshToken);
    
    void invalidateToken(String token);
    
    void invalidateAllUserTokens(String username);
    
    Optional<String> findTokenByUsername(String username);
    
    boolean isTokenValid(String token);
    
    void cleanExpiredTokens();
}