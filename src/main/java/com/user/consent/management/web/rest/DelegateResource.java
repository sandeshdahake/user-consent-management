package com.user.consent.management.web.rest;

import com.user.consent.management.domain.Delegate;
import com.user.consent.management.service.DelegateService;
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
 * REST controller for managing {@link com.user.consent.management.domain.Delegate}.
 */
@RestController
@RequestMapping("/api")
public class DelegateResource {

    private final Logger log = LoggerFactory.getLogger(DelegateResource.class);

    private static final String ENTITY_NAME = "delegate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DelegateService delegateService;

    public DelegateResource(DelegateService delegateService) {
        this.delegateService = delegateService;
    }

    /**
     * {@code POST  /delegates} : Create a new delegate.
     *
     * @param delegate the delegate to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new delegate, or with status {@code 400 (Bad Request)} if the delegate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/delegates")
    public ResponseEntity<Delegate> createDelegate(@RequestBody Delegate delegate) throws URISyntaxException {
        log.debug("REST request to save Delegate : {}", delegate);
        if (delegate.getId() != null) {
            throw new BadRequestAlertException("A new delegate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Delegate result = delegateService.save(delegate);
        return ResponseEntity.created(new URI("/api/delegates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /delegates} : Updates an existing delegate.
     *
     * @param delegate the delegate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated delegate,
     * or with status {@code 400 (Bad Request)} if the delegate is not valid,
     * or with status {@code 500 (Internal Server Error)} if the delegate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/delegates")
    public ResponseEntity<Delegate> updateDelegate(@RequestBody Delegate delegate) throws URISyntaxException {
        log.debug("REST request to update Delegate : {}", delegate);
        if (delegate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Delegate result = delegateService.save(delegate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, delegate.getId()))
            .body(result);
    }

    /**
     * {@code GET  /delegates} : get all the delegates.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of delegates in body.
     */
    @GetMapping("/delegates")
    public List<Delegate> getAllDelegates() {
        log.debug("REST request to get all Delegates");
        return delegateService.findAll();
    }

    /**
     * {@code GET  /delegates/:id} : get the "id" delegate.
     *
     * @param id the id of the delegate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the delegate, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/delegates/{id}")
    public ResponseEntity<Delegate> getDelegate(@PathVariable String id) {
        log.debug("REST request to get Delegate : {}", id);
        Optional<Delegate> delegate = delegateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(delegate);
    }

    /**
     * {@code DELETE  /delegates/:id} : delete the "id" delegate.
     *
     * @param id the id of the delegate to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delegates/{id}")
    public ResponseEntity<Void> deleteDelegate(@PathVariable String id) {
        log.debug("REST request to delete Delegate : {}", id);

        delegateService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
