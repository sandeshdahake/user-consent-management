package com.user.consent.management.web.rest;

import com.user.consent.management.ConsentApp;
import com.user.consent.management.domain.Validation;
import com.user.consent.management.repository.ValidationRepository;
import com.user.consent.management.service.ValidationService;

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
 * Integration tests for the {@link ValidationResource} REST controller.
 */
@SpringBootTest(classes = ConsentApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ValidationResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private ValidationRepository validationRepository;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private MockMvc restValidationMockMvc;

    private Validation validation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Validation createEntity() {
        Validation validation = new Validation()
            .type(DEFAULT_TYPE);
        return validation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Validation createUpdatedEntity() {
        Validation validation = new Validation()
            .type(UPDATED_TYPE);
        return validation;
    }

    @BeforeEach
    public void initTest() {
        validationRepository.deleteAll();
        validation = createEntity();
    }

    @Test
    public void createValidation() throws Exception {
        int databaseSizeBeforeCreate = validationRepository.findAll().size();
        // Create the Validation
        restValidationMockMvc.perform(post("/api/validations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(validation)))
            .andExpect(status().isCreated());

        // Validate the Validation in the database
        List<Validation> validationList = validationRepository.findAll();
        assertThat(validationList).hasSize(databaseSizeBeforeCreate + 1);
        Validation testValidation = validationList.get(validationList.size() - 1);
        assertThat(testValidation.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    public void createValidationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = validationRepository.findAll().size();

        // Create the Validation with an existing ID
        validation.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restValidationMockMvc.perform(post("/api/validations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(validation)))
            .andExpect(status().isBadRequest());

        // Validate the Validation in the database
        List<Validation> validationList = validationRepository.findAll();
        assertThat(validationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllValidations() throws Exception {
        // Initialize the database
        validationRepository.save(validation);

        // Get all the validationList
        restValidationMockMvc.perform(get("/api/validations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(validation.getId())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }
    
    @Test
    public void getValidation() throws Exception {
        // Initialize the database
        validationRepository.save(validation);

        // Get the validation
        restValidationMockMvc.perform(get("/api/validations/{id}", validation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(validation.getId()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }
    @Test
    public void getNonExistingValidation() throws Exception {
        // Get the validation
        restValidationMockMvc.perform(get("/api/validations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateValidation() throws Exception {
        // Initialize the database
        validationService.save(validation);

        int databaseSizeBeforeUpdate = validationRepository.findAll().size();

        // Update the validation
        Validation updatedValidation = validationRepository.findById(validation.getId()).get();
        updatedValidation
            .type(UPDATED_TYPE);

        restValidationMockMvc.perform(put("/api/validations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedValidation)))
            .andExpect(status().isOk());

        // Validate the Validation in the database
        List<Validation> validationList = validationRepository.findAll();
        assertThat(validationList).hasSize(databaseSizeBeforeUpdate);
        Validation testValidation = validationList.get(validationList.size() - 1);
        assertThat(testValidation.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    public void updateNonExistingValidation() throws Exception {
        int databaseSizeBeforeUpdate = validationRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restValidationMockMvc.perform(put("/api/validations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(validation)))
            .andExpect(status().isBadRequest());

        // Validate the Validation in the database
        List<Validation> validationList = validationRepository.findAll();
        assertThat(validationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteValidation() throws Exception {
        // Initialize the database
        validationService.save(validation);

        int databaseSizeBeforeDelete = validationRepository.findAll().size();

        // Delete the validation
        restValidationMockMvc.perform(delete("/api/validations/{id}", validation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Validation> validationList = validationRepository.findAll();
        assertThat(validationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
