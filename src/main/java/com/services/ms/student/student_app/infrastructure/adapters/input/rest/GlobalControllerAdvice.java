package com.services.ms.student.student_app.infrastructure.adapters.input.rest;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.services.ms.student.student_app.domain.exception.StudentNotFoundException;
import com.services.ms.student.student_app.domain.exception.UserNotFoundException;
import com.services.ms.student.student_app.domain.exception.InvalidCredentialsException;
import com.services.ms.student.student_app.domain.exception.TokenExpiredException;
import com.services.ms.student.student_app.domain.exception.UnauthorizedException;
import com.services.ms.student.student_app.domain.model.ErrorResponse;
import com.services.ms.student.student_app.utils.ErrorCatalog;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(StudentNotFoundException.class)
    public ErrorResponse handleStudentNotFoundException() {
        return ErrorResponse.builder()
                .code(ErrorCatalog.STUDENT_NOT_FOUND.getCode())
                .message(ErrorCatalog.STUDENT_NOT_FOUND.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        
        // Determine if it's a student-related validation or generic
        String objectName = result.getObjectName();
        ErrorCatalog errorCatalog = objectName.toLowerCase().contains("student") 
            ? ErrorCatalog.INVALID_STUDENT 
            : ErrorCatalog.INVALID_REQUEST;

        return ErrorResponse.builder()
                .code(errorCatalog.getCode())
                .message(errorCatalog.getMessage())
                .details(result.getFieldErrors().stream().map(FieldError::getDefaultMessage)
                        .collect(Collectors.toList()))
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorResponse handleUserNotFoundException() {
        return ErrorResponse.builder()
                .code(ErrorCatalog.USER_NOT_FOUND.getCode())
                .message(ErrorCatalog.USER_NOT_FOUND.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidCredentialsException.class)
    public ErrorResponse handleInvalidCredentialsException() {
        return ErrorResponse.builder()
                .code(ErrorCatalog.INVALID_CREDENTIALS.getCode())
                .message(ErrorCatalog.INVALID_CREDENTIALS.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(TokenExpiredException.class)
    public ErrorResponse handleTokenExpiredException() {
        return ErrorResponse.builder()
                .code(ErrorCatalog.TOKEN_EXPIRED.getCode())
                .message(ErrorCatalog.TOKEN_EXPIRED.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(UnauthorizedException.class)
    public ErrorResponse handleUnauthorizedException(UnauthorizedException ex) {
        return ErrorResponse.builder()
                .code(ErrorCatalog.UNAUTHORIZED_ACCESS.getCode())
                .message(ex.getMessage() != null ? ex.getMessage() : ErrorCatalog.UNAUTHORIZED_ACCESS.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleGenericException(Exception ex) {
        return ErrorResponse.builder()
                .code(ErrorCatalog.GENERIC_ERROR.getCode())
                .message(ErrorCatalog.GENERIC_ERROR.getMessage())
                .details(Collections.singletonList(ex.getMessage()))
                .timestamp(LocalDateTime.now())
                .build();
    }

}
