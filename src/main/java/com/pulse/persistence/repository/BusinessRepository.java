package com.pulse.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pulse.persistence.entity.BusinessEntity;

/**
 * Repository interface for performing CRUD and query operations on
 * BusinessEntity.
 *
 * <p>
 * This interface extends {@link JpaRepository} to provide standard JPA-based
 * persistence operations (save, findById, findAll, delete, etc.). In addition,
 * domain-specific query methods are declared for common lookup use-cases.
 * </p>
 */
@Repository
public interface BusinessRepository extends JpaRepository<BusinessEntity, Long> {

    /**
     * Determines whether a business with the given email exists.
     *
     * @param email the email address to check for (must not be null)
     * @return {@code true} if a BusinessEntity with the given email exists;
     *         {@code false} otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Determines whether a business with the given name exists (case-insensitive).
     *
     * @param name the business name to check for (must not be null)
     * @return {@code true} if a BusinessEntity with the given name exists;
     *         {@code false} otherwise
     */
    boolean existsByNameIgnoreCase(String name);

    /**
     * Finds all business entities that match the provided status flag.
     *
     * <p>
     * Convention used here is application-specific; callers should agree on the
     * meaning
     * of the {@code status} flag (for example, {@code true} = active, {@code false}
     * = inactive).
     * </p>
     *
     * @param status the status flag to filter by (may be {@code null} if allowing
     *               tri-state)
     * @return a list of {@link BusinessEntity} instances matching the given status;
     *         never {@code null}
     */
    List<BusinessEntity> findByStatus(Boolean status);

    /**
     * Finds businesses with a name equal to the provided value, ignoring case.
     *
     * <p>
     * Returns all matching entities; if multiple businesses use the same name,
     * the returned list will contain all of them.
     * </p>
     *
     * @param name the business name to search for (case-insensitive)
     * @return a list of {@link BusinessEntity} instances whose name matches the
     *         given value; never {@code null}
     */
    List<BusinessEntity> findByNameIgnoreCase(String name);

    /**
     * Find a business by email address.
     *
     * @param email the email address to search for
     * @return an Optional containing the business if found, empty otherwise
     */
    Optional<BusinessEntity> findByEmailIgnoreCase(String email);

    /**
     * Find businesses whose name contains the given substring (case-insensitive).
     *
     * @param nameFragment the substring to search for in business names
     * @return a list of businesses whose name contains the fragment
     */
    List<BusinessEntity> findByNameContainingIgnoreCase(String nameFragment);

    /**
     * Count the number of active businesses.
     *
     * @return the count of active businesses
     */
    @Query("SELECT COUNT(b) FROM BusinessEntity b WHERE b.status = true")
    long countActiveBusinesses();

    /**
     * Find businesses by status with custom sorting and filtering.
     *
     * @param status the status to filter by
     * @return a list of businesses with the given status
     */
    @Query("SELECT b FROM BusinessEntity b WHERE b.status = :status ORDER BY b.name ASC")
    List<BusinessEntity> findActiveBusinessesSorted(@Param("status") Boolean status);
}
