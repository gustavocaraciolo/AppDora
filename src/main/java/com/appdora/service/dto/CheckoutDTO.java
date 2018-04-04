package com.appdora.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.appdora.domain.enumeration.FormaDePagamento;

/**
 * A DTO for the Checkout entity.
 */
public class CheckoutDTO implements Serializable {

    private Long id;

    private ZonedDateTime dataHora;

    private BigDecimal desconto;

    private BigDecimal precoTotal;

    private FormaDePagamento formaPagamento;

    private Long clienteId;

    private ClienteDTO clienteDTO;

    private Set<ItensCheckoutDTO> itensCheckouts = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(ZonedDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public BigDecimal getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(BigDecimal precoTotal) {
        this.precoTotal = precoTotal;
    }

    public FormaDePagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaDePagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Set<ItensCheckoutDTO> getItensCheckouts() {
        return itensCheckouts;
    }

    public void setItensCheckouts(Set<ItensCheckoutDTO> itensCheckouts) {
        this.itensCheckouts = itensCheckouts;
    }

    public ClienteDTO getClienteDTO() {
        return clienteDTO;
    }

    public void setClienteDTO(ClienteDTO clienteDTO) {
        this.clienteDTO = clienteDTO;
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
            ", desconto=" + getDesconto() +
            ", precoTotal=" + getPrecoTotal() +
            ", formaPagamento='" + getFormaPagamento() + "'" +
            "}";
    }
}
