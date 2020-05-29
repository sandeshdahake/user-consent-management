package com.user.consent.management.service;

import com.user.consent.management.domain.Validation;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Validation}.
 */
public interface ValidationService {

    /**
     * Save a validation.
     *
     * @param validation the entity to save.
     * @return the persisted entity.
     */
    Validation save(Validation validation);

    /**
     * Get all the validations.
     *
     * @return the list of entities.
     */
    List<Validation> findAll();


    /**
     * Get the "id" validation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Validation> findOne(String id);

    /**
     * Delete the "id" validation.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
