package com.appdora.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Checkout entity.
 */
public class CheckoutDTO implements Serializable {

    private Long id;

    private ZonedDateTime dataHora;

    private Integer quantidade;

    private BigDecimal desconto;

    private Long clienteId;

    public CheckoutDTO(Integer quantidade, BigDecimal desconto, Long clienteId, Set<ProdutoDTO> produtos) {
        this.quantidade = quantidade;
        this.desconto = desconto;
        this.clienteId = clienteId;
        this.produtos = produtos;
    }

    public CheckoutDTO() {
    }

    private Set<ProdutoDTO> produtos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDataHora() {
        return ZonedDateTime.now();
    }

    public void setDataHora(ZonedDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Set<ProdutoDTO> getProdutos() {
        return produtos;
    }

    public void setProdutos(Set<ProdutoDTO> produtos) {
        this.produtos = produtos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CheckoutDTO checkoutDTO = (CheckoutDTO) o;
        if(checkoutDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), checkoutDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CheckoutDTO{" +
            "id=" + getId() +
            ", dataHora='" + getDataHora() + "'" +
            ", quantidade=" + getQuantidade() +
            ", desconto=" + getDesconto() +
            "}";
    }
}
