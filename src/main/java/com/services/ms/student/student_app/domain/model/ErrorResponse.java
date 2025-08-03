package com.services.ms.student.student_app.domain.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Getter
@Setter
public class ErrorResponse {

    private String code;

    private String message;

    private List<String> details;

    private LocalDateTime timestamp;



}
