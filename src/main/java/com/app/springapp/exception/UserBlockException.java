package com.app.springapp.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class UserBlockException extends RuntimeException {
    private HttpStatus status;

    public UserBlockException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
