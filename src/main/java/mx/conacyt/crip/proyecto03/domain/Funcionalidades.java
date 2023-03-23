package mx.conacyt.crip.proyecto03.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.*;
import mx.conacyt.crip.proyecto03.domain.enumeration.Funcionalidad;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Funcionalidades.
 */
@Table("funcionalidades")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Funcionalidades implements Serializable {

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

    @Column("funcionalidad")
    private Funcionalidad funcionalidad;

    @Column("alta")
    private String alta;

    @Column("editar")
    private String editar;

    @Column("activar_inact")
    private String activarInact;

    @Column("consulta")
    private String consulta;

    @Column("usuario")
    private String usuario;

    @Column("fecha_mod")
    private LocalDate fechaMod;

    @Transient
    @JsonIgnoreProperties(value = { "funcionalidades" }, allowSetters = true)
    private Set<Perfiles> perfils = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Funcionalidades id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public Funcionalidades fecha(LocalDate fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getPerfil() {
        return this.perfil;
    }

    public Funcionalidades perfil(String perfil) {
        this.setPerfil(perfil);
        return this;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public Funcionalidad getFuncionalidad() {
        return this.funcionalidad;
    }

    public Funcionalidades funcionalidad(Funcionalidad funcionalidad) {
        this.setFuncionalidad(funcionalidad);
        return this;
    }

    public void setFuncionalidad(Funcionalidad funcionalidad) {
        this.funcionalidad = funcionalidad;
    }

    public String getAlta() {
        return this.alta;
    }

    public Funcionalidades alta(String alta) {
        this.setAlta(alta);
        return this;
    }

    public void setAlta(String alta) {
        this.alta = alta;
    }

    public String getEditar() {
        return this.editar;
    }

    public Funcionalidades editar(String editar) {
        this.setEditar(editar);
        return this;
    }

    public void setEditar(String editar) {
        this.editar = editar;
    }

    public String getActivarInact() {
        return this.activarInact;
    }

    public Funcionalidades activarInact(String activarInact) {
        this.setActivarInact(activarInact);
        return this;
    }

    public void setActivarInact(String activarInact) {
        this.activarInact = activarInact;
    }

    public String getConsulta() {
        return this.consulta;
    }

    public Funcionalidades consulta(String consulta) {
        this.setConsulta(consulta);
        return this;
    }

    public void setConsulta(String consulta) {
        this.consulta = consulta;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public Funcionalidades usuario(String usuario) {
        this.setUsuario(usuario);
        return this;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public LocalDate getFechaMod() {
        return this.fechaMod;
    }

    public Funcionalidades fechaMod(LocalDate fechaMod) {
        this.setFechaMod(fechaMod);
        return this;
    }

    public void setFechaMod(LocalDate fechaMod) {
        this.fechaMod = fechaMod;
    }

    public Set<Perfiles> getPerfils() {
        return this.perfils;
    }

    public void setPerfils(Set<Perfiles> perfiles) {
        if (this.perfils != null) {
            this.perfils.forEach(i -> i.setFuncionalidades(null));
        }
        if (perfiles != null) {
            perfiles.forEach(i -> i.setFuncionalidades(this));
        }
        this.perfils = perfiles;
    }

    public Funcionalidades perfils(Set<Perfiles> perfiles) {
        this.setPerfils(perfiles);
        return this;
    }

    public Funcionalidades addPerfil(Perfiles perfiles) {
        this.perfils.add(perfiles);
        perfiles.setFuncionalidades(this);
        return this;
    }

    public Funcionalidades removePerfil(Perfiles perfiles) {
        this.perfils.remove(perfiles);
        perfiles.setFuncionalidades(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Funcionalidades)) {
            return false;
        }
        return id != null && id.equals(((Funcionalidades) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Funcionalidades{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", perfil='" + getPerfil() + "'" +
            ", funcionalidad='" + getFuncionalidad() + "'" +
            ", alta='" + getAlta() + "'" +
            ", editar='" + getEditar() + "'" +
            ", activarInact='" + getActivarInact() + "'" +
            ", consulta='" + getConsulta() + "'" +
            ", usuario='" + getUsuario() + "'" +
            ", fechaMod='" + getFechaMod() + "'" +
            "}";
    }
}
