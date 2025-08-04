package com.services.ms.student.student_app.infrastructure.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.services.ms.student.student_app.domain.model.ErrorResponse;
import com.services.ms.student.student_app.utils.ErrorCatalog;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        
        log.error("Unauthorized error: {}", authException.getMessage());
        
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(ErrorCatalog.UNAUTHORIZED_ACCESS.getCode())
                .message(ErrorCatalog.UNAUTHORIZED_ACCESS.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        
        objectMapper.writeValue(response.getOutputStream(), errorResponse);
    }
}