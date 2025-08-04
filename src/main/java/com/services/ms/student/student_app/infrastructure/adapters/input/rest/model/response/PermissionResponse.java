package com.services.ms.student.student_app.infrastructure.adapters.input.rest.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PermissionResponse {

    private Long id;
    private String name;
    private String description;
    private String resource;
    private String action;
}