package com.user.consent.management.web.rest;

import com.user.consent.management.domain.Purpose;
import com.user.consent.management.service.PurposeService;
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
 * REST controller for managing {@link com.user.consent.management.domain.Purpose}.
 */
@RestController
@RequestMapping("/api")
public class PurposeResource {

    private final Logger log = LoggerFactory.getLogger(PurposeResource.class);

    private static final String ENTITY_NAME = "purpose";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PurposeService purposeService;

    public PurposeResource(PurposeService purposeService) {
        this.purposeService = purposeService;
    }

    /**
     * {@code POST  /purposes} : Create a new purpose.
     *
     * @param purpose the purpose to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new purpose, or with status {@code 400 (Bad Request)} if the purpose has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/purposes")
    public ResponseEntity<Purpose> createPurpose(@Valid @RequestBody Purpose purpose) throws URISyntaxException {
        log.debug("REST request to save Purpose : {}", purpose);
        if (purpose.getId() != null) {
            throw new BadRequestAlertException("A new purpose cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Purpose result = purposeService.save(purpose);
        return ResponseEntity.created(new URI("/api/purposes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /purposes} : Updates an existing purpose.
     *
     * @param purpose the purpose to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated purpose,
     * or with status {@code 400 (Bad Request)} if the purpose is not valid,
     * or with status {@code 500 (Internal Server Error)} if the purpose couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/purposes")
    public ResponseEntity<Purpose> updatePurpose(@Valid @RequestBody Purpose purpose) throws URISyntaxException {
        log.debug("REST request to update Purpose : {}", purpose);
        if (purpose.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Purpose result = purposeService.save(purpose);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, purpose.getId()))
            .body(result);
    }

    /**
     * {@code GET  /purposes} : get all the purposes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of purposes in body.
     */
    @GetMapping("/purposes")
    public List<Purpose> getAllPurposes() {
        log.debug("REST request to get all Purposes");
        return purposeService.findAll();
    }

    /**
     * {@code GET  /purposes/:id} : get the "id" purpose.
     *
     * @param id the id of the purpose to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the purpose, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/purposes/{id}")
    public ResponseEntity<Purpose> getPurpose(@PathVariable String id) {
        log.debug("REST request to get Purpose : {}", id);
        Optional<Purpose> purpose = purposeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(purpose);
    }

    /**
     * {@code DELETE  /purposes/:id} : delete the "id" purpose.
     *
     * @param id the id of the purpose to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/purposes/{id}")
    public ResponseEntity<Void> deletePurpose(@PathVariable String id) {
        log.debug("REST request to delete Purpose : {}", id);

        purposeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
