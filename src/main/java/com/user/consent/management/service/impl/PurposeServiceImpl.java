package com.user.consent.management.service.impl;

import com.user.consent.management.service.PurposeService;
import com.user.consent.management.domain.Purpose;
import com.user.consent.management.repository.PurposeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Purpose}.
 */
@Service
public class PurposeServiceImpl implements PurposeService {

    private final Logger log = LoggerFactory.getLogger(PurposeServiceImpl.class);

    private final PurposeRepository purposeRepository;

    public PurposeServiceImpl(PurposeRepository purposeRepository) {
        this.purposeRepository = purposeRepository;
    }

    /**
     * Save a purpose.
     *
     * @param purpose the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Purpose save(Purpose purpose) {
        log.debug("Request to save Purpose : {}", purpose);
        return purposeRepository.save(purpose);
    }

    /**
     * Get all the purposes.
     *
     * @return the list of entities.
     */
    @Override
    public List<Purpose> findAll() {
        log.debug("Request to get all Purposes");
        return purposeRepository.findAll();
    }


    /**
     * Get one purpose by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<Purpose> findOne(String id) {
        log.debug("Request to get Purpose : {}", id);
        return purposeRepository.findById(id);
    }

    /**
     * Delete the purpose by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Purpose : {}", id);

        purposeRepository.deleteById(id);
    }
}
