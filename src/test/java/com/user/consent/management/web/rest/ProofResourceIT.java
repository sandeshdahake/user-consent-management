package com.user.consent.management.web.rest;

import com.user.consent.management.ConsentApp;
import com.user.consent.management.domain.Proof;
import com.user.consent.management.repository.ProofRepository;
import com.user.consent.management.service.ProofService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProofResource} REST controller.
 */
@SpringBootTest(classes = ConsentApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProofResourceIT {

    private static final String DEFAULT_FILENAME = "AAAAAAAAAA";
    private static final String UPDATED_FILENAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    @Autowired
    private ProofRepository proofRepository;

    @Autowired
    private ProofService proofService;

    @Autowired
    private MockMvc restProofMockMvc;

    private Proof proof;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Proof createEntity() {
        Proof proof = new Proof()
            .filename(DEFAULT_FILENAME)
            .file(DEFAULT_FILE)
            .fileContentType(DEFAULT_FILE_CONTENT_TYPE);
        return proof;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Proof createUpdatedEntity() {
        Proof proof = new Proof()
            .filename(UPDATED_FILENAME)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE);
        return proof;
    }

    @BeforeEach
    public void initTest() {
        proofRepository.deleteAll();
        proof = createEntity();
    }

    @Test
    public void createProof() throws Exception {
        int databaseSizeBeforeCreate = proofRepository.findAll().size();
        // Create the Proof
        restProofMockMvc.perform(post("/api/proofs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(proof)))
            .andExpect(status().isCreated());

        // Validate the Proof in the database
        List<Proof> proofList = proofRepository.findAll();
        assertThat(proofList).hasSize(databaseSizeBeforeCreate + 1);
        Proof testProof = proofList.get(proofList.size() - 1);
        assertThat(testProof.getFilename()).isEqualTo(DEFAULT_FILENAME);
        assertThat(testProof.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testProof.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);
    }

    @Test
    public void createProofWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = proofRepository.findAll().size();

        // Create the Proof with an existing ID
        proof.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restProofMockMvc.perform(post("/api/proofs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(proof)))
            .andExpect(status().isBadRequest());

        // Validate the Proof in the database
        List<Proof> proofList = proofRepository.findAll();
        assertThat(proofList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllProofs() throws Exception {
        // Initialize the database
        proofRepository.save(proof);

        // Get all the proofList
        restProofMockMvc.perform(get("/api/proofs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proof.getId())))
            .andExpect(jsonPath("$.[*].filename").value(hasItem(DEFAULT_FILENAME)))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))));
    }
    
    @Test
    public void getProof() throws Exception {
        // Initialize the database
        proofRepository.save(proof);

        // Get the proof
        restProofMockMvc.perform(get("/api/proofs/{id}", proof.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(proof.getId()))
            .andExpect(jsonPath("$.filename").value(DEFAULT_FILENAME))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64Utils.encodeToString(DEFAULT_FILE)));
    }
    @Test
    public void getNonExistingProof() throws Exception {
        // Get the proof
        restProofMockMvc.perform(get("/api/proofs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateProof() throws Exception {
        // Initialize the database
        proofService.save(proof);

        int databaseSizeBeforeUpdate = proofRepository.findAll().size();

        // Update the proof
        Proof updatedProof = proofRepository.findById(proof.getId()).get();
        updatedProof
            .filename(UPDATED_FILENAME)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE);

        restProofMockMvc.perform(put("/api/proofs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProof)))
            .andExpect(status().isOk());

        // Validate the Proof in the database
        List<Proof> proofList = proofRepository.findAll();
        assertThat(proofList).hasSize(databaseSizeBeforeUpdate);
        Proof testProof = proofList.get(proofList.size() - 1);
        assertThat(testProof.getFilename()).isEqualTo(UPDATED_FILENAME);
        assertThat(testProof.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testProof.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
    }

    @Test
    public void updateNonExistingProof() throws Exception {
        int databaseSizeBeforeUpdate = proofRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProofMockMvc.perform(put("/api/proofs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(proof)))
            .andExpect(status().isBadRequest());

        // Validate the Proof in the database
        List<Proof> proofList = proofRepository.findAll();
        assertThat(proofList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteProof() throws Exception {
        // Initialize the database
        proofService.save(proof);

        int databaseSizeBeforeDelete = proofRepository.findAll().size();

        // Delete the proof
        restProofMockMvc.perform(delete("/api/proofs/{id}", proof.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Proof> proofList = proofRepository.findAll();
        assertThat(proofList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
