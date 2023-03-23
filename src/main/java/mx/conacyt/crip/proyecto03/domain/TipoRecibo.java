package mx.conacyt.crip.proyecto03.domain;

import java.io.Serializable;
import java.time.LocalDate;
import mx.conacyt.crip.proyecto03.domain.enumeration.Estatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A TipoRecibo.
 */
@Table("tipo_recibo")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TipoRecibo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("fecha")
    private LocalDate fecha;

    @Column("tipo_recibo")
    private String tipoRecibo;

    @Column("clave")
    private String clave;

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

    public TipoRecibo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public TipoRecibo fecha(LocalDate fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getTipoRecibo() {
        return this.tipoRecibo;
    }

    public TipoRecibo tipoRecibo(String tipoRecibo) {
        this.setTipoRecibo(tipoRecibo);
        return this;
    }

    public void setTipoRecibo(String tipoRecibo) {
        this.tipoRecibo = tipoRecibo;
    }

    public String getClave() {
        return this.clave;
    }

    public TipoRecibo clave(String clave) {
        this.setClave(clave);
        return this;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Estatus getEstatus() {
        return this.estatus;
    }

    public TipoRecibo estatus(Estatus estatus) {
        this.setEstatus(estatus);
        return this;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public TipoRecibo usuario(String usuario) {
        this.setUsuario(usuario);
        return this;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public LocalDate getFechaMod() {
        return this.fechaMod;
    }

    public TipoRecibo fechaMod(LocalDate fechaMod) {
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
        if (!(o instanceof TipoRecibo)) {
            return false;
        }
        return id != null && id.equals(((TipoRecibo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipoRecibo{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", tipoRecibo='" + getTipoRecibo() + "'" +
            ", clave='" + getClave() + "'" +
            ", estatus='" + getEstatus() + "'" +
            ", usuario='" + getUsuario() + "'" +
            ", fechaMod='" + getFechaMod() + "'" +
            "}";
    }
}
