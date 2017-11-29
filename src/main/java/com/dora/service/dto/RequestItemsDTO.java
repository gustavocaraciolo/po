package com.dora.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the RequestItems entity.
 */
public class RequestItemsDTO implements Serializable {

    private Long id;

    private BigDecimal discount;

    private Long productId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RequestItemsDTO requestItemsDTO = (RequestItemsDTO) o;
        if(requestItemsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), requestItemsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RequestItemsDTO{" +
            "id=" + getId() +
            ", discount='" + getDiscount() + "'" +
            "}";
    }
}
