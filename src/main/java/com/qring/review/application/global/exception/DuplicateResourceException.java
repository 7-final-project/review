package com.qring.review.application.global.exception;

import lombok.Getter;

@Getter
public class DuplicateResourceException extends ReviewException {
    public DuplicateResourceException(String message) {
        super(ErrorCode.DUPLICATE_ERROR, message);
    }
}
