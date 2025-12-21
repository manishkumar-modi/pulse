package com.pulse.dto.request;

import com.pulse.validator.OnCreate;
import com.pulse.validator.OnUpdate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * Request DTO for Business entity.
 *
 * <p>
 * This class represents the payload received from API clients for creating or
 * updating a Business.
 * It uses Jakarta Bean Validation with validation groups to enforce different
 * rules for create and
 * update operations.
 * </p>
 *
 * <h3>Validation Groups</h3>
 * <ul>
 * <li><b>OnCreate</b> - Applied when creating a new Business</li>
 * <li><b>OnUpdate</b> - Applied when updating an existing Business</li>
 * </ul>
 *
 * <h3>Validation Strategy</h3>
 * <ul>
 * <li>Fields required during creation may be optional during update</li>
 * <li>Fields provided during update are still validated for correctness</li>
 * <li>Null values are ignored by most constraints unless explicitly
 * restricted</li>
 * </ul>
 *
 * <p>
 * This ensures flexibility for partial updates while maintaining strict
 * validation during creation.
 * </p>
 */
public record BusinessRequestDto(

        /**
         * Unique identifier of the business.
         *
         * <p>
         * <b>Validation Rules:</b>
         * </p>
         * <ul>
         * <li>Must be <code>null</code> during creation</li>
         * <li>Must be non-null and positive during update</li>
         * </ul>
         */
        @Null(groups = OnCreate.class, message = "{id.null}") @NotNull(groups = OnUpdate.class, message = "{id.required}") @Positive(groups = OnUpdate.class, message = "{id.positive}") Long id,

        /**
         * Name of the business.
         *
         * <p>
         * <b>Validation Rules:</b>
         * </p>
         * <ul>
         * <li>Required during creation</li>
         * <li>Optional during update</li>
         * <li>Maximum length: 50 characters</li>
         * </ul>
         */
        @NotBlank(groups = OnCreate.class, message = "{name.required}") @Size(max = 50, groups = {
                OnCreate.class, OnUpdate.class }, message = "{name.size}") String name,

        /**
         * Description of the business.
         *
         * <p>
         * Optional field. If provided:
         * </p>
         * <ul>
         * <li>Maximum length: 5000 characters</li>
         * </ul>
         */
        @Size(max = 5000, groups = { OnCreate.class,
                OnUpdate.class }, message = "{description.size}") String description,

        /**
         * Email of the business.
         *
         * <p>
         * <b>Validation Rules:</b>
         * </p>
         * <ul>
         * <li>Required during creation</li>
         * <li>Optional during update</li>
         * <li>Must follow valid email format</li>
         * <li>Maximum length: 50 characters</li>
         * </ul>
         */
        @NotNull(groups = OnCreate.class, message = "{email.required}") @Email(groups = { OnCreate.class,
                OnUpdate.class }, message = "{email.invalid}") @Size(max = 50, groups = { OnCreate.class,
                        OnUpdate.class }, message = "{email.size}") String email,

        /**
         * Contact phone number of the business.
         *
         * <p>
         * Optional field. If provided:
         * </p>
         * <ul>
         * <li>Must contain 10 to 15 digits</li>
         * <li>May optionally start with '+'</li>
         * <li>Maximum length: 50 characters</li>
         * </ul>
         */
        @Pattern(regexp = "^[+]?[0-9]{10,15}$", groups = { OnCreate.class,
                OnUpdate.class }, message = "{phone.invalid}") @Size(max = 50, groups = { OnCreate.class,
                        OnUpdate.class }, message = "{phone.size}") String phone,

        /**
         * Address of the business.
         *
         * <p>
         * <b>Validation Rules:</b>
         * </p>
         * <ul>
         * <li>Required during creation</li>
         * <li>Optional during update</li>
         * <li>Maximum length: 200 characters</li>
         * </ul>
         */
        @NotNull(groups = OnCreate.class, message = "{address.required}") @Size(max = 200, groups = { OnCreate.class,
                OnUpdate.class }, message = "{address.size}") String address,

        /**
         * Status of the business (e.g., active/inactive).
         *
         * <p>
         * <b>Validation Rules:</b>
         * </p>
         * <ul>
         * <li>Required during creation</li>
         * <li>Optional during update</li>
         * </ul>
         */
        @NotNull(groups = OnCreate.class, message = "{status.required}") Boolean status) {

}
