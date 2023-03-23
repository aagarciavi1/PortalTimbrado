package mx.conacyt.crip.proyecto03.domain;

import java.io.Serializable;
import java.time.LocalDate;
import mx.conacyt.crip.proyecto03.domain.enumeration.Estatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A EstatusCFDI.
 */
@Table("estatus_cfdi")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EstatusCFDI implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("fecha")
    private LocalDate fecha;

    @Column("desc_est_cfdi")
    private String descEstCFDI;

    @Column("estatus")
    private Estatus estatus;

    @Column("usuario")
    private String usuario;

    @Column("fecha_mod")
    private LocalDate fechaMod;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EstatusCFDI id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public EstatusCFDI fecha(LocalDate fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDescEstCFDI() {
        return this.descEstCFDI;
    }

    public EstatusCFDI descEstCFDI(String descEstCFDI) {
        this.setDescEstCFDI(descEstCFDI);
        return this;
    }

    public void setDescEstCFDI(String descEstCFDI) {
        this.descEstCFDI = descEstCFDI;
    }

    public Estatus getEstatus() {
        return this.estatus;
    }

    public EstatusCFDI estatus(Estatus estatus) {
        this.setEstatus(estatus);
        return this;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public EstatusCFDI usuario(String usuario) {
        this.setUsuario(usuario);
        return this;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public LocalDate getFechaMod() {
        return this.fechaMod;
    }

    public EstatusCFDI fechaMod(LocalDate fechaMod) {
        this.setFechaMod(fechaMod);
        return this;
    }

    public void setFechaMod(LocalDate fechaMod) {
        this.fechaMod = fechaMod;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EstatusCFDI)) {
            return false;
        }
        return id != null && id.equals(((EstatusCFDI) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EstatusCFDI{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", descEstCFDI='" + getDescEstCFDI() + "'" +
            ", estatus='" + getEstatus() + "'" +
            ", usuario='" + getUsuario() + "'" +
            ", fechaMod='" + getFechaMod() + "'" +
            "}";
    }
}
