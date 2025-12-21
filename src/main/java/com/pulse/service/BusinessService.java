package com.pulse.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.pulse.dto.request.BusinessRequestDto;
import com.pulse.dto.response.BusinessResponseDto;
import com.pulse.exception.BusinessException;
import com.pulse.mapper.BusinessMapper;
import com.pulse.persistence.entity.BusinessEntity;
import com.pulse.persistence.repository.BusinessRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BusinessService {

    private final BusinessRepository businessRepository;

    public List<BusinessResponseDto> getBusinesses() {

        LOGGER.debug("Fetching all businesses");
        return businessRepository.findAll().stream().map(BusinessMapper::toDto).toList();

    }

    public List<BusinessResponseDto> getActiveBusinesses() {

        LOGGER.debug("Fetching active businesses");
        return businessRepository.findByStatus(true).stream().map(BusinessMapper::toDto).toList();

    }

    public BusinessResponseDto getBusinessById(Long id) {

        LOGGER.debug("Fetching business: {}", id);
        return businessRepository.findById(id).map(BusinessMapper::toDto).orElseThrow(() -> {
            throw new BusinessException(HttpStatus.NOT_FOUND, "business.id.not.found", id);
        });

    }

    public List<BusinessResponseDto> getBusinessesByName(String name) {

        LOGGER.debug("Getting business: {}", name);
        return businessRepository.findByNameIgnoreCase(name).stream().map(BusinessMapper::toDto).toList();

    }

    @Transactional
    public BusinessResponseDto createBusiness(BusinessRequestDto businessRequestDto) {

        LOGGER.debug("Creating business: {}", businessRequestDto.name());
        if (businessRepository.existsByEmail(businessRequestDto.email())) {
            throw new BusinessException(HttpStatus.CONFLICT, "email.exists", businessRequestDto.email());
        }

        try {

            BusinessEntity businessEntity = BusinessMapper.toEntity(businessRequestDto);
            businessEntity = businessRepository.save(businessEntity);
            LOGGER.info("business.created.success", businessEntity.getId());
            return BusinessMapper.toDto(businessEntity);

        } catch (Exception exception) {

            LOGGER.error("business.creation.failed", exception);
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "business.creation.failed",
                    businessRequestDto.name());

        }

    }

    @Transactional
    public BusinessResponseDto updateBusiness(BusinessRequestDto businessRequestDto) {

        Long id = businessRequestDto.id();
        LOGGER.debug("Updating business: {}", id);

        BusinessEntity businessEntity = businessRepository.findById(id).orElseThrow(() -> {
            throw new BusinessException(HttpStatus.NOT_FOUND, "business.id.not.found", id);
        });

        if (StringUtils.hasText(businessRequestDto.name())
                && !businessRequestDto.name().equals(businessEntity.getName())
                && businessRepository.existsByNameIgnoreCase(businessRequestDto.name())) {
            throw new BusinessException(HttpStatus.CONFLICT, "name.exists", businessRequestDto.name());

        }

        if (StringUtils.hasText(businessRequestDto.email())
                && !businessRequestDto.email().equals(businessEntity.getEmail())
                && businessRepository.existsByEmail(businessRequestDto.email())) {
            throw new BusinessException(HttpStatus.CONFLICT, "email.exists", businessRequestDto.email());

        }

        try {

            if (businessRequestDto.name() != null) {
                businessEntity.setName(businessRequestDto.name());
            }
            if (businessRequestDto.description() != null) {
                businessEntity.setDescription(businessRequestDto.description());
            }
            if (businessRequestDto.email() != null) {
                businessEntity.setEmail(businessRequestDto.email());
            }
            if (businessRequestDto.phone() != null) {
                businessEntity.setPhone(businessRequestDto.phone());
            }
            if (businessRequestDto.address() != null) {
                businessEntity.setAddress(businessRequestDto.address());
            }
            if (businessRequestDto.status() != null) {
                businessEntity.setStatus(businessRequestDto.status());
            }

            businessEntity = businessRepository.save(businessEntity);
            LOGGER.info("business.updated.success", id);
            return BusinessMapper.toDto(businessEntity);

        } catch (Exception exception) {

            LOGGER.error("business.update.failed", id, exception);
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "business.update.failed", id);

        }

    }

    @Transactional
    public void deleteBusiness(Long id) {

        LOGGER.debug("Deleting business: {}", id);

        if (!businessRepository.existsById(id)) {

            LOGGER.warn("Failed to find business: {}", id);
            throw new BusinessException(HttpStatus.NOT_FOUND, "business.id.not.found", id);

        }

        try {

            businessRepository.deleteById(id);
            LOGGER.info("Deleted business: {}", id);

        } catch (Exception e) {

            LOGGER.error("Failed to delete business: {}", id, e);
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "business.deletion.failed", id);

        }

    }

}
