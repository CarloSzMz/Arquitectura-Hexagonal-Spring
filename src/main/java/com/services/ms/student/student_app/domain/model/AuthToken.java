package com.services.ms.student.student_app.domain.model;

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
public class AuthToken {

    private String token;
    private String refreshToken;
    private LocalDateTime expiresAt;
    private LocalDateTime refreshExpiresAt;
    private String tokenType;
    private User user;

    public boolean isExpired() {
        return expiresAt != null && LocalDateTime.now().isAfter(expiresAt);
    }

    public boolean isRefreshExpired() {
        return refreshExpiresAt != null && LocalDateTime.now().isAfter(refreshExpiresAt);
    }
}