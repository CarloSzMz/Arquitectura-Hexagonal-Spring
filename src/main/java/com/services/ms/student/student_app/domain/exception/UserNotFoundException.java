package com.services.ms.student.student_app.domain.exception;

import com.services.ms.student.student_app.utils.ErrorCatalog;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super(ErrorCatalog.USER_NOT_FOUND.getMessage());
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}