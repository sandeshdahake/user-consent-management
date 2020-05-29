package com.user.consent.management.service;

import com.user.consent.management.domain.Purpose;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Purpose}.
 */
public interface PurposeService {

    /**
     * Save a purpose.
     *
     * @param purpose the entity to save.
     * @return the persisted entity.
     */
    Purpose save(Purpose purpose);

    /**
     * Get all the purposes.
     *
     * @return the list of entities.
     */
    List<Purpose> findAll();


    /**
     * Get the "id" purpose.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Purpose> findOne(String id);

    /**
     * Delete the "id" purpose.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
