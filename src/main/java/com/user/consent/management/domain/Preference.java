package com.user.consent.management.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Preference.
 */
@Document(collection = "preference")
public class Preference implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("preference_id")
    private String preferenceId;

    @NotNull
    @Field("enabled")
    private Boolean enabled;

    @DBRef
    @Field("purpose")
    @JsonIgnoreProperties(value = "preferences", allowSetters = true)
    private Purpose purpose;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPreferenceId() {
        return preferenceId;
    }

    public Preference preferenceId(String preferenceId) {
        this.preferenceId = preferenceId;
        return this;
    }

    public void setPreferenceId(String preferenceId) {
        this.preferenceId = preferenceId;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public Preference enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Purpose getPurpose() {
        return purpose;
    }

    public Preference purpose(Purpose purpose) {
        this.purpose = purpose;
        return this;
    }

    public void setPurpose(Purpose purpose) {
        this.purpose = purpose;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Preference)) {
            return false;
        }
        return id != null && id.equals(((Preference) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Preference{" +
            "id=" + getId() +
            ", preferenceId='" + getPreferenceId() + "'" +
            ", enabled='" + isEnabled() + "'" +
            "}";
    }
}
