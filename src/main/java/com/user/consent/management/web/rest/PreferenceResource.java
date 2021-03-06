package com.user.consent.management.web.rest;

import com.user.consent.management.domain.Preference;
import com.user.consent.management.service.PreferenceService;
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
 * REST controller for managing {@link com.user.consent.management.domain.Preference}.
 */
@RestController
@RequestMapping("/api")
public class PreferenceResource {

    private final Logger log = LoggerFactory.getLogger(PreferenceResource.class);

    private static final String ENTITY_NAME = "preference";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PreferenceService preferenceService;

    public PreferenceResource(PreferenceService preferenceService) {
        this.preferenceService = preferenceService;
    }

    /**
     * {@code POST  /preferences} : Create a new preference.
     *
     * @param preference the preference to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new preference, or with status {@code 400 (Bad Request)} if the preference has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/preferences")
    public ResponseEntity<Preference> createPreference(@Valid @RequestBody Preference preference) throws URISyntaxException {
        log.debug("REST request to save Preference : {}", preference);
        if (preference.getId() != null) {
            throw new BadRequestAlertException("A new preference cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Preference result = preferenceService.save(preference);
        return ResponseEntity.created(new URI("/api/preferences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /preferences} : Updates an existing preference.
     *
     * @param preference the preference to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated preference,
     * or with status {@code 400 (Bad Request)} if the preference is not valid,
     * or with status {@code 500 (Internal Server Error)} if the preference couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/preferences")
    public ResponseEntity<Preference> updatePreference(@Valid @RequestBody Preference preference) throws URISyntaxException {
        log.debug("REST request to update Preference : {}", preference);
        if (preference.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Preference result = preferenceService.save(preference);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, preference.getId()))
            .body(result);
    }

    /**
     * {@code GET  /preferences} : get all the preferences.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of preferences in body.
     */
    @GetMapping("/preferences")
    public List<Preference> getAllPreferences() {
        log.debug("REST request to get all Preferences");
        return preferenceService.findAll();
    }

    /**
     * {@code GET  /preferences/:id} : get the "id" preference.
     *
     * @param id the id of the preference to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the preference, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/preferences/{id}")
    public ResponseEntity<Preference> getPreference(@PathVariable String id) {
        log.debug("REST request to get Preference : {}", id);
        Optional<Preference> preference = preferenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(preference);
    }

    /**
     * {@code DELETE  /preferences/:id} : delete the "id" preference.
     *
     * @param id the id of the preference to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/preferences/{id}")
    public ResponseEntity<Void> deletePreference(@PathVariable String id) {
        log.debug("REST request to delete Preference : {}", id);

        preferenceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
