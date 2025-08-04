package com.services.ms.student.student_app.domain.model;

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
public class User {

    private Long id;
    private String username;
    private String email;
    private String password;
    private boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
    private Set<Role> roles;

    public boolean hasRole(String roleName) {
        return roles != null && roles.stream()
                .anyMatch(role -> role.getName().equals(roleName));
    }

    public boolean hasAnyRole(String... roleNames) {
        if (roles == null) return false;
        for (String roleName : roleNames) {
            if (hasRole(roleName)) return true;
        }
        return false;
    }
}