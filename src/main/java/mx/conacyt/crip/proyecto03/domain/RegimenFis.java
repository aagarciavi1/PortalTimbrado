package mx.conacyt.crip.proyecto03.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import mx.conacyt.crip.proyecto03.domain.enumeration.Estatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Catálogos
 */
@Schema(description = "Catálogos")
@Table("regimen_fis")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RegimenFis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("fecha")
    private LocalDate fecha;

    @Column("regimen_f")
    private String regimenF;

    @Column("estatus")
    private Estatus estatus;

    @Column("usuario")
    private String usuario;

    @Column("fecha_mod")
    private String fechaMod;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RegimenFis id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public RegimenFis fecha(LocalDate fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getRegimenF() {
        return this.regimenF;
    }

    public RegimenFis regimenF(String regimenF) {
        this.setRegimenF(regimenF);
        return this;
    }

    public void setRegimenF(String regimenF) {
        this.regimenF = regimenF;
    }

    public Estatus getEstatus() {
        return this.estatus;
    }

    public RegimenFis estatus(Estatus estatus) {
        this.setEstatus(estatus);
        return this;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public RegimenFis usuario(String usuario) {
        this.setUsuario(usuario);
        return this;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFechaMod() {
        return this.fechaMod;
    }

    public RegimenFis fechaMod(String fechaMod) {
        this.setFechaMod(fechaMod);
        return this;
    }

    public void setFechaMod(String fechaMod) {
        this.fechaMod = fechaMod;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RegimenFis)) {
            return false;
        }
        return id != null && id.equals(((RegimenFis) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RegimenFis{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", regimenF='" + getRegimenF() + "'" +
            ", estatus='" + getEstatus() + "'" +
            ", usuario='" + getUsuario() + "'" +
            ", fechaMod='" + getFechaMod() + "'" +
            "}";
    }
}
