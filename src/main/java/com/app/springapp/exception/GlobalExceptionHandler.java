package com.app.springapp.exception;

import com.app.springapp.domain.dto.response.ApiResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FileException.class)
    public ResponseEntity<ApiResponseDTO> handleFileException(FileException e) {
        HttpStatus status = e.getHttpStatus() != null ? e.getHttpStatus() : HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status)
                .body(ApiResponseDTO.of(false, e.getMessage()));
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ApiResponseDTO> handleUserException(UserException e) {
        HttpStatus status = e.getHttpStatus() != null ? e.getHttpStatus() : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status)
                .body(ApiResponseDTO.of(false, e.getMessage()));
    }

    @ExceptionHandler(PostException.class)
    public ResponseEntity<ApiResponseDTO> handlePostException(PostException e) {
        HttpStatus status = e.getStatus() != null ? e.getStatus() : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status)
                .body(ApiResponseDTO.of(false, e.getMessage()));
    }

    @ExceptionHandler(CommentException.class)
    public ResponseEntity<ApiResponseDTO> handleCommentException(CommentException e) {
        HttpStatus status = e.getStatus() != null ? e.getStatus() : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status)
                .body(ApiResponseDTO.of(false, e.getMessage()));
    }

    @ExceptionHandler(ChatException.class)
    public ResponseEntity<ApiResponseDTO> handleChatException(ChatException e) {
        HttpStatus status = e.getStatus() != null ? e.getStatus() : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status)
                .body(ApiResponseDTO.of(false, e.getMessage()));
    }

    @ExceptionHandler(EduException.class)
    public ResponseEntity<ApiResponseDTO> handleEduException(EduException e) {
        HttpStatus status = e.getHttpStatus() != null ? e.getHttpStatus() : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status)
                .body(ApiResponseDTO.of(false, e.getMessage()));
    }

    @ExceptionHandler(MyPageException.class)
    public ResponseEntity<ApiResponseDTO> handleMyPageException(MyPageException e) {
        HttpStatus status = e.getHttpStatus() != null ? e.getHttpStatus() : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status)
                .body(ApiResponseDTO.of(false, e.getMessage()));
    }

    @ExceptionHandler(JwtTokenException.class)
    public ResponseEntity<ApiResponseDTO> handleJwtTokenException(JwtTokenException e) {
        HttpStatus status = e.getHttpStatus() != null ? e.getHttpStatus() : HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status)
                .body(ApiResponseDTO.of(false, e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO> handleException(Exception e) {
        log.error("[500] 서버 내부 오류: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseDTO.of(false, "서버 내부 오류가 발생했습니다."));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Void> handleNoResourceFound(NoResourceFoundException e) {
        return ResponseEntity.notFound().build();
    }
}
