package com.user.consent.management.service;

import com.user.consent.management.domain.Delegate;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Delegate}.
 */
public interface DelegateService {

    /**
     * Save a delegate.
     *
     * @param delegate the entity to save.
     * @return the persisted entity.
     */
    Delegate save(Delegate delegate);

    /**
     * Get all the delegates.
     *
     * @return the list of entities.
     */
    List<Delegate> findAll();


    /**
     * Get the "id" delegate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Delegate> findOne(String id);

    /**
     * Delete the "id" delegate.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
