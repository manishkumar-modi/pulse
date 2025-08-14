package com.pulse.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test class for {@link com.pulse.controller.HealthController}.
 * <p>
 * This class uses Spring Boot's testing support with MockMvc to verify that the
 * health check endpoint of the Pulse application is functioning correctly.
 * It ensures that the endpoint returns an HTTP 200 OK status and the expected
 * JSON response containing the message "Pulse backend is running".
 * </p>
 */
@SpringBootTest
@AutoConfigureMockMvc
public class HealthControllerTest {

    /** MockMvc instance for performing HTTP requests in tests */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Tests the /api/health endpoint.
     * <p>
     * Sends a GET request to the health endpoint and verifies:
     * <ul>
     *   <li>HTTP status is 200 OK</li>
     *   <li>JSON response contains the expected message</li>
     * </ul>
     * </p>
     *
     * @throws Exception if the request fails
     */
    @Test
    public void testHealthCheck() throws Exception {

        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Pulse backend is running"));

    }

}
