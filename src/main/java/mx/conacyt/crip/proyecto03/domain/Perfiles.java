package mx.conacyt.crip.proyecto03.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Perfiles.
 */
@Table("perfiles")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Perfiles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @NotNull(message = "must not be null")
    @Column("fecha")
    private LocalDate fecha;

    @NotNull(message = "must not be null")
    @Column("perfil")
    private String perfil;

    @Column("usuario")
    private String usuario;

    @Column("fecha_mod")
    private LocalDate fechaMod;

    @Transient
    @JsonIgnoreProperties(value = { "perfils" }, allowSetters = true)
    private Funcionalidades funcionalidades;

    @Column("funcionalidades_id")
    private Long funcionalidadesId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Perfiles id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public Perfiles fecha(LocalDate fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getPerfil() {
        return this.perfil;
    }

    public Perfiles perfil(String perfil) {
        this.setPerfil(perfil);
        return this;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public Perfiles usuario(String usuario) {
        this.setUsuario(usuario);
        return this;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public LocalDate getFechaMod() {
        return this.fechaMod;
    }

    public Perfiles fechaMod(LocalDate fechaMod) {
        this.setFechaMod(fechaMod);
        return this;
    }

    public void setFechaMod(LocalDate fechaMod) {
        this.fechaMod = fechaMod;
    }

    public Funcionalidades getFuncionalidades() {
        return this.funcionalidades;
    }

    public void setFuncionalidades(Funcionalidades funcionalidades) {
        this.funcionalidades = funcionalidades;
        this.funcionalidadesId = funcionalidades != null ? funcionalidades.getId() : null;
    }

    public Perfiles funcionalidades(Funcionalidades funcionalidades) {
        this.setFuncionalidades(funcionalidades);
        return this;
    }

    public Long getFuncionalidadesId() {
        return this.funcionalidadesId;
    }

    public void setFuncionalidadesId(Long funcionalidades) {
        this.funcionalidadesId = funcionalidades;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Perfiles)) {
            return false;
        }
        return id != null && id.equals(((Perfiles) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Perfiles{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", perfil='" + getPerfil() + "'" +
            ", usuario='" + getUsuario() + "'" +
            ", fechaMod='" + getFechaMod() + "'" +
            "}";
    }
}
