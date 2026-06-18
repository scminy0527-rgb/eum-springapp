package com.app.springapp.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class LearningAnalysisException extends RuntimeException {
    private final HttpStatus httpStatus;

    public LearningAnalysisException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}