package com.app.springapp.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class EduException extends RuntimeException {
    private HttpStatus httpStatus;

    public EduException(String message) {
        super(message);
    }
    public EduException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
