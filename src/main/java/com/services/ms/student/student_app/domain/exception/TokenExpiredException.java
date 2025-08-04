package com.services.ms.student.student_app.domain.exception;

import com.services.ms.student.student_app.utils.ErrorCatalog;

public class TokenExpiredException extends RuntimeException {

    public TokenExpiredException() {
        super(ErrorCatalog.TOKEN_EXPIRED.getMessage());
    }

    public TokenExpiredException(String message) {
        super(message);
    }
}