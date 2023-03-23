package mx.conacyt.crip.proyecto03.domain;

import java.io.Serializable;
import java.time.LocalDate;
import mx.conacyt.crip.proyecto03.domain.enumeration.Estatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A TipoCFDI.
 */
@Table("tipo_cfdi")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TipoCFDI implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("fecha")
    private LocalDate fecha;

    @Column("tipo_cfdi")
    private String tipoCFDI;

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

    public TipoCFDI id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public TipoCFDI fecha(LocalDate fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getTipoCFDI() {
        return this.tipoCFDI;
    }

    public TipoCFDI tipoCFDI(String tipoCFDI) {
        this.setTipoCFDI(tipoCFDI);
        return this;
    }

    public void setTipoCFDI(String tipoCFDI) {
        this.tipoCFDI = tipoCFDI;
    }

    public Estatus getEstatus() {
        return this.estatus;
    }

    public TipoCFDI estatus(Estatus estatus) {
        this.setEstatus(estatus);
        return this;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public TipoCFDI usuario(String usuario) {
        this.setUsuario(usuario);
        return this;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public LocalDate getFechaMod() {
        return this.fechaMod;
    }

    public TipoCFDI fechaMod(LocalDate fechaMod) {
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
        if (!(o instanceof TipoCFDI)) {
            return false;
        }
        return id != null && id.equals(((TipoCFDI) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipoCFDI{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", tipoCFDI='" + getTipoCFDI() + "'" +
            ", estatus='" + getEstatus() + "'" +
            ", usuario='" + getUsuario() + "'" +
            ", fechaMod='" + getFechaMod() + "'" +
            "}";
    }
}
