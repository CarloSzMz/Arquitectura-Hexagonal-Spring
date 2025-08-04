package com.services.ms.student.student_app.infrastructure.adapters.input.rest.model.response;

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
public class RoleResponse {

    private Long id;
    private String name;
    private String description;
    private Set<PermissionResponse> permissions;
}