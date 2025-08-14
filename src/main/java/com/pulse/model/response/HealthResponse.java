package com.pulse.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Represents the response object for health check endpoints.
 * <p>
 * This class is used to encapsulate a simple health status message.
 * It ignores unknown JSON properties during deserialization and
 * excludes null fields when serialized to JSON.
 * </p>
 * Example usage:
 * <pre>
 *     HealthResponse response = new HealthResponse("Service is up");
 * </pre>
 * <p>
 * Lombok annotations are used to automatically generate getters, setters,
 * toString, and no-argument constructor.
 */
@Data
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HealthResponse {

    /**
     * Message describing the health status
     */
    private String message;

    /**
     * Constructs a HealthResponse with a specific message.
     *
     * @param message the health status message
     */
    public HealthResponse(String message) {
        this.message = message;
    }

}
