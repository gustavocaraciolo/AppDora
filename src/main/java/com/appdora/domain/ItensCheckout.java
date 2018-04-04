package com.appdora.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ItensCheckout.
 */
@Entity
@Table(name = "itens_checkout")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "itenscheckout")
public class ItensCheckout implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantidade")
    private Integer quantidade;

    @ManyToOne
    private Produto produto;

    @ManyToMany(mappedBy = "itensCheckouts")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Checkout> checkouts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public ItensCheckout quantidade(Integer quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public ItensCheckout produto(Produto produto) {
        this.produto = produto;
        return this;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Set<Checkout> getCheckouts() {
        return checkouts;
    }

    public ItensCheckout checkouts(Set<Checkout> checkouts) {
        this.checkouts = checkouts;
        return this;
    }

    public ItensCheckout addCheckout(Checkout checkout) {
        this.checkouts.add(checkout);
        checkout.getItensCheckouts().add(this);
        return this;
    }

    public ItensCheckout removeCheckout(Checkout checkout) {
        this.checkouts.remove(checkout);
        checkout.getItensCheckouts().remove(this);
        return this;
    }

    public void setCheckouts(Set<Checkout> checkouts) {
        this.checkouts = checkouts;
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
        ItensCheckout itensCheckout = (ItensCheckout) o;
        if (itensCheckout.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), itensCheckout.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ItensCheckout{" +
            "id=" + getId() +
            ", quantidade=" + getQuantidade() +
            "}";
    }
}
