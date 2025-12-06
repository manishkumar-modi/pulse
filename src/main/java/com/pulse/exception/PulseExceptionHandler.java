package com.pulse.exception;

import com.pulse.model.response.PulseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for REST APIs in the Pulse application.
 * This class handles specific exceptions and provides structured error responses.
 */
@Slf4j
@RestControllerAdvice
public class PulseExceptionHandler {

    /**
     * Handles all other unexpected exceptions globally.
     *
     * @param exception the unhandled exception.
     * @return ResponseEntity with a generic error message and HTTP 500 status.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<PulseResponse> handleGenericException(Exception exception) {

        LOGGER.error("Unhandled exception {}", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new PulseResponse("An unexpected error occurred."));

    }

}
