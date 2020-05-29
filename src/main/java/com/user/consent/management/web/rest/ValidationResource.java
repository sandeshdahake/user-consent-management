package com.user.consent.management.web.rest;

import com.user.consent.management.domain.Validation;
import com.user.consent.management.service.ValidationService;
import com.user.consent.management.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.user.consent.management.domain.Validation}.
 */
@RestController
@RequestMapping("/api")
public class ValidationResource {

    private final Logger log = LoggerFactory.getLogger(ValidationResource.class);

    private static final String ENTITY_NAME = "validation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ValidationService validationService;

    public ValidationResource(ValidationService validationService) {
        this.validationService = validationService;
    }

    /**
     * {@code POST  /validations} : Create a new validation.
     *
     * @param validation the validation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new validation, or with status {@code 400 (Bad Request)} if the validation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/validations")
    public ResponseEntity<Validation> createValidation(@RequestBody Validation validation) throws URISyntaxException {
        log.debug("REST request to save Validation : {}", validation);
        if (validation.getId() != null) {
            throw new BadRequestAlertException("A new validation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Validation result = validationService.save(validation);
        return ResponseEntity.created(new URI("/api/validations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /validations} : Updates an existing validation.
     *
     * @param validation the validation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated validation,
     * or with status {@code 400 (Bad Request)} if the validation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the validation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/validations")
    public ResponseEntity<Validation> updateValidation(@RequestBody Validation validation) throws URISyntaxException {
        log.debug("REST request to update Validation : {}", validation);
        if (validation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Validation result = validationService.save(validation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, validation.getId()))
            .body(result);
    }

    /**
     * {@code GET  /validations} : get all the validations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of validations in body.
     */
    @GetMapping("/validations")
    public List<Validation> getAllValidations() {
        log.debug("REST request to get all Validations");
        return validationService.findAll();
    }

    /**
     * {@code GET  /validations/:id} : get the "id" validation.
     *
     * @param id the id of the validation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the validation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/validations/{id}")
    public ResponseEntity<Validation> getValidation(@PathVariable String id) {
        log.debug("REST request to get Validation : {}", id);
        Optional<Validation> validation = validationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(validation);
    }

    /**
     * {@code DELETE  /validations/:id} : delete the "id" validation.
     *
     * @param id the id of the validation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/validations/{id}")
    public ResponseEntity<Void> deleteValidation(@PathVariable String id) {
        log.debug("REST request to delete Validation : {}", id);

        validationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
