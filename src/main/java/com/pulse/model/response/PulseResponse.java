package com.pulse.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

/**
 * A generic response wrapper for API responses in the Pulse application.
 * Supports returning success messages, error messages, validation errors,
 * single user data, or a list of users.
 * <p>
 * Fields are only included in the JSON output if they are not null.
 * </p>
 */
@Data
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PulseResponse {

    /**
     * Optional response code representing the status of the operation.
     */
    private String code;

    /**
     * Optional human-readable message for the client.
     */
    private String message;

    /**
     * A map of field-specific error messages used for validation feedback.
     * Key = field name, Value = error message.
     */
    private Map<String, String> fieldErrors;

    /**
     * Constructs a response with only a message.
     *
     * @param message a general response message.
     */
    public PulseResponse(String message) {
        this.message = message;
    }

    /**
     * Constructs a response with field-specific validation errors.
     *
     * @param fieldErrors map containing validation error messages per field.
     */
    public PulseResponse(Map<String, String> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

}
