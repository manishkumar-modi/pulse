package com.pulse.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Base mapped superclass providing common audit fields and lifecycle callbacks.
 *
 * <p>Entities that extend this class inherit audit attributes typically required for
 * persistence records such as {@code createdBy}, {@code createdAt}, {@code updatedBy},
 * and {@code updatedAt}. The lifecycle callback methods {@link #onCreate()} and
 * {@link #onUpdate()} populate or update these fields automatically on persist and
 * update events respectively.</p>
 *
 * <p>NOTE: In this example the {@code createdBy} and {@code updatedBy} values are
 * populated with a placeholder ("system"). In a real application these values should
 * be derived from the current security / authentication context (for example, the
 * currently authenticated user).</p>
 */
@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    /**
     * Identifier of the principal that created the entity.
     *
     * <p>Populated on insert and not updatable afterward by default.</p>
     */
    @Column(name = "CREATED_BY", nullable = false, updatable = false, length = 50)
    private String createdBy;

    /**
     * Timestamp when the entity was created.
     *
     * <p>Populated on insert and not updatable afterward by default.</p>
     */
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Identifier of the principal that last updated the entity.
     *
     * <p>Updated on each update lifecycle event.</p>
     */
    @Column(name = "UPDATED_BY", length = 50)
    private String updatedBy;

    /**
     * Timestamp when the entity was last updated.
     *
     * <p>Updated on each update lifecycle event.</p>
     */
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    /**
     * JPA lifecycle callback invoked before the entity is persisted for the first time.
     *
     * <p>This method sets {@link #createdAt}, {@link #updatedAt}, {@link #createdBy},
     * and {@link #updatedBy}. In production, the creator/updater values should be
     * supplied from the authenticated user context rather than hard-coded.</p>
     */
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        // TODO In a real app, these values should come from logged-in user context
        this.createdBy = "system";
        this.updatedBy = "system";
    }

    /**
     * JPA lifecycle callback invoked before the entity is updated.
     *
     * <p>This method updates the {@link #updatedAt} and {@link #updatedBy} fields.
     * In production, the updater value should be supplied from the authenticated user
     * context rather than hard-coded.</p>
     */
    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
        // TODO In a real app, this value should come from logged-in user context
        this.updatedBy = "system";
    }

}
