package com.user.consent.management.service;

import com.user.consent.management.domain.Consent;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Consent}.
 */
public interface ConsentService {

    /**
     * Save a consent.
     *
     * @param consent the entity to save.
     * @return the persisted entity.
     */
    Consent save(Consent consent);

    /**
     * Get all the consents.
     *
     * @return the list of entities.
     */
    List<Consent> findAll();


    /**
     * Get the "id" consent.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Consent> findOne(String id);

    /**
     * Delete the "id" consent.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
