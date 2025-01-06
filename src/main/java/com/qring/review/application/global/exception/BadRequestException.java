package com.qring.review.application.global.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends ReviewException {
    public BadRequestException(String message) {
        super(ErrorCode.BAD_REQUEST_ERROR, message);
    }
}
