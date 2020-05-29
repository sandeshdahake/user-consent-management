package com.user.consent.management.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * A Delegate.
 */
@Document(collection = "delegate")
public class Delegate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("delegate_id")
    private String delegateId;

    @Field("name")
    private String name;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDelegateId() {
        return delegateId;
    }

    public Delegate delegateId(String delegateId) {
        this.delegateId = delegateId;
        return this;
    }

    public void setDelegateId(String delegateId) {
        this.delegateId = delegateId;
    }

    public String getName() {
        return name;
    }

    public Delegate name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Delegate)) {
            return false;
        }
        return id != null && id.equals(((Delegate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Delegate{" +
            "id=" + getId() +
            ", delegateId='" + getDelegateId() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
