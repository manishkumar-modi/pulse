package com.pulse.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pulse.dto.response.PulseResponse;
import com.pulse.service.MessageService;

/**
 * REST controller that exposes a health check endpoint for the Pulse backend service.
 * <p>
 * The health endpoint is intended to be used by load balancers, orchestration systems, or
 * monitoring tools to confirm the application is running and responsive.
 * </p>
 *
 * <p>
 * URL: <code>${pulse.api.prefix}/health</code><br>
 * Method: GET<br>
 * Response: {@link PulseResponse} with a simple health message and an auto-generated timestamp.
 * </p>
 *
 * <p>
 * Example Response:
 * 
 * <pre>
 * {
 *   "success": true,
 *   "status": 200,
 *   "message": "Pulse backend is running"
 * }
 * </pre>
 * </p>
 *
 * @author Manish Modi
 * @since 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${pulse.api.prefix}/health")
public class HealthController {

    private final MessageService messageService;

    /**
     * Returns a health check response.
     *
     * <p>
     * The response is wrapped in {@link PulseResponse} and includes a timestamp generated at the
     * moment the response is created.
     * </p>
     *
     * @return {@link ResponseEntity} containing {@link PulseResponse} with HTTP status 200 (OK)
     */
    @GetMapping
    public ResponseEntity<PulseResponse<Void>> health() {

        LOGGER.debug("Received health check request");

        PulseResponse<Void> response = new PulseResponse<>(true, HttpStatus.OK.value(),
                messageService.getMessage("health.running"), "requestId");

        LOGGER.debug("Returning health check response");
        return ResponseEntity.ok(response);

    }

}
