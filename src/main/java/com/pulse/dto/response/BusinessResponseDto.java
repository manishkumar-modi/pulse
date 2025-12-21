package com.pulse.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

/**
 * Immutable response DTO for Business entity.
 * <p>
 * Contains all business data returned to API clients after mapping from the entity.
 * </p>
 */
public record BusinessResponseDto(

        /** Unique identifier for the business. */
        @JsonProperty("id") Long id,

        /** Business name. */
        @JsonProperty("name") String name,

        /** Business description. */
        @JsonProperty("description") String description,

        /** Business email address. */
        @JsonProperty("email") String email,

        /** Business phone number. */
        @JsonProperty("phone") String phone,

        /** Business address. */
        @JsonProperty("address") String address,

        /** Business status (active/inactive). */
        @JsonProperty("status") Boolean status,

        /** Identifier of the principal that created the business record. */
        @JsonProperty("createdBy") String createdBy,

        /** Timestamp when the business record was created. */
        @JsonProperty("createdAt") LocalDateTime createdAt,

        /** Identifier of the principal that last updated the business record. */
        @JsonProperty("updatedBy") String updatedBy,

        /** Timestamp when the business record was last updated. */
        @JsonProperty("updatedAt") LocalDateTime updatedAt) {

}
