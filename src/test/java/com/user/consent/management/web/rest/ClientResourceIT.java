package com.user.consent.management.web.rest;

import com.user.consent.management.ConsentApp;
import com.user.consent.management.domain.Client;
import com.user.consent.management.repository.ClientRepository;
import com.user.consent.management.service.ClientService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ClientResource} REST controller.
 */
@SpringBootTest(classes = ConsentApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ClientResourceIT {

    private static final String DEFAULT_ORGANIZATION_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZATION_USER_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CUSTOMER_ID = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private MockMvc restClientMockMvc;

    private Client client;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createEntity() {
        Client client = new Client()
            .organizationUserId(DEFAULT_ORGANIZATION_USER_ID)
            .version(DEFAULT_VERSION)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .customerId(DEFAULT_CUSTOMER_ID)
            .country(DEFAULT_COUNTRY);
        return client;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createUpdatedEntity() {
        Client client = new Client()
            .organizationUserId(UPDATED_ORGANIZATION_USER_ID)
            .version(UPDATED_VERSION)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .customerId(UPDATED_CUSTOMER_ID)
            .country(UPDATED_COUNTRY);
        return client;
    }

    @BeforeEach
    public void initTest() {
        clientRepository.deleteAll();
        client = createEntity();
    }

    @Test
    public void createClient() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().size();
        // Create the Client
        restClientMockMvc.perform(post("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isCreated());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate + 1);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getOrganizationUserId()).isEqualTo(DEFAULT_ORGANIZATION_USER_ID);
        assertThat(testClient.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testClient.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testClient.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testClient.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testClient.getCountry()).isEqualTo(DEFAULT_COUNTRY);
    }

    @Test
    public void createClientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().size();

        // Create the Client with an existing ID
        client.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientMockMvc.perform(post("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkOrganizationUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setOrganizationUserId(null);

        // Create the Client, which fails.


        restClientMockMvc.perform(post("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setCustomerId(null);

        // Create the Client, which fails.


        restClientMockMvc.perform(post("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setCountry(null);

        // Create the Client, which fails.


        restClientMockMvc.perform(post("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllClients() throws Exception {
        // Initialize the database
        clientRepository.save(client);

        // Get all the clientList
        restClientMockMvc.perform(get("/api/clients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(client.getId())))
            .andExpect(jsonPath("$.[*].organizationUserId").value(hasItem(DEFAULT_ORGANIZATION_USER_ID)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)));
    }
    
    @Test
    public void getClient() throws Exception {
        // Initialize the database
        clientRepository.save(client);

        // Get the client
        restClientMockMvc.perform(get("/api/clients/{id}", client.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(client.getId()))
            .andExpect(jsonPath("$.organizationUserId").value(DEFAULT_ORGANIZATION_USER_ID))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY));
    }
    @Test
    public void getNonExistingClient() throws Exception {
        // Get the client
        restClientMockMvc.perform(get("/api/clients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateClient() throws Exception {
        // Initialize the database
        clientService.save(client);

        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Update the client
        Client updatedClient = clientRepository.findById(client.getId()).get();
        updatedClient
            .organizationUserId(UPDATED_ORGANIZATION_USER_ID)
            .version(UPDATED_VERSION)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .customerId(UPDATED_CUSTOMER_ID)
            .country(UPDATED_COUNTRY);

        restClientMockMvc.perform(put("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedClient)))
            .andExpect(status().isOk());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getOrganizationUserId()).isEqualTo(UPDATED_ORGANIZATION_USER_ID);
        assertThat(testClient.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testClient.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testClient.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testClient.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testClient.getCountry()).isEqualTo(UPDATED_COUNTRY);
    }

    @Test
    public void updateNonExistingClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientMockMvc.perform(put("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteClient() throws Exception {
        // Initialize the database
        clientService.save(client);

        int databaseSizeBeforeDelete = clientRepository.findAll().size();

        // Delete the client
        restClientMockMvc.perform(delete("/api/clients/{id}", client.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
