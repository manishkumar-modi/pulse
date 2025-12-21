package com.pulse.exception;

import lombok.Getter;

@Getter
public class PulseException extends RuntimeException {

    private final int status;
    private final String errorCode;

    public PulseException(int status, String errorCode, String message) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
    }

    public static PulseException notFound(String message) {
        return new PulseException(404, "NOT_FOUND", message);
    }

    public static PulseException badRequest(String message) {
        return new PulseException(400, "BAD_REQUEST", message);
    }

    public static PulseException internalError(String message) {
        return new PulseException(500, "INTERNAL_SERVER_ERROR", message);
    }
}
