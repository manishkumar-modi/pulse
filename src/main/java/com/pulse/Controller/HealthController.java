package com.pulse.Controller;

import com.pulse.model.response.PulseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${apiPrefix}/health")
public class HealthController {

    @GetMapping
    public ResponseEntity<PulseResponse> health() {

        LOGGER.debug("Request has been received");
        ResponseEntity<PulseResponse> responseEntity = ResponseEntity
                .status(HttpStatus.OK)
                .body(new PulseResponse("Pulse backend is running"));
        LOGGER.debug("Request has been received");


        return responseEntity;

    }

}
