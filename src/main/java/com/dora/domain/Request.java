package com.dora.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Request.
 */
@Entity
@Table(name = "request")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Request implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_request")
    private ZonedDateTime dateRequest;

    @ManyToOne(optional = false)
    @NotNull
    private UserExtra userExtra;

    @ManyToOne
    private RequestItems requestItems;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateRequest() {
        return dateRequest;
    }

    public Request dateRequest(ZonedDateTime dateRequest) {
        this.dateRequest = dateRequest;
        return this;
    }

    public void setDateRequest(ZonedDateTime dateRequest) {
        this.dateRequest = dateRequest;
    }

    public UserExtra getUserExtra() {
        return userExtra;
    }

    public Request userExtra(UserExtra userExtra) {
        this.userExtra = userExtra;
        return this;
    }

    public void setUserExtra(UserExtra userExtra) {
        this.userExtra = userExtra;
    }

    public RequestItems getRequestItems() {
        return requestItems;
    }

    public Request requestItems(RequestItems requestItems) {
        this.requestItems = requestItems;
        return this;
    }

    public void setRequestItems(RequestItems requestItems) {
        this.requestItems = requestItems;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Request request = (Request) o;
        if (request.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), request.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Request{" +
            "id=" + getId() +
            ", dateRequest='" + getDateRequest() + "'" +
            "}";
    }
}
