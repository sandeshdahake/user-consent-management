package com.user.consent.management.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.user.consent.management.domain.enumeration.Status;

/**
 * A Event.
 */
@Document(collection = "event")
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("organization_id")
    private String organizationId;

    @Field("created_at")
    private Instant createdAt;

    @NotNull
    @Field("status")
    private Status status;

    @DBRef
    @Field("delegate")
    private Delegate delegate;

    @DBRef
    @Field("validation")
    private Validation validation;

    @DBRef
    @Field("consents")
    private Consent consents;

    @DBRef
    @Field("proofs")
    private Set<Proof> proofs = new HashSet<>();

    @DBRef
    @Field("user")
    @JsonIgnoreProperties(value = "events", allowSetters = true)
    private Client user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public Event organizationId(String organizationId) {
        this.organizationId = organizationId;
        return this;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Event createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Status getStatus() {
        return status;
    }

    public Event status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Delegate getDelegate() {
        return delegate;
    }

    public Event delegate(Delegate delegate) {
        this.delegate = delegate;
        return this;
    }

    public void setDelegate(Delegate delegate) {
        this.delegate = delegate;
    }

    public Validation getValidation() {
        return validation;
    }

    public Event validation(Validation validation) {
        this.validation = validation;
        return this;
    }

    public void setValidation(Validation validation) {
        this.validation = validation;
    }

    public Consent getConsents() {
        return consents;
    }

    public Event consents(Consent consent) {
        this.consents = consent;
        return this;
    }

    public void setConsents(Consent consent) {
        this.consents = consent;
    }

    public Set<Proof> getProofs() {
        return proofs;
    }

    public Event proofs(Set<Proof> proofs) {
        this.proofs = proofs;
        return this;
    }

    public Event addProofs(Proof proof) {
        this.proofs.add(proof);
        proof.setEvent(this);
        return this;
    }

    public Event removeProofs(Proof proof) {
        this.proofs.remove(proof);
        proof.setEvent(null);
        return this;
    }

    public void setProofs(Set<Proof> proofs) {
        this.proofs = proofs;
    }

    public Client getUser() {
        return user;
    }

    public Event user(Client client) {
        this.user = client;
        return this;
    }

    public void setUser(Client client) {
        this.user = client;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Event)) {
            return false;
        }
        return id != null && id.equals(((Event) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Event{" +
            "id=" + getId() +
            ", organizationId='" + getOrganizationId() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
