package com.user.consent.management.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Proof.
 */
@Document(collection = "proof")
public class Proof implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("filename")
    private String filename;

    
    @Field("file")
    private byte[] file;

    @Field("file_content_type")
    private String fileContentType;

    @DBRef
    @Field("event")
    @JsonIgnoreProperties(value = "proofs", allowSetters = true)
    private Event event;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public Proof filename(String filename) {
        this.filename = filename;
        return this;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getFile() {
        return file;
    }

    public Proof file(byte[] file) {
        this.file = file;
        return this;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public Proof fileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
        return this;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public Event getEvent() {
        return event;
    }

    public Proof event(Event event) {
        this.event = event;
        return this;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Proof)) {
            return false;
        }
        return id != null && id.equals(((Proof) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Proof{" +
            "id=" + getId() +
            ", filename='" + getFilename() + "'" +
            ", file='" + getFile() + "'" +
            ", fileContentType='" + getFileContentType() + "'" +
            "}";
    }
}
