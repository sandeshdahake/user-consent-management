package com.user.consent.management.web.rest;

import com.user.consent.management.ConsentApp;
import com.user.consent.management.domain.Consent;
import com.user.consent.management.repository.ConsentRepository;
import com.user.consent.management.service.ConsentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ConsentResource} REST controller.
 */
@SpringBootTest(classes = ConsentApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConsentResourceIT {

    @Autowired
    private ConsentRepository consentRepository;

    @Autowired
    private ConsentService consentService;

    @Autowired
    private MockMvc restConsentMockMvc;

    private Consent consent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Consent createEntity() {
        Consent consent = new Consent();
        return consent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Consent createUpdatedEntity() {
        Consent consent = new Consent();
        return consent;
    }

    @BeforeEach
    public void initTest() {
        consentRepository.deleteAll();
        consent = createEntity();
    }

    @Test
    public void createConsent() throws Exception {
        int databaseSizeBeforeCreate = consentRepository.findAll().size();
        // Create the Consent
        restConsentMockMvc.perform(post("/api/consents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consent)))
            .andExpect(status().isCreated());

        // Validate the Consent in the database
        List<Consent> consentList = consentRepository.findAll();
        assertThat(consentList).hasSize(databaseSizeBeforeCreate + 1);
        Consent testConsent = consentList.get(consentList.size() - 1);
    }

    @Test
    public void createConsentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = consentRepository.findAll().size();

        // Create the Consent with an existing ID
        consent.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsentMockMvc.perform(post("/api/consents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consent)))
            .andExpect(status().isBadRequest());

        // Validate the Consent in the database
        List<Consent> consentList = consentRepository.findAll();
        assertThat(consentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllConsents() throws Exception {
        // Initialize the database
        consentRepository.save(consent);

        // Get all the consentList
        restConsentMockMvc.perform(get("/api/consents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consent.getId())));
    }
    
    @Test
    public void getConsent() throws Exception {
        // Initialize the database
        consentRepository.save(consent);

        // Get the consent
        restConsentMockMvc.perform(get("/api/consents/{id}", consent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(consent.getId()));
    }
    @Test
    public void getNonExistingConsent() throws Exception {
        // Get the consent
        restConsentMockMvc.perform(get("/api/consents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateConsent() throws Exception {
        // Initialize the database
        consentService.save(consent);

        int databaseSizeBeforeUpdate = consentRepository.findAll().size();

        // Update the consent
        Consent updatedConsent = consentRepository.findById(consent.getId()).get();

        restConsentMockMvc.perform(put("/api/consents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConsent)))
            .andExpect(status().isOk());

        // Validate the Consent in the database
        List<Consent> consentList = consentRepository.findAll();
        assertThat(consentList).hasSize(databaseSizeBeforeUpdate);
        Consent testConsent = consentList.get(consentList.size() - 1);
    }

    @Test
    public void updateNonExistingConsent() throws Exception {
        int databaseSizeBeforeUpdate = consentRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConsentMockMvc.perform(put("/api/consents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consent)))
            .andExpect(status().isBadRequest());

        // Validate the Consent in the database
        List<Consent> consentList = consentRepository.findAll();
        assertThat(consentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteConsent() throws Exception {
        // Initialize the database
        consentService.save(consent);

        int databaseSizeBeforeDelete = consentRepository.findAll().size();

        // Delete the consent
        restConsentMockMvc.perform(delete("/api/consents/{id}", consent.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Consent> consentList = consentRepository.findAll();
        assertThat(consentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
