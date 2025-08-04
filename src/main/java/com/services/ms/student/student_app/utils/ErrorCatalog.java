package com.services.ms.student.student_app.utils;

import lombok.Getter;

@Getter
public enum ErrorCatalog {

    STUDENT_NOT_FOUND("ERR_STUDENT_001", "Student not found"),
    INVALID_STUDENT("ERR_STUDENT_002", "Invalid student parameters"),
    INVALID_REQUEST("ERR_REQ_001", "Invalid request parameters"),
    GENERIC_ERROR("ERR_GEN_001", "An unexpected error occurred"),
    
    // Security related errors
    USER_NOT_FOUND("ERR_AUTH_001", "User not found"),
    INVALID_CREDENTIALS("ERR_AUTH_002", "Invalid username or password"),
    TOKEN_EXPIRED("ERR_AUTH_003", "Token has expired"),
    UNAUTHORIZED_ACCESS("ERR_AUTH_004", "Unauthorized access"),
    TOKEN_INVALID("ERR_AUTH_005", "Invalid token"),
    USER_DISABLED("ERR_AUTH_006", "User account is disabled"),
    INSUFFICIENT_PERMISSIONS("ERR_AUTH_007", "Insufficient permissions");

    private final String code;

    private final String message;

    ErrorCatalog(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
