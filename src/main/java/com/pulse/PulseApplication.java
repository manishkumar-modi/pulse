package com.pulse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Entry point for the Pulse application.
 * <p>
 * This class bootstraps the Spring Boot application and enables
 * </p>
 */
@EnableJpaAuditing
@SpringBootApplication
public class PulseApplication {

    /**
     * Main method used to launch the Spring Boot application.
     *
     * @param args application startup arguments (typically passed via command line).
     */
    public static void main(String[] args) {
        SpringApplication.run(PulseApplication.class, args);
    }

}
