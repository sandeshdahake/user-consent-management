package com.user.consent.management.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Client.
 */
@Document(collection = "client")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("organization_user_id")
    private String organizationUserId;

    @Field("version")
    private Integer version;

    @Field("created_at")
    private Instant createdAt;

    @Field("updated_at")
    private Instant updatedAt;

    @NotNull
    @Field("customer_id")
    private String customerId;

    @NotNull
    @Field("country")
    private String country;

    @DBRef
    @Field("consents")
    private Consent consents;

    @DBRef
    @Field("events")
    private Set<Event> events = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrganizationUserId() {
        return organizationUserId;
    }

    public Client organizationUserId(String organizationUserId) {
        this.organizationUserId = organizationUserId;
        return this;
    }

    public void setOrganizationUserId(String organizationUserId) {
        this.organizationUserId = organizationUserId;
    }

    public Integer getVersion() {
        return version;
    }

    public Client version(Integer version) {
        this.version = version;
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Client createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Client updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Client customerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCountry() {
        return country;
    }

    public Client country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Consent getConsents() {
        return consents;
    }

    public Client consents(Consent consent) {
        this.consents = consent;
        return this;
    }

    public void setConsents(Consent consent) {
        this.consents = consent;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public Client events(Set<Event> events) {
        this.events = events;
        return this;
    }

    public Client addEvents(Event event) {
        this.events.add(event);
        event.setUser(this);
        return this;
    }

    public Client removeEvents(Event event) {
        this.events.remove(event);
        event.setUser(null);
        return this;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", organizationUserId='" + getOrganizationUserId() + "'" +
            ", version=" + getVersion() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", customerId='" + getCustomerId() + "'" +
            ", country='" + getCountry() + "'" +
            "}";
    }
}
