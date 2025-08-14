package com.pulse.controller;

import com.pulse.model.response.HealthResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HealthController is responsible for exposing a health check endpoint for the Pulse backend service.
 * <p>
 * This endpoint can be used by monitoring tools, load balancers, or other services to verify
 * that the backend application is running and responsive.
 * </p>
 *
 * <p>
 * URL: <code>${apiPrefix}/health</code><br>
 * Method: GET<br>
 * Response: {@link HealthResponse} containing a simple status message.
 * </p>
 * <p>
 * Example Response:
 * <pre>
 * {
 *   "message": "Pulse backend is running"
 * }
 * </pre>
 *
 * @author Manish Modi
 * @since 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${apiPrefix}/health")
public class HealthController {

    /**
     * Handles the GET request to check the health status of the Pulse backend.
     *
     * @return {@link ResponseEntity} containing {@link HealthResponse} with HTTP status 200 (OK).
     */
    @GetMapping
    public ResponseEntity<HealthResponse> health() {

        LOGGER.debug("Request has been received");
        ResponseEntity<HealthResponse> responseEntity = ResponseEntity
                .status(HttpStatus.OK)
                .body(new HealthResponse("Pulse backend is running"));
        LOGGER.debug("Response has been sent");

        return responseEntity;

    }

}
