package com.dora.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A RequestItems.
 */
@Entity
@Table(name = "request_items")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RequestItems implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "discount", precision=10, scale=2)
    private BigDecimal discount;

    @OneToMany(mappedBy = "requestItems")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Request> pedidos = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public RequestItems discount(BigDecimal discount) {
        this.discount = discount;
        return this;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Set<Request> getPedidos() {
        return pedidos;
    }

    public RequestItems pedidos(Set<Request> requests) {
        this.pedidos = requests;
        return this;
    }

    public RequestItems addPedido(Request request) {
        this.pedidos.add(request);
        request.setRequestItems(this);
        return this;
    }

    public RequestItems removePedido(Request request) {
        this.pedidos.remove(request);
        request.setRequestItems(null);
        return this;
    }

    public void setPedidos(Set<Request> requests) {
        this.pedidos = requests;
    }

    public Product getProduct() {
        return product;
    }

    public RequestItems product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
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
        RequestItems requestItems = (RequestItems) o;
        if (requestItems.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), requestItems.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RequestItems{" +
            "id=" + getId() +
            ", discount='" + getDiscount() + "'" +
            "}";
    }
}
