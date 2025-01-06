package com.qring.review.application.global.exception;

import lombok.Getter;

@Getter
public class UnauthorizedAccessException extends ReviewException {
    public UnauthorizedAccessException(String message) {
        super(ErrorCode.AUTHORITY_ERROR, message);
    }
}
