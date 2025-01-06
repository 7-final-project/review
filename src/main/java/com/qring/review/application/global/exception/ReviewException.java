package com.qring.review.application.global.exception;

import lombok.Getter;

@Getter
public class ReviewException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String message;

    public ReviewException(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }


}
