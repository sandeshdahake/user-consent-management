package com.user.consent.management.web.rest;

import com.user.consent.management.domain.Consent;
import com.user.consent.management.service.ConsentService;
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
 * REST controller for managing {@link com.user.consent.management.domain.Consent}.
 */
@RestController
@RequestMapping("/api")
public class ConsentResource {

    private final Logger log = LoggerFactory.getLogger(ConsentResource.class);

    private static final String ENTITY_NAME = "consent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConsentService consentService;

    public ConsentResource(ConsentService consentService) {
        this.consentService = consentService;
    }

    /**
     * {@code POST  /consents} : Create a new consent.
     *
     * @param consent the consent to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new consent, or with status {@code 400 (Bad Request)} if the consent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/consents")
    public ResponseEntity<Consent> createConsent(@RequestBody Consent consent) throws URISyntaxException {
        log.debug("REST request to save Consent : {}", consent);
        if (consent.getId() != null) {
            throw new BadRequestAlertException("A new consent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Consent result = consentService.save(consent);
        return ResponseEntity.created(new URI("/api/consents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /consents} : Updates an existing consent.
     *
     * @param consent the consent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated consent,
     * or with status {@code 400 (Bad Request)} if the consent is not valid,
     * or with status {@code 500 (Internal Server Error)} if the consent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/consents")
    public ResponseEntity<Consent> updateConsent(@RequestBody Consent consent) throws URISyntaxException {
        log.debug("REST request to update Consent : {}", consent);
        if (consent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Consent result = consentService.save(consent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, consent.getId()))
            .body(result);
    }

    /**
     * {@code GET  /consents} : get all the consents.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of consents in body.
     */
    @GetMapping("/consents")
    public List<Consent> getAllConsents() {
        log.debug("REST request to get all Consents");
        return consentService.findAll();
    }

    /**
     * {@code GET  /consents/:id} : get the "id" consent.
     *
     * @param id the id of the consent to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the consent, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/consents/{id}")
    public ResponseEntity<Consent> getConsent(@PathVariable String id) {
        log.debug("REST request to get Consent : {}", id);
        Optional<Consent> consent = consentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(consent);
    }

    /**
     * {@code DELETE  /consents/:id} : delete the "id" consent.
     *
     * @param id the id of the consent to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/consents/{id}")
    public ResponseEntity<Void> deleteConsent(@PathVariable String id) {
        log.debug("REST request to delete Consent : {}", id);

        consentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
