package com.appdora.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.appdora.domain.enumeration.FlagSimNao;

/**
 * A Portal.
 */
@Entity
@Table(name = "portal")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "portal")
public class Portal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "data_ativacao")
    private Instant dataAtivacao;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "flag_default", nullable = false)
    private FlagSimNao flagDefault;

    @NotNull
    @Column(name = "tipo_idioma", nullable = false)
    private String tipoIdioma;

    @Enumerated(EnumType.STRING)
    @Column(name = "flag_senha_encriptada")
    private FlagSimNao flagSenhaEncriptada;

    @ManyToMany(mappedBy = "portals")    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIgnore    private Set<Utilizador> utilizdors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Portal descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Instant getDataAtivacao() {
        return dataAtivacao;
    }

    public Portal dataAtivacao(Instant dataAtivacao) {
        this.dataAtivacao = dataAtivacao;
        return this;
    }

    public void setDataAtivacao(Instant dataAtivacao) {
        this.dataAtivacao = dataAtivacao;
    }

    public FlagSimNao getFlagDefault() {
        return flagDefault;
    }

    public Portal flagDefault(FlagSimNao flagDefault) {
        this.flagDefault = flagDefault;
        return this;
    }

    public void setFlagDefault(FlagSimNao flagDefault) {
        this.flagDefault = flagDefault;
    }

    public String getTipoIdioma() {
        return tipoIdioma;
    }

    public Portal tipoIdioma(String tipoIdioma) {
        this.tipoIdioma = tipoIdioma;
        return this;
    }

    public void setTipoIdioma(String tipoIdioma) {
        this.tipoIdioma = tipoIdioma;
    }

    public FlagSimNao getFlagSenhaEncriptada() {
        return flagSenhaEncriptada;
    }

    public Portal flagSenhaEncriptada(FlagSimNao flagSenhaEncriptada) {
        this.flagSenhaEncriptada = flagSenhaEncriptada;
        return this;
    }

    public void setFlagSenhaEncriptada(FlagSimNao flagSenhaEncriptada) {
        this.flagSenhaEncriptada = flagSenhaEncriptada;
    }

    public Set<Utilizador> getUtilizdors() {
        return utilizdors;
    }

    public Portal utilizdors(Set<Utilizador> utilizadors) {
        this.utilizdors = utilizadors;
        return this;
    }

    public Portal addUtilizdor(Utilizador utilizador) {
        this.utilizdors.add(utilizador);
        utilizador.getPortals().add(this);
        return this;
    }

    public Portal removeUtilizdor(Utilizador utilizador) {
        this.utilizdors.remove(utilizador);
        utilizador.getPortals().remove(this);
        return this;
    }

    public void setUtilizdors(Set<Utilizador> utilizadors) {
        this.utilizdors = utilizadors;
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
        Portal portal = (Portal) o;
        if (portal.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), portal.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Portal{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", dataAtivacao='" + getDataAtivacao() + "'" +
            ", flagDefault='" + getFlagDefault() + "'" +
            ", tipoIdioma='" + getTipoIdioma() + "'" +
            ", flagSenhaEncriptada='" + getFlagSenhaEncriptada() + "'" +
            "}";
    }
}
