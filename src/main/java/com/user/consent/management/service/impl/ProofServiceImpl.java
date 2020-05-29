package com.user.consent.management.service.impl;

import com.user.consent.management.service.ProofService;
import com.user.consent.management.domain.Proof;
import com.user.consent.management.repository.ProofRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Proof}.
 */
@Service
public class ProofServiceImpl implements ProofService {

    private final Logger log = LoggerFactory.getLogger(ProofServiceImpl.class);

    private final ProofRepository proofRepository;

    public ProofServiceImpl(ProofRepository proofRepository) {
        this.proofRepository = proofRepository;
    }

    /**
     * Save a proof.
     *
     * @param proof the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Proof save(Proof proof) {
        log.debug("Request to save Proof : {}", proof);
        return proofRepository.save(proof);
    }

    /**
     * Get all the proofs.
     *
     * @return the list of entities.
     */
    @Override
    public List<Proof> findAll() {
        log.debug("Request to get all Proofs");
        return proofRepository.findAll();
    }


    /**
     * Get one proof by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<Proof> findOne(String id) {
        log.debug("Request to get Proof : {}", id);
        return proofRepository.findById(id);
    }

    /**
     * Delete the proof by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Proof : {}", id);

        proofRepository.deleteById(id);
    }
}
