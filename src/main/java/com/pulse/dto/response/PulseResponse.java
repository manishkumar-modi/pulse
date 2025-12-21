package com.pulse.dto.response;

import java.time.Instant;
import java.util.List;

import org.slf4j.MDC;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * A generic response wrapper for API responses in the Pulse application.
 * Supports returning success
 * messages, error messages, validation errors, single entity payloads, or a
 * list of entities.
 *
 * <p>
 * Fields are only included in the JSON output if they are not null.
 * </p>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record PulseResponse<T>(

        /** true when the request was successful; false when there was an error. */
        boolean success,
        /** HTTP status code returned to the client. */
        int status,
        /** Human-readable message describing the result. */
        String message,
        /** Optional response payload when the request succeeds. */
        @JsonInclude(JsonInclude.Include.NON_EMPTY) T data,
        /** Optional error details when the request fails. */
        Error error,
        /** Correlation ID for the request, if provided. */
        String requestId,
        /** Timestamp for when the response was generated. */
        String timestamp) {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static record Error(

            /** Business or application-specific error code. */
            String errorCode,
            /** Error classification, for example VALIDATION or SYSTEM. */
            String errorMessage,
            /** API path where the error occurred. */
            String path, /** Specific field validation errors, if any. */
            List<FieldError> fieldErrors) {

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static record FieldError(

            /** Name of the field that failed validation. */
            String field,
            /** One or more validation error messages for the field. */
            List<String> messages) {

    }

    /*
     * Constructs a response with the current timestamp.
     *
     * @param success whether the request was successful
     *
     * @param status HTTP status code returned to the client
     *
     * @param message human-readable result message
     *
     * @param requestId optional request correlation identifier
     */
    // public PulseResponse(boolean success, int status, String message) {
    // this(success, status, message, null, null, MDC.get("requestId"),
    // Instant.now().toString());
    // }

    /**
     * Creates a successful response with a payload and generated timestamp.
     *
     * @param status    HTTP status code
     * @param message   human-readable result message
     * @param requestId optional request correlation identifier
     * @param data      response payload
     * @param <T>       payload type
     * @return a success response wrapper
     */
    public static <T> PulseResponse<T> created(T data) {

        return new PulseResponse<>(true, HttpStatus.CREATED.value(), null, data, null, MDC.get("requestId"),
                Instant.now().toString());

    }

    /**
     * Creates a successful response with no payload and generated timestamp.
     *
     * @param status    HTTP status code
     * @param message   human-readable result message
     * @param requestId optional request correlation identifier
     * @param <T>       payload type
     * @return a success response wrapper with no payload
     */
    public static <T> PulseResponse<T> success(int status, T data) {

        return new PulseResponse<>(true, status, null, data, null, MDC.get("requestId"), Instant.now().toString());

    }

    /**
     * Creates a successful response with no parameters, using default OK status
     * and no payload.
     *
     * @param <T> payload type
     * @return a success response wrapper with default status OK
     */
    public static <T> PulseResponse<T> success() {

        return new PulseResponse<>(true, HttpStatus.OK.value(), null, null, null, MDC.get("requestId"),
                Instant.now().toString());

    }

    public static <T> PulseResponse<T> error(String errorCode, String errorMessage) {

        Error error = new PulseResponse.Error(errorCode, errorMessage, null, null);
        return new PulseResponse<>(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), null, null, error,
                MDC.get("requestId"), Instant.now().toString());

    }

}
