package com.pulse.mapper;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.util.CollectionUtils;

import com.pulse.dto.request.BusinessRequestDto;
import com.pulse.dto.response.BusinessResponseDto;
import com.pulse.persistence.entity.BusinessEntity;

/**
 * Utility class for converting between {@link BusinessEntity},
 * {@link BusinessRequestDto}, and
 * {@link BusinessResponseDto} objects.
 * <p>
 * This class is stateless and should not be instantiated.
 * </p>
 */
public final class BusinessMapper {

    private BusinessMapper() {
        // Prevent instantiation
    }

    /**
     * Converts a {@link BusinessEntity} to a {@link BusinessResponseDto}.
     *
     * @param businessEntity the source BusinessEntity
     * @return the mapped BusinessResponseDto, or null when the source entity is
     *         null
     */
    public static BusinessResponseDto toDto(BusinessEntity businessEntity) {

        if (businessEntity == null) {
            return null;
        }

        return new BusinessResponseDto(
                businessEntity.getId(),
                businessEntity.getName(),
                businessEntity.getDescription(),
                businessEntity.getEmail(),
                businessEntity.getPhone(),
                businessEntity.getAddress(),
                businessEntity.getStatus(),
                businessEntity.getCreatedBy(),
                businessEntity.getCreatedAt(),
                businessEntity.getUpdatedBy(),
                businessEntity.getUpdatedAt());

    }

    /**
     * Converts a {@link BusinessRequestDto} to a {@link BusinessEntity}.
     *
     * @param businessRequestDto the source BusinessRequestDto
     * @return the mapped BusinessEntity, or null when the source dto is null
     */
    public static BusinessEntity toEntity(BusinessRequestDto businessRequestDto) {

        if (businessRequestDto == null) {
            return null;
        }

        BusinessEntity entity = new BusinessEntity();
        if (businessRequestDto.id() != null) {
            entity.setId(businessRequestDto.id());
        }
        entity.setName(businessRequestDto.name());
        entity.setDescription(businessRequestDto.description());
        entity.setEmail(businessRequestDto.email());
        entity.setPhone(businessRequestDto.phone());
        entity.setAddress(businessRequestDto.address());
        entity.setStatus(businessRequestDto.status());
        return entity;

    }

    /**
     * Converts a list of {@link BusinessRequestDto} objects to a list of
     * {@link BusinessEntity}
     * objects.
     *
     * @param businessRequestDtos list of BusinessRequestDto instances
     * @return list of BusinessEntity instances, or an empty list when input is null
     *         or empty
     */
    public static List<BusinessEntity> toEntityList(List<BusinessRequestDto> businessRequestDtos) {

        if (CollectionUtils.isEmpty(businessRequestDtos)) {
            return Collections.emptyList();
        }

        return businessRequestDtos.stream().filter(Objects::nonNull).map(BusinessMapper::toEntity).toList();

    }

    /**
     * Converts a list of {@link BusinessEntity} objects to a list of
     * {@link BusinessResponseDto}
     * objects.
     *
     * @param entities list of BusinessEntity instances
     * @return list of BusinessResponseDto instances, or an empty list when input is
     *         null or empty
     */
    public static List<BusinessResponseDto> toDtoList(List<BusinessEntity> entities) {

        if (CollectionUtils.isEmpty(entities)) {
            return Collections.emptyList();
        }

        return entities.stream().filter(Objects::nonNull).map(BusinessMapper::toDto).toList();

    }

}
