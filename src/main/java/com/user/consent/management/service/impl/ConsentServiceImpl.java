package com.user.consent.management.service.impl;

import com.user.consent.management.service.ConsentService;
import com.user.consent.management.domain.Consent;
import com.user.consent.management.repository.ConsentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Consent}.
 */
@Service
public class ConsentServiceImpl implements ConsentService {

    private final Logger log = LoggerFactory.getLogger(ConsentServiceImpl.class);

    private final ConsentRepository consentRepository;

    public ConsentServiceImpl(ConsentRepository consentRepository) {
        this.consentRepository = consentRepository;
    }

    /**
     * Save a consent.
     *
     * @param consent the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Consent save(Consent consent) {
        log.debug("Request to save Consent : {}", consent);
        return consentRepository.save(consent);
    }

    /**
     * Get all the consents.
     *
     * @return the list of entities.
     */
    @Override
    public List<Consent> findAll() {
        log.debug("Request to get all Consents");
        return consentRepository.findAll();
    }


    /**
     * Get one consent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<Consent> findOne(String id) {
        log.debug("Request to get Consent : {}", id);
        return consentRepository.findById(id);
    }

    /**
     * Delete the consent by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Consent : {}", id);

        consentRepository.deleteById(id);
    }
}
