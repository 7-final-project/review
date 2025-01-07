package com.qring.review.application.global.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends ReviewException {
    public EntityNotFoundException(String message) {
        super(ErrorCode.NOT_FOUND_ERROR, message);
    }
}
