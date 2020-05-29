package com.user.consent.management.web.rest;

import com.user.consent.management.ConsentApp;
import com.user.consent.management.domain.Delegate;
import com.user.consent.management.repository.DelegateRepository;
import com.user.consent.management.service.DelegateService;

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
 * Integration tests for the {@link DelegateResource} REST controller.
 */
@SpringBootTest(classes = ConsentApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DelegateResourceIT {

    private static final String DEFAULT_DELEGATE_ID = "AAAAAAAAAA";
    private static final String UPDATED_DELEGATE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private DelegateRepository delegateRepository;

    @Autowired
    private DelegateService delegateService;

    @Autowired
    private MockMvc restDelegateMockMvc;

    private Delegate delegate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Delegate createEntity() {
        Delegate delegate = new Delegate()
            .delegateId(DEFAULT_DELEGATE_ID)
            .name(DEFAULT_NAME);
        return delegate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Delegate createUpdatedEntity() {
        Delegate delegate = new Delegate()
            .delegateId(UPDATED_DELEGATE_ID)
            .name(UPDATED_NAME);
        return delegate;
    }

    @BeforeEach
    public void initTest() {
        delegateRepository.deleteAll();
        delegate = createEntity();
    }

    @Test
    public void createDelegate() throws Exception {
        int databaseSizeBeforeCreate = delegateRepository.findAll().size();
        // Create the Delegate
        restDelegateMockMvc.perform(post("/api/delegates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(delegate)))
            .andExpect(status().isCreated());

        // Validate the Delegate in the database
        List<Delegate> delegateList = delegateRepository.findAll();
        assertThat(delegateList).hasSize(databaseSizeBeforeCreate + 1);
        Delegate testDelegate = delegateList.get(delegateList.size() - 1);
        assertThat(testDelegate.getDelegateId()).isEqualTo(DEFAULT_DELEGATE_ID);
        assertThat(testDelegate.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    public void createDelegateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = delegateRepository.findAll().size();

        // Create the Delegate with an existing ID
        delegate.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restDelegateMockMvc.perform(post("/api/delegates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(delegate)))
            .andExpect(status().isBadRequest());

        // Validate the Delegate in the database
        List<Delegate> delegateList = delegateRepository.findAll();
        assertThat(delegateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllDelegates() throws Exception {
        // Initialize the database
        delegateRepository.save(delegate);

        // Get all the delegateList
        restDelegateMockMvc.perform(get("/api/delegates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(delegate.getId())))
            .andExpect(jsonPath("$.[*].delegateId").value(hasItem(DEFAULT_DELEGATE_ID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    public void getDelegate() throws Exception {
        // Initialize the database
        delegateRepository.save(delegate);

        // Get the delegate
        restDelegateMockMvc.perform(get("/api/delegates/{id}", delegate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(delegate.getId()))
            .andExpect(jsonPath("$.delegateId").value(DEFAULT_DELEGATE_ID))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    public void getNonExistingDelegate() throws Exception {
        // Get the delegate
        restDelegateMockMvc.perform(get("/api/delegates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateDelegate() throws Exception {
        // Initialize the database
        delegateService.save(delegate);

        int databaseSizeBeforeUpdate = delegateRepository.findAll().size();

        // Update the delegate
        Delegate updatedDelegate = delegateRepository.findById(delegate.getId()).get();
        updatedDelegate
            .delegateId(UPDATED_DELEGATE_ID)
            .name(UPDATED_NAME);

        restDelegateMockMvc.perform(put("/api/delegates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDelegate)))
            .andExpect(status().isOk());

        // Validate the Delegate in the database
        List<Delegate> delegateList = delegateRepository.findAll();
        assertThat(delegateList).hasSize(databaseSizeBeforeUpdate);
        Delegate testDelegate = delegateList.get(delegateList.size() - 1);
        assertThat(testDelegate.getDelegateId()).isEqualTo(UPDATED_DELEGATE_ID);
        assertThat(testDelegate.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    public void updateNonExistingDelegate() throws Exception {
        int databaseSizeBeforeUpdate = delegateRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDelegateMockMvc.perform(put("/api/delegates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(delegate)))
            .andExpect(status().isBadRequest());

        // Validate the Delegate in the database
        List<Delegate> delegateList = delegateRepository.findAll();
        assertThat(delegateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteDelegate() throws Exception {
        // Initialize the database
        delegateService.save(delegate);

        int databaseSizeBeforeDelete = delegateRepository.findAll().size();

        // Delete the delegate
        restDelegateMockMvc.perform(delete("/api/delegates/{id}", delegate.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Delegate> delegateList = delegateRepository.findAll();
        assertThat(delegateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
