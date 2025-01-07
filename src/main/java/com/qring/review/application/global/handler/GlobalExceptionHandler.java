package com.qring.review.application.global.handler;

import com.qring.review.application.global.dto.ResDTO;
import com.qring.review.application.global.exception.ReviewException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ReviewException.class})
    public ResponseEntity<ResDTO<Object>> ReviewExceptionHandler(ReviewException ex) {
        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(ex.getErrorCode().getCode())
                        .message(ex.getMessage())
                        .build(),
                ex.getErrorCode().getHttpStatus()
        );
    }
}
