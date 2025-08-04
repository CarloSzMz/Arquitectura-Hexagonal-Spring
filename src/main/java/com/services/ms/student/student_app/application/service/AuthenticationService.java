package com.services.ms.student.student_app.application.service;

import com.services.ms.student.student_app.application.ports.input.AuthenticationServicePort;
import com.services.ms.student.student_app.application.ports.output.JwtTokenPort;
import com.services.ms.student.student_app.application.ports.output.TokenPersistencePort;
import com.services.ms.student.student_app.application.ports.output.UserPersistencePort;
import com.services.ms.student.student_app.domain.exception.InvalidCredentialsException;
import com.services.ms.student.student_app.domain.exception.TokenExpiredException;
import com.services.ms.student.student_app.domain.exception.UserNotFoundException;
import com.services.ms.student.student_app.domain.model.AuthToken;
import com.services.ms.student.student_app.domain.model.User;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthenticationServicePort {

    private final UserPersistencePort userPersistencePort;
    private final JwtTokenPort jwtTokenPort;
    private final TokenPersistencePort tokenPersistencePort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthToken authenticate(String username, String password) {
        User user = userPersistencePort.findByUsername(username)
                .orElseThrow(InvalidCredentialsException::new);

        if (!user.isEnabled()) {
            throw new InvalidCredentialsException("User account is disabled");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        AuthToken authToken = jwtTokenPort.generateToken(user);
        
        // Save token for validation
        tokenPersistencePort.saveToken(
            username, 
            authToken.getToken(), 
            authToken.getRefreshToken()
        );

        return authToken;
    }

    @Override
    public AuthToken refreshToken(String refreshToken) {
        if (!jwtTokenPort.validateToken(refreshToken)) {
            throw new TokenExpiredException("Invalid refresh token");
        }

        String username = jwtTokenPort.extractUsername(refreshToken);
        User user = userPersistencePort.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        if (!user.isEnabled()) {
            throw new InvalidCredentialsException("User account is disabled");
        }

        // Invalidate old tokens
        tokenPersistencePort.invalidateAllUserTokens(username);

        // Generate new tokens
        AuthToken newAuthToken = jwtTokenPort.generateToken(user);
        
        tokenPersistencePort.saveToken(
            username, 
            newAuthToken.getToken(), 
            newAuthToken.getRefreshToken()
        );

        return newAuthToken;
    }

    @Override
    public void logout(String token) {
        if (jwtTokenPort.validateToken(token)) {
            String username = jwtTokenPort.extractUsername(token);
            tokenPersistencePort.invalidateAllUserTokens(username);
        }
    }

    @Override
    public boolean validateToken(String token) {
        if (!jwtTokenPort.validateToken(token)) {
            return false;
        }
        
        return tokenPersistencePort.isTokenValid(token);
    }

    @Override
    public String extractUsername(String token) {
        return jwtTokenPort.extractUsername(token);
    }
}