package com.user.consent.management.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Consent.
 */
@Document(collection = "consent")
public class Consent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @DBRef
    @Field("purposes")
    private Set<Purpose> purposes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<Purpose> getPurposes() {
        return purposes;
    }

    public Consent purposes(Set<Purpose> purposes) {
        this.purposes = purposes;
        return this;
    }

    public Consent addPurposes(Purpose purpose) {
        this.purposes.add(purpose);
        purpose.setConsent(this);
        return this;
    }

    public Consent removePurposes(Purpose purpose) {
        this.purposes.remove(purpose);
        purpose.setConsent(null);
        return this;
    }

    public void setPurposes(Set<Purpose> purposes) {
        this.purposes = purposes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Consent)) {
            return false;
        }
        return id != null && id.equals(((Consent) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Consent{" +
            "id=" + getId() +
            "}";
    }
}
