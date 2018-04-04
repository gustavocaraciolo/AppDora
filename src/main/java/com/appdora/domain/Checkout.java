package com.appdora.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.appdora.domain.enumeration.FormaDePagamento;

/**
 * A Checkout.
 */
@Entity
@Table(name = "checkout")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "checkout")
public class Checkout implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "data_hora", nullable = false)
    private ZonedDateTime dataHora;

    @Column(name = "desconto", precision=10, scale=2)
    private BigDecimal desconto;

    @Column(name = "preco_total", precision=10, scale=2)
    private BigDecimal precoTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "forma_pagamento")
    private FormaDePagamento formaPagamento;

    @ManyToOne
    private Cliente cliente;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "checkout_itens_checkout",
               joinColumns = @JoinColumn(name="checkouts_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="itens_checkouts_id", referencedColumnName="id"))
    private Set<ItensCheckout> itensCheckouts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDataHora() {
        return dataHora;
    }

    public Checkout dataHora(ZonedDateTime dataHora) {
        this.dataHora = dataHora;
        return this;
    }

    public void setDataHora(ZonedDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public Checkout desconto(BigDecimal desconto) {
        this.desconto = desconto;
        return this;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public BigDecimal getPrecoTotal() {
        return precoTotal;
    }

    public Checkout precoTotal(BigDecimal precoTotal) {
        this.precoTotal = precoTotal;
        return this;
    }

    public void setPrecoTotal(BigDecimal precoTotal) {
        this.precoTotal = precoTotal;
    }

    public FormaDePagamento getFormaPagamento() {
        return formaPagamento;
    }

    public Checkout formaPagamento(FormaDePagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
        return this;
    }

    public void setFormaPagamento(FormaDePagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Checkout cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Set<ItensCheckout> getItensCheckouts() {
        return itensCheckouts;
    }

    public Checkout itensCheckouts(Set<ItensCheckout> itensCheckouts) {
        this.itensCheckouts = itensCheckouts;
        return this;
    }

    public Checkout addItensCheckout(ItensCheckout itensCheckout) {
        this.itensCheckouts.add(itensCheckout);
        itensCheckout.getCheckouts().add(this);
        return this;
    }

    public Checkout removeItensCheckout(ItensCheckout itensCheckout) {
        this.itensCheckouts.remove(itensCheckout);
        itensCheckout.getCheckouts().remove(this);
        return this;
    }

    public void setItensCheckouts(Set<ItensCheckout> itensCheckouts) {
        this.itensCheckouts = itensCheckouts;
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
        Checkout checkout = (Checkout) o;
        if (checkout.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), checkout.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Checkout{" +
            "id=" + getId() +
            ", dataHora='" + getDataHora() + "'" +
            ", desconto=" + getDesconto() +
            ", precoTotal=" + getPrecoTotal() +
            ", formaPagamento='" + getFormaPagamento() + "'" +
            "}";
    }
}
