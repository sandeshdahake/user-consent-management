package com.user.consent.management.web.rest;

import com.user.consent.management.ConsentApp;
import com.user.consent.management.domain.Purpose;
import com.user.consent.management.repository.PurposeRepository;
import com.user.consent.management.service.PurposeService;

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
 * Integration tests for the {@link PurposeResource} REST controller.
 */
@SpringBootTest(classes = ConsentApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PurposeResourceIT {

    private static final String DEFAULT_PURPOSE_ID = "AAAAAAAAAA";
    private static final String UPDATED_PURPOSE_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    @Autowired
    private PurposeRepository purposeRepository;

    @Autowired
    private PurposeService purposeService;

    @Autowired
    private MockMvc restPurposeMockMvc;

    private Purpose purpose;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Purpose createEntity() {
        Purpose purpose = new Purpose()
            .purposeId(DEFAULT_PURPOSE_ID)
            .enabled(DEFAULT_ENABLED);
        return purpose;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Purpose createUpdatedEntity() {
        Purpose purpose = new Purpose()
            .purposeId(UPDATED_PURPOSE_ID)
            .enabled(UPDATED_ENABLED);
        return purpose;
    }

    @BeforeEach
    public void initTest() {
        purposeRepository.deleteAll();
        purpose = createEntity();
    }

    @Test
    public void createPurpose() throws Exception {
        int databaseSizeBeforeCreate = purposeRepository.findAll().size();
        // Create the Purpose
        restPurposeMockMvc.perform(post("/api/purposes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purpose)))
            .andExpect(status().isCreated());

        // Validate the Purpose in the database
        List<Purpose> purposeList = purposeRepository.findAll();
        assertThat(purposeList).hasSize(databaseSizeBeforeCreate + 1);
        Purpose testPurpose = purposeList.get(purposeList.size() - 1);
        assertThat(testPurpose.getPurposeId()).isEqualTo(DEFAULT_PURPOSE_ID);
        assertThat(testPurpose.isEnabled()).isEqualTo(DEFAULT_ENABLED);
    }

    @Test
    public void createPurposeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = purposeRepository.findAll().size();

        // Create the Purpose with an existing ID
        purpose.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restPurposeMockMvc.perform(post("/api/purposes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purpose)))
            .andExpect(status().isBadRequest());

        // Validate the Purpose in the database
        List<Purpose> purposeList = purposeRepository.findAll();
        assertThat(purposeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkPurposeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = purposeRepository.findAll().size();
        // set the field null
        purpose.setPurposeId(null);

        // Create the Purpose, which fails.


        restPurposeMockMvc.perform(post("/api/purposes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purpose)))
            .andExpect(status().isBadRequest());

        List<Purpose> purposeList = purposeRepository.findAll();
        assertThat(purposeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkEnabledIsRequired() throws Exception {
        int databaseSizeBeforeTest = purposeRepository.findAll().size();
        // set the field null
        purpose.setEnabled(null);

        // Create the Purpose, which fails.


        restPurposeMockMvc.perform(post("/api/purposes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purpose)))
            .andExpect(status().isBadRequest());

        List<Purpose> purposeList = purposeRepository.findAll();
        assertThat(purposeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllPurposes() throws Exception {
        // Initialize the database
        purposeRepository.save(purpose);

        // Get all the purposeList
        restPurposeMockMvc.perform(get("/api/purposes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purpose.getId())))
            .andExpect(jsonPath("$.[*].purposeId").value(hasItem(DEFAULT_PURPOSE_ID)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())));
    }
    
    @Test
    public void getPurpose() throws Exception {
        // Initialize the database
        purposeRepository.save(purpose);

        // Get the purpose
        restPurposeMockMvc.perform(get("/api/purposes/{id}", purpose.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(purpose.getId()))
            .andExpect(jsonPath("$.purposeId").value(DEFAULT_PURPOSE_ID))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()));
    }
    @Test
    public void getNonExistingPurpose() throws Exception {
        // Get the purpose
        restPurposeMockMvc.perform(get("/api/purposes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updatePurpose() throws Exception {
        // Initialize the database
        purposeService.save(purpose);

        int databaseSizeBeforeUpdate = purposeRepository.findAll().size();

        // Update the purpose
        Purpose updatedPurpose = purposeRepository.findById(purpose.getId()).get();
        updatedPurpose
            .purposeId(UPDATED_PURPOSE_ID)
            .enabled(UPDATED_ENABLED);

        restPurposeMockMvc.perform(put("/api/purposes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPurpose)))
            .andExpect(status().isOk());

        // Validate the Purpose in the database
        List<Purpose> purposeList = purposeRepository.findAll();
        assertThat(purposeList).hasSize(databaseSizeBeforeUpdate);
        Purpose testPurpose = purposeList.get(purposeList.size() - 1);
        assertThat(testPurpose.getPurposeId()).isEqualTo(UPDATED_PURPOSE_ID);
        assertThat(testPurpose.isEnabled()).isEqualTo(UPDATED_ENABLED);
    }

    @Test
    public void updateNonExistingPurpose() throws Exception {
        int databaseSizeBeforeUpdate = purposeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPurposeMockMvc.perform(put("/api/purposes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purpose)))
            .andExpect(status().isBadRequest());

        // Validate the Purpose in the database
        List<Purpose> purposeList = purposeRepository.findAll();
        assertThat(purposeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deletePurpose() throws Exception {
        // Initialize the database
        purposeService.save(purpose);

        int databaseSizeBeforeDelete = purposeRepository.findAll().size();

        // Delete the purpose
        restPurposeMockMvc.perform(delete("/api/purposes/{id}", purpose.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Purpose> purposeList = purposeRepository.findAll();
        assertThat(purposeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
