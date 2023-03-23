package mx.conacyt.crip.proyecto03.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Parametros.
 */
@Table("parametros")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Parametros implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @NotNull(message = "must not be null")
    @Column("fecha")
    private LocalDate fecha;

    @NotNull(message = "must not be null")
    @Column("id_instit")
    private Integer idInstit;

    @NotNull(message = "must not be null")
    @Column("nivel")
    private Integer nivel;

    @Column("usuario")
    private String usuario;

    @Column("fecha_mod")
    private LocalDate fechaMod;

    @Transient
    private RegistInstit registinstit;

    @Transient
    private TipoCFDI tipocfdi;

    @Transient
    @JsonIgnoreProperties(value = { "tiporecibo", "repgrafica", "parametros" }, allowSetters = true)
    private Set<Recibo> recibos = new HashSet<>();

    @Column("registinstit_id")
    private Long registinstitId;

    @Column("tipocfdi_id")
    private Long tipocfdiId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Parametros id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public Parametros fecha(LocalDate fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Integer getIdInstit() {
        return this.idInstit;
    }

    public Parametros idInstit(Integer idInstit) {
        this.setIdInstit(idInstit);
        return this;
    }

    public void setIdInstit(Integer idInstit) {
        this.idInstit = idInstit;
    }

    public Integer getNivel() {
        return this.nivel;
    }

    public Parametros nivel(Integer nivel) {
        this.setNivel(nivel);
        return this;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public Parametros usuario(String usuario) {
        this.setUsuario(usuario);
        return this;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public LocalDate getFechaMod() {
        return this.fechaMod;
    }

    public Parametros fechaMod(LocalDate fechaMod) {
        this.setFechaMod(fechaMod);
        return this;
    }

    public void setFechaMod(LocalDate fechaMod) {
        this.fechaMod = fechaMod;
    }

    public RegistInstit getRegistinstit() {
        return this.registinstit;
    }

    public void setRegistinstit(RegistInstit registInstit) {
        this.registinstit = registInstit;
        this.registinstitId = registInstit != null ? registInstit.getId() : null;
    }

    public Parametros registinstit(RegistInstit registInstit) {
        this.setRegistinstit(registInstit);
        return this;
    }

    public TipoCFDI getTipocfdi() {
        return this.tipocfdi;
    }

    public void setTipocfdi(TipoCFDI tipoCFDI) {
        this.tipocfdi = tipoCFDI;
        this.tipocfdiId = tipoCFDI != null ? tipoCFDI.getId() : null;
    }

    public Parametros tipocfdi(TipoCFDI tipoCFDI) {
        this.setTipocfdi(tipoCFDI);
        return this;
    }

    public Set<Recibo> getRecibos() {
        return this.recibos;
    }

    public void setRecibos(Set<Recibo> recibos) {
        if (this.recibos != null) {
            this.recibos.forEach(i -> i.setParametros(null));
        }
        if (recibos != null) {
            recibos.forEach(i -> i.setParametros(this));
        }
        this.recibos = recibos;
    }

    public Parametros recibos(Set<Recibo> recibos) {
        this.setRecibos(recibos);
        return this;
    }

    public Parametros addRecibo(Recibo recibo) {
        this.recibos.add(recibo);
        recibo.setParametros(this);
        return this;
    }

    public Parametros removeRecibo(Recibo recibo) {
        this.recibos.remove(recibo);
        recibo.setParametros(null);
        return this;
    }

    public Long getRegistinstitId() {
        return this.registinstitId;
    }

    public void setRegistinstitId(Long registInstit) {
        this.registinstitId = registInstit;
    }

    public Long getTipocfdiId() {
        return this.tipocfdiId;
    }

    public void setTipocfdiId(Long tipoCFDI) {
        this.tipocfdiId = tipoCFDI;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Parametros)) {
            return false;
        }
        return id != null && id.equals(((Parametros) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Parametros{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", idInstit=" + getIdInstit() +
            ", nivel=" + getNivel() +
            ", usuario='" + getUsuario() + "'" +
            ", fechaMod='" + getFechaMod() + "'" +
            "}";
    }
}
