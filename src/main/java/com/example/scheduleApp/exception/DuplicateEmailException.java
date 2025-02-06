package com.example.scheduleApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Duplicate email")
public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String message) {
            super(message);
        }
}
