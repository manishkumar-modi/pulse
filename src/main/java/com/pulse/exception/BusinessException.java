package com.pulse.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final HttpStatus status;

    private final String messageKey;

    private final Object[] args;

    public BusinessException(HttpStatus status, String messageKey, Object... args) {

        this.status = status;
        this.messageKey = messageKey;
        this.args = args;

    }

}
