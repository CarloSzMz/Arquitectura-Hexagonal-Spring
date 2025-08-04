package com.services.ms.student.student_app.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tokens")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String token;

    @Column(name = "refresh_token", length = 1000)
    private String refreshToken;

    @Column(nullable = false)
    private String username;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @Builder.Default
    private boolean valid = true;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}