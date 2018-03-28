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

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "desconto", precision=10, scale=2)
    private BigDecimal desconto;

    @OneToOne
    @JoinColumn(unique = true)
    private Cliente cliente;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "checkout_produto",
               joinColumns = @JoinColumn(name="checkouts_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="produtos_id", referencedColumnName="id"))
    private Set<Produto> produtos = new HashSet<>();

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

    public Integer getQuantidade() {
        return quantidade;
    }

    public Checkout quantidade(Integer quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
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

    public Set<Produto> getProdutos() {
        return produtos;
    }

    public Checkout produtos(Set<Produto> produtos) {
        this.produtos = produtos;
        return this;
    }

    public Checkout addProduto(Produto produto) {
        this.produtos.add(produto);
        produto.getCheckouts().add(this);
        return this;
    }

    public Checkout removeProduto(Produto produto) {
        this.produtos.remove(produto);
        produto.getCheckouts().remove(this);
        return this;
    }

    public void setProdutos(Set<Produto> produtos) {
        this.produtos = produtos;
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
            ", quantidade=" + getQuantidade() +
            ", desconto=" + getDesconto() +
            "}";
    }
}
