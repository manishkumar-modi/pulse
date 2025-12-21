package com.pulse.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * JPA entity that represents a Business record persisted in the BUSINESS table.
 *
 * <p>This entity contains basic contact and descriptive information for a business and
 * extends {@link BaseEntity} which should provide common audit fields (createdAt,
 * updatedAt, etc.). The class is annotated with table-level indexes for commonly
 * queried columns (id, name, email, phone).</p>
 *
 * <p>Indexing notes:
 * - ID: primary key; database typically creates an index automatically, but an explicit
 * index is added here per request.
 * - NAME / EMAIL / PHONE: indexed to improve lookup performance for searches and
 * uniqueness checks (if applied at the application or DB level).</p>
 */
@Getter
@Setter
@Entity
@Table(
        name = "BUSINESS",
        indexes = {
                @Index(name = "IDX_BUSINESS_NAME", columnList = "NAME"),
                @Index(name = "IDX_BUSINESS_EMAIL", columnList = "EMAIL"),
                @Index(name = "IDX_BUSINESS_PHONE", columnList = "PHONE")
        }
)
public class BusinessEntity extends BaseEntity {

    /**
     * Primary identifier for the business.
     *
     * <p>Populated by the database using identity generation strategy. This column is
     * not updatable after insert.</p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    /**
     * Business display name.
     *
     * <p>Required; maximum length is 50 characters.</p>
     */
    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    /**
     * Extended description for the business.
     *
     * <p>Mapped to a TEXT column to allow longer content than standard VARCHAR.</p>
     */
    @Column(name = "DESCRIPTION", columnDefinition = "TEXT")
    private String description;

    /**
     * Contact email address for the business.
     *
     * <p>Maximum length is 50 characters. Consider validating format before persisting.</p>
     */
    @Column(name = "EMAIL", length = 50)
    private String email;

    /**
     * Contact phone number for the business.
     *
     * <p>Stored as string to preserve formatting; maximum length is 50 characters.</p>
     */
    @Column(name = "PHONE", length = 50)
    private String phone;

    /**
     * Postal or street address for the business.
     *
     * <p>Maximum length is 200 characters.</p>
     */
    @Column(name = "ADDRESS", length = 200)
    private String address;

    /**
     * Active status flag for the business.
     *
     * <p>Defaults to {@code true}. The meaning of the flag is application-specific
     * (for example, {@code true} = active, {@code false} = inactive).</p>
     */
    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean status = Boolean.TRUE;

}
