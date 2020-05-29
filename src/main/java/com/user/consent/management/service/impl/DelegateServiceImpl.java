package com.user.consent.management.service.impl;

import com.user.consent.management.service.DelegateService;
import com.user.consent.management.domain.Delegate;
import com.user.consent.management.repository.DelegateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Delegate}.
 */
@Service
public class DelegateServiceImpl implements DelegateService {

    private final Logger log = LoggerFactory.getLogger(DelegateServiceImpl.class);

    private final DelegateRepository delegateRepository;

    public DelegateServiceImpl(DelegateRepository delegateRepository) {
        this.delegateRepository = delegateRepository;
    }

    /**
     * Save a delegate.
     *
     * @param delegate the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Delegate save(Delegate delegate) {
        log.debug("Request to save Delegate : {}", delegate);
        return delegateRepository.save(delegate);
    }

    /**
     * Get all the delegates.
     *
     * @return the list of entities.
     */
    @Override
    public List<Delegate> findAll() {
        log.debug("Request to get all Delegates");
        return delegateRepository.findAll();
    }


    /**
     * Get one delegate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<Delegate> findOne(String id) {
        log.debug("Request to get Delegate : {}", id);
        return delegateRepository.findById(id);
    }

    /**
     * Delete the delegate by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Delegate : {}", id);

        delegateRepository.deleteById(id);
    }
}
