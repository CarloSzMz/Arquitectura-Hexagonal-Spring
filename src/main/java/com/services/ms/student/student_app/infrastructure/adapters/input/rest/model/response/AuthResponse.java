package com.services.ms.student.student_app.infrastructure.adapters.input.rest.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private String refreshToken;
    private String tokenType;
    private LocalDateTime expiresAt;
    private UserResponse user;
}