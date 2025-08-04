package com.services.ms.student.student_app.infrastructure.adapters.input.rest.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
    private Set<RoleResponse> roles;
}