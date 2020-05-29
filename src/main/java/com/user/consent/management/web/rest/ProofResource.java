package com.user.consent.management.web.rest;

import com.user.consent.management.domain.Proof;
import com.user.consent.management.service.ProofService;
import com.user.consent.management.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.user.consent.management.domain.Proof}.
 */
@RestController
@RequestMapping("/api")
public class ProofResource {

    private final Logger log = LoggerFactory.getLogger(ProofResource.class);

    private static final String ENTITY_NAME = "proof";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProofService proofService;

    public ProofResource(ProofService proofService) {
        this.proofService = proofService;
    }

    /**
     * {@code POST  /proofs} : Create a new proof.
     *
     * @param proof the proof to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new proof, or with status {@code 400 (Bad Request)} if the proof has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/proofs")
    public ResponseEntity<Proof> createProof(@Valid @RequestBody Proof proof) throws URISyntaxException {
        log.debug("REST request to save Proof : {}", proof);
        if (proof.getId() != null) {
            throw new BadRequestAlertException("A new proof cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Proof result = proofService.save(proof);
        return ResponseEntity.created(new URI("/api/proofs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /proofs} : Updates an existing proof.
     *
     * @param proof the proof to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated proof,
     * or with status {@code 400 (Bad Request)} if the proof is not valid,
     * or with status {@code 500 (Internal Server Error)} if the proof couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/proofs")
    public ResponseEntity<Proof> updateProof(@Valid @RequestBody Proof proof) throws URISyntaxException {
        log.debug("REST request to update Proof : {}", proof);
        if (proof.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Proof result = proofService.save(proof);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, proof.getId()))
            .body(result);
    }

    /**
     * {@code GET  /proofs} : get all the proofs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of proofs in body.
     */
    @GetMapping("/proofs")
    public List<Proof> getAllProofs() {
        log.debug("REST request to get all Proofs");
        return proofService.findAll();
    }

    /**
     * {@code GET  /proofs/:id} : get the "id" proof.
     *
     * @param id the id of the proof to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the proof, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/proofs/{id}")
    public ResponseEntity<Proof> getProof(@PathVariable String id) {
        log.debug("REST request to get Proof : {}", id);
        Optional<Proof> proof = proofService.findOne(id);
        return ResponseUtil.wrapOrNotFound(proof);
    }

    /**
     * {@code DELETE  /proofs/:id} : delete the "id" proof.
     *
     * @param id the id of the proof to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/proofs/{id}")
    public ResponseEntity<Void> deleteProof(@PathVariable String id) {
        log.debug("REST request to delete Proof : {}", id);

        proofService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
