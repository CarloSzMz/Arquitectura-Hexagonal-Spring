package com.services.ms.student.student_app.application.ports.output;

import com.services.ms.student.student_app.domain.model.AuthToken;
import com.services.ms.student.student_app.domain.model.User;

public interface JwtTokenPort {

    AuthToken generateToken(User user);
    
    String generateAccessToken(User user);
    
    String generateRefreshToken(User user);
    
    boolean validateToken(String token);
    
    String extractUsername(String token);
    
    boolean isTokenExpired(String token);
}