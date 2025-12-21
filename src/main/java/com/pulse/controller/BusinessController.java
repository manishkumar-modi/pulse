package com.pulse.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pulse.dto.request.BusinessRequestDto;
import com.pulse.dto.response.BusinessResponseDto;
import com.pulse.dto.response.PulseResponse;
import com.pulse.service.BusinessService;
import com.pulse.validator.OnCreate;
import com.pulse.validator.OnUpdate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("${pulse.api.prefix}/business")
public class BusinessController {

    private final BusinessService businessService;

    @GetMapping
    public ResponseEntity<PulseResponse<List<BusinessResponseDto>>> all() {

        LOGGER.info("Fetching all businesses");
        return ResponseEntity.status(HttpStatus.OK)
                .body(PulseResponse.success(businessService.getAllBusinesses()));

    }

    @GetMapping("/active")
    public ResponseEntity<PulseResponse<List<BusinessResponseDto>>> getActive() {

        LOGGER.info("Fetching all active businesses");
        return ResponseEntity.status(HttpStatus.OK)
                .body(PulseResponse.success(businessService.getActiveBusinesses()));

    }

    @GetMapping("/{id}")
    public ResponseEntity<PulseResponse<BusinessResponseDto>> get(
            @PathVariable("id") @NotNull(message = "{id.required}") @Positive(message = "{id.positive}") Long id) {

        LOGGER.info("Fetching business with ID: {}", id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(PulseResponse.success(businessService.getBusinessById(id)));

    }

    @GetMapping("/search/{name}")
    public ResponseEntity<PulseResponse<List<BusinessResponseDto>>> searchByName(
            @PathVariable("name") @NotBlank(message = "{name.required}") @Size(max = 50, message = "{name.size}") String name) {

        LOGGER.info("Searching businesses by name: {}", name);
        return ResponseEntity.status(HttpStatus.OK)
                .body(PulseResponse.success(businessService.getBusinessesByName(name)));

    }

    @PostMapping
    public ResponseEntity<PulseResponse<BusinessResponseDto>> create(
            @RequestBody @Validated(OnCreate.class) BusinessRequestDto businessRequestDto) {

        LOGGER.info("Creating new business: {}", businessRequestDto.name());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PulseResponse.created(businessService.createBusiness(businessRequestDto)));

    }

    @PutMapping
    public ResponseEntity<PulseResponse<BusinessResponseDto>> update(
            @RequestBody @Validated(OnUpdate.class) BusinessRequestDto businessRequestDto) {

        LOGGER.info("Updating business with ID: {}", businessRequestDto.id());
        return ResponseEntity.status(HttpStatus.OK)
                .body(PulseResponse.success(businessService.updateBusiness(businessRequestDto)));

    }

    @PutMapping("/{id}/inactivate")
    public ResponseEntity<PulseResponse<BusinessResponseDto>> inactivate(
            @PathVariable("id") @NotNull(message = "{id.required}") @Positive(message = "{id.positive}") Long id) {

        LOGGER.info("Inactivating business with ID: {}", id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(PulseResponse.success(businessService.inactivateBusiness(id)));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PulseResponse<Void>> delete(
            @PathVariable("id") @NotNull(message = "{id.required}") @Positive(message = "{id.positive}") Long id) {

        LOGGER.info("Deleting business with ID: {}", id);
        businessService.deleteBusiness(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(PulseResponse.success());

    }

}
