package com.services.ms.student.student_app.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    private Long id;
    private String name;
    private String description;
    private Set<Permission> permissions;

    public boolean hasPermission(String permissionName) {
        return permissions != null && permissions.stream()
                .anyMatch(permission -> permission.getName().equals(permissionName));
    }
}