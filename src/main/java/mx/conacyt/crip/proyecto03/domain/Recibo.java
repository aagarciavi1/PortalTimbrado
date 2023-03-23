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
 * A Recibo.
 */
@Table("recibo")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Recibo implements Serializable {

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

    @NotNull(message = "must not be null")
    @Column("tipo_cfdi")
    private String tipoCFDI;

    @Column("clave")
    private String clave;

    @Column("envio_con_cfdi")
    private String envioConCFDI;

    @Column("envio_sin_cfdi")
    private String envioSinCFDI;

    @Column("aplica_leyenda")
    private String aplicaLeyenda;

    @Column("leyenda")
    private Long leyenda;

    @Column("usuario")
    private String usuario;

    @Column("fecha_mod")
    private LocalDate fechaMod;

    @Transient
    private TipoRecibo tiporecibo;

    @Transient
    private RepGrafica repgrafica;

    @Transient
    @JsonIgnoreProperties(value = { "registinstit", "tipocfdi", "recibos" }, allowSetters = true)
    private Parametros parametros;

    @Column("tiporecibo_id")
    private Long tiporeciboId;

    @Column("repgrafica_id")
    private Long repgraficaId;

    @Column("parametros_id")
    private Long parametrosId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Recibo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public Recibo fecha(LocalDate fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Integer getIdInstit() {
        return this.idInstit;
    }

    public Recibo idInstit(Integer idInstit) {
        this.setIdInstit(idInstit);
        return this;
    }

    public void setIdInstit(Integer idInstit) {
        this.idInstit = idInstit;
    }

    public Integer getNivel() {
        return this.nivel;
    }

    public Recibo nivel(Integer nivel) {
        this.setNivel(nivel);
        return this;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public String getTipoCFDI() {
        return this.tipoCFDI;
    }

    public Recibo tipoCFDI(String tipoCFDI) {
        this.setTipoCFDI(tipoCFDI);
        return this;
    }

    public void setTipoCFDI(String tipoCFDI) {
        this.tipoCFDI = tipoCFDI;
    }

    public String getClave() {
        return this.clave;
    }

    public Recibo clave(String clave) {
        this.setClave(clave);
        return this;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getEnvioConCFDI() {
        return this.envioConCFDI;
    }

    public Recibo envioConCFDI(String envioConCFDI) {
        this.setEnvioConCFDI(envioConCFDI);
        return this;
    }

    public void setEnvioConCFDI(String envioConCFDI) {
        this.envioConCFDI = envioConCFDI;
    }

    public String getEnvioSinCFDI() {
        return this.envioSinCFDI;
    }

    public Recibo envioSinCFDI(String envioSinCFDI) {
        this.setEnvioSinCFDI(envioSinCFDI);
        return this;
    }

    public void setEnvioSinCFDI(String envioSinCFDI) {
        this.envioSinCFDI = envioSinCFDI;
    }

    public String getAplicaLeyenda() {
        return this.aplicaLeyenda;
    }

    public Recibo aplicaLeyenda(String aplicaLeyenda) {
        this.setAplicaLeyenda(aplicaLeyenda);
        return this;
    }

    public void setAplicaLeyenda(String aplicaLeyenda) {
        this.aplicaLeyenda = aplicaLeyenda;
    }

    public Long getLeyenda() {
        return this.leyenda;
    }

    public Recibo leyenda(Long leyenda) {
        this.setLeyenda(leyenda);
        return this;
    }

    public void setLeyenda(Long leyenda) {
        this.leyenda = leyenda;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public Recibo usuario(String usuario) {
        this.setUsuario(usuario);
        return this;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public LocalDate getFechaMod() {
        return this.fechaMod;
    }

    public Recibo fechaMod(LocalDate fechaMod) {
        this.setFechaMod(fechaMod);
        return this;
    }

    public void setFechaMod(LocalDate fechaMod) {
        this.fechaMod = fechaMod;
    }

    public TipoRecibo getTiporecibo() {
        return this.tiporecibo;
    }

    public void setTiporecibo(TipoRecibo tipoRecibo) {
        this.tiporecibo = tipoRecibo;
        this.tiporeciboId = tipoRecibo != null ? tipoRecibo.getId() : null;
    }

    public Recibo tiporecibo(TipoRecibo tipoRecibo) {
        this.setTiporecibo(tipoRecibo);
        return this;
    }

    public RepGrafica getRepgrafica() {
        return this.repgrafica;
    }

    public void setRepgrafica(RepGrafica repGrafica) {
        this.repgrafica = repGrafica;
        this.repgraficaId = repGrafica != null ? repGrafica.getId() : null;
    }

    public Recibo repgrafica(RepGrafica repGrafica) {
        this.setRepgrafica(repGrafica);
        return this;
    }

    public Parametros getParametros() {
        return this.parametros;
    }

    public void setParametros(Parametros parametros) {
        this.parametros = parametros;
        this.parametrosId = parametros != null ? parametros.getId() : null;
    }

    public Recibo parametros(Parametros parametros) {
        this.setParametros(parametros);
        return this;
    }

    public Long getTiporeciboId() {
        return this.tiporeciboId;
    }

    public void setTiporeciboId(Long tipoRecibo) {
        this.tiporeciboId = tipoRecibo;
    }

    public Long getRepgraficaId() {
        return this.repgraficaId;
    }

    public void setRepgraficaId(Long repGrafica) {
        this.repgraficaId = repGrafica;
    }

    public Long getParametrosId() {
        return this.parametrosId;
    }

    public void setParametrosId(Long parametros) {
        this.parametrosId = parametros;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Recibo)) {
            return false;
        }
        return id != null && id.equals(((Recibo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Recibo{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", idInstit=" + getIdInstit() +
            ", nivel=" + getNivel() +
            ", tipoCFDI='" + getTipoCFDI() + "'" +
            ", clave='" + getClave() + "'" +
            ", envioConCFDI='" + getEnvioConCFDI() + "'" +
            ", envioSinCFDI='" + getEnvioSinCFDI() + "'" +
            ", aplicaLeyenda='" + getAplicaLeyenda() + "'" +
            ", leyenda=" + getLeyenda() +
            ", usuario='" + getUsuario() + "'" +
            ", fechaMod='" + getFechaMod() + "'" +
            "}";
    }
}
