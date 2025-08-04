package com.services.ms.student.student_app.domain.exception;

import com.services.ms.student.student_app.utils.ErrorCatalog;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {
        super(ErrorCatalog.INVALID_CREDENTIALS.getMessage());
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }
}