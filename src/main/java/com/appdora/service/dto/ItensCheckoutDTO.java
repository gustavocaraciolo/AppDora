package com.appdora.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ItensCheckout entity.
 */
public class ItensCheckoutDTO implements Serializable {

    private Long id;

    private Integer quantidade;

    private Long produtoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ItensCheckoutDTO itensCheckoutDTO = (ItensCheckoutDTO) o;
        if(itensCheckoutDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), itensCheckoutDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ItensCheckoutDTO{" +
            "id=" + getId() +
            ", quantidade=" + getQuantidade() +
            "}";
    }
}
