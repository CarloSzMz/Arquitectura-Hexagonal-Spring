package com.services.ms.student.student_app.application.ports.input;

import com.services.ms.student.student_app.domain.model.AuthToken;

public interface AuthenticationServicePort {

    AuthToken authenticate(String username, String password);
    
    AuthToken refreshToken(String refreshToken);
    
    void logout(String token);
    
    boolean validateToken(String token);
    
    String extractUsername(String token);
}