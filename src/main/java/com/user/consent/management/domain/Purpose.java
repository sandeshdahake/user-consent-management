package com.user.consent.management.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Purpose.
 */
@Document(collection = "purpose")
public class Purpose implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("purpose_id")
    private String purposeId;

    @NotNull
    @Field("enabled")
    private Boolean enabled;

    @DBRef
    @Field("preferences")
    private Set<Preference> preferences = new HashSet<>();

    @DBRef
    @Field("consent")
    @JsonIgnoreProperties(value = "purposes", allowSetters = true)
    private Consent consent;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPurposeId() {
        return purposeId;
    }

    public Purpose purposeId(String purposeId) {
        this.purposeId = purposeId;
        return this;
    }

    public void setPurposeId(String purposeId) {
        this.purposeId = purposeId;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public Purpose enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Preference> getPreferences() {
        return preferences;
    }

    public Purpose preferences(Set<Preference> preferences) {
        this.preferences = preferences;
        return this;
    }

    public Purpose addPreferences(Preference preference) {
        this.preferences.add(preference);
        preference.setPurpose(this);
        return this;
    }

    public Purpose removePreferences(Preference preference) {
        this.preferences.remove(preference);
        preference.setPurpose(null);
        return this;
    }

    public void setPreferences(Set<Preference> preferences) {
        this.preferences = preferences;
    }

    public Consent getConsent() {
        return consent;
    }

    public Purpose consent(Consent consent) {
        this.consent = consent;
        return this;
    }

    public void setConsent(Consent consent) {
        this.consent = consent;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Purpose)) {
            return false;
        }
        return id != null && id.equals(((Purpose) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Purpose{" +
            "id=" + getId() +
            ", purposeId='" + getPurposeId() + "'" +
            ", enabled='" + isEnabled() + "'" +
            "}";
    }
}
