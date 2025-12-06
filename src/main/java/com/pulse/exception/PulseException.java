package com.pulse.exception;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * Custom runtime exception used within the Pulse application to handle
 * business-specific and system-level errors with an associated error code.
 *
 * <p>
 * The class extends {@link RuntimeException}, allowing it to be thrown
 * without mandatory catch blocks, making it suitable for service-layer or
 * business-rule validations in the Pulse application.</p>
 */
@Slf4j
@Getter
@ToString
public class PulseException extends RuntimeException {

    private final String code;
    private final String message;
    private Exception exception;

    /**
     * Creates a new {@code PulseException} with an error code and message.
     *
     * @param code    A unique error code representing the exception type.
     * @param message A descriptive message explaining the reason for the exception.
     */
    public PulseException(String code, String message) {

        super(message);
        this.code = code;
        this.message = message;
        LOGGER.error(this.code, this.message);

    }

    /**
     * Creates a new {@code PulseException} with an error code, message,
     * and an underlying cause.
     *
     * @param code      A unique error code representing the exception type.
     * @param message   A descriptive message explaining the reason for the exception.
     * @param exception The underlying exception that caused this exception.
     */
    public PulseException(String code, String message, Exception exception) {

        super(message);
        this.code = code;
        this.message = message;
        this.exception = exception;
        LOGGER.error(this.code, this.message, this.exception);

    }

}
