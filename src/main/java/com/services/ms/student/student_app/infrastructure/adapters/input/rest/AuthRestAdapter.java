package com.services.ms.student.student_app.infrastructure.adapters.input.rest;

import com.services.ms.student.student_app.application.ports.input.AuthenticationServicePort;
import com.services.ms.student.student_app.domain.model.AuthToken;
import com.services.ms.student.student_app.infrastructure.adapters.input.rest.mapper.AuthRestMapper;
import com.services.ms.student.student_app.infrastructure.adapters.input.rest.model.request.LoginRequest;
import com.services.ms.student.student_app.infrastructure.adapters.input.rest.model.request.RefreshTokenRequest;
import com.services.ms.student.student_app.infrastructure.adapters.input.rest.model.response.AuthResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthRestAdapter {

    private final AuthenticationServicePort authenticationServicePort;
    private final AuthRestMapper authRestMapper;

    @PostMapping("/v1/api/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        AuthToken authToken = authenticationServicePort.authenticate(
                loginRequest.getUsername(), 
                loginRequest.getPassword()
        );

        AuthResponse response = authRestMapper.toAuthResponse(authToken);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/v1/api/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshRequest) {
        AuthToken authToken = authenticationServicePort.refreshToken(refreshRequest.getRefreshToken());
        
        AuthResponse response = authRestMapper.toAuthResponse(authToken);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/v1/api/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String token = extractTokenFromRequest(request);
        if (token != null) {
            authenticationServicePort.logout(token);
        }
        return ResponseEntity.ok().build();
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}