package com.appdora.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.appdora.domain.enumeration.Genero;

/**
 * A Utilizador.
 */
@Entity
@Table(name = "utilizador")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "utilizador")
public class Utilizador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "primeiro_nome", nullable = false)
    private String primeiroNome;

    @NotNull
    @Column(name = "ultimo_nome", nullable = false)
    private String ultimoNome;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "genero", nullable = false)
    private Genero genero;

    @NotNull
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "telefone", nullable = false)
    private String telefone;

    @NotNull
    @Column(name = "endereco_linha_1", nullable = false)
    private String enderecoLinha1;

    @Column(name = "endereco_linha_2")
    private String enderecoLinha2;

    @NotNull
    @Column(name = "cidade", nullable = false)
    private String cidade;

    @NotNull
    @Column(name = "pais", nullable = false)
    private String pais;

    @OneToOne(optional = false)    @NotNull
    @JoinColumn(unique = true)
    private User user;

    @ManyToMany    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "utilizador_portal",
               joinColumns = @JoinColumn(name = "utilizadors_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "portals_id", referencedColumnName = "id"))
    private Set<Portal> portals = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrimeiroNome() {
        return primeiroNome;
    }

    public Utilizador primeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
        return this;
    }

    public void setPrimeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }

    public String getUltimoNome() {
        return ultimoNome;
    }

    public Utilizador ultimoNome(String ultimoNome) {
        this.ultimoNome = ultimoNome;
        return this;
    }

    public void setUltimoNome(String ultimoNome) {
        this.ultimoNome = ultimoNome;
    }

    public Genero getGenero() {
        return genero;
    }

    public Utilizador genero(Genero genero) {
        this.genero = genero;
        return this;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getEmail() {
        return email;
    }

    public Utilizador email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public Utilizador telefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEnderecoLinha1() {
        return enderecoLinha1;
    }

    public Utilizador enderecoLinha1(String enderecoLinha1) {
        this.enderecoLinha1 = enderecoLinha1;
        return this;
    }

    public void setEnderecoLinha1(String enderecoLinha1) {
        this.enderecoLinha1 = enderecoLinha1;
    }

    public String getEnderecoLinha2() {
        return enderecoLinha2;
    }

    public Utilizador enderecoLinha2(String enderecoLinha2) {
        this.enderecoLinha2 = enderecoLinha2;
        return this;
    }

    public void setEnderecoLinha2(String enderecoLinha2) {
        this.enderecoLinha2 = enderecoLinha2;
    }

    public String getCidade() {
        return cidade;
    }

    public Utilizador cidade(String cidade) {
        this.cidade = cidade;
        return this;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getPais() {
        return pais;
    }

    public Utilizador pais(String pais) {
        this.pais = pais;
        return this;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public User getUser() {
        return user;
    }

    public Utilizador user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Portal> getPortals() {
        return portals;
    }

    public Utilizador portals(Set<Portal> portals) {
        this.portals = portals;
        return this;
    }

    public Utilizador addPortal(Portal portal) {
        this.portals.add(portal);
        portal.getUtilizdors().add(this);
        return this;
    }

    public Utilizador removePortal(Portal portal) {
        this.portals.remove(portal);
        portal.getUtilizdors().remove(this);
        return this;
    }

    public void setPortals(Set<Portal> portals) {
        this.portals = portals;
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
        Utilizador utilizador = (Utilizador) o;
        if (utilizador.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), utilizador.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Utilizador{" +
            "id=" + getId() +
            ", primeiroNome='" + getPrimeiroNome() + "'" +
            ", ultimoNome='" + getUltimoNome() + "'" +
            ", genero='" + getGenero() + "'" +
            ", email='" + getEmail() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", enderecoLinha1='" + getEnderecoLinha1() + "'" +
            ", enderecoLinha2='" + getEnderecoLinha2() + "'" +
            ", cidade='" + getCidade() + "'" +
            ", pais='" + getPais() + "'" +
            "}";
    }
}
