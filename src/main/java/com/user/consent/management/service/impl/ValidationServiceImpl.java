package com.user.consent.management.service.impl;

import com.user.consent.management.service.ValidationService;
import com.user.consent.management.domain.Validation;
import com.user.consent.management.repository.ValidationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Validation}.
 */
@Service
public class ValidationServiceImpl implements ValidationService {

    private final Logger log = LoggerFactory.getLogger(ValidationServiceImpl.class);

    private final ValidationRepository validationRepository;

    public ValidationServiceImpl(ValidationRepository validationRepository) {
        this.validationRepository = validationRepository;
    }

    /**
     * Save a validation.
     *
     * @param validation the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Validation save(Validation validation) {
        log.debug("Request to save Validation : {}", validation);
        return validationRepository.save(validation);
    }

    /**
     * Get all the validations.
     *
     * @return the list of entities.
     */
    @Override
    public List<Validation> findAll() {
        log.debug("Request to get all Validations");
        return validationRepository.findAll();
    }


    /**
     * Get one validation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<Validation> findOne(String id) {
        log.debug("Request to get Validation : {}", id);
        return validationRepository.findById(id);
    }

    /**
     * Delete the validation by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Validation : {}", id);

        validationRepository.deleteById(id);
    }
}
