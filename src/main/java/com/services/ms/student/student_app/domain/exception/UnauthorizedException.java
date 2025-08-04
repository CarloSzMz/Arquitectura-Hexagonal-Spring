package com.services.ms.student.student_app.domain.exception;

import com.services.ms.student.student_app.utils.ErrorCatalog;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException() {
        super(ErrorCatalog.UNAUTHORIZED_ACCESS.getMessage());
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}