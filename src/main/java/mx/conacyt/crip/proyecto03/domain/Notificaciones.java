package mx.conacyt.crip.proyecto03.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.*;
import mx.conacyt.crip.proyecto03.domain.enumeration.TipoNot;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Notificaciones.
 */
@Table("notificaciones")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Notificaciones implements Serializable {

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

    @Column("tipo_not")
    private TipoNot tipoNot;

    @Column("clave")
    private String clave;

    @Column("asunto")
    private String asunto;

    @Column("texto")
    private Long texto;

    @Column("pie_pagina")
    private String piePagina;

    @Column("usuario")
    private String usuario;

    @Column("fecha_mod")
    private LocalDate fechaMod;

    @Transient
    private RegistInstit registinstit;

    @Transient
    private TipoCFDI tipocfdi;

    @Transient
    private TipoRecibo tiporecibo;

    @Column("registinstit_id")
    private Long registinstitId;

    @Column("tipocfdi_id")
    private Long tipocfdiId;

    @Column("tiporecibo_id")
    private Long tiporeciboId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Notificaciones id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public Notificaciones fecha(LocalDate fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Integer getIdInstit() {
        return this.idInstit;
    }

    public Notificaciones idInstit(Integer idInstit) {
        this.setIdInstit(idInstit);
        return this;
    }

    public void setIdInstit(Integer idInstit) {
        this.idInstit = idInstit;
    }

    public Integer getNivel() {
        return this.nivel;
    }

    public Notificaciones nivel(Integer nivel) {
        this.setNivel(nivel);
        return this;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public TipoNot getTipoNot() {
        return this.tipoNot;
    }

    public Notificaciones tipoNot(TipoNot tipoNot) {
        this.setTipoNot(tipoNot);
        return this;
    }

    public void setTipoNot(TipoNot tipoNot) {
        this.tipoNot = tipoNot;
    }

    public String getClave() {
        return this.clave;
    }

    public Notificaciones clave(String clave) {
        this.setClave(clave);
        return this;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getAsunto() {
        return this.asunto;
    }

    public Notificaciones asunto(String asunto) {
        this.setAsunto(asunto);
        return this;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public Long getTexto() {
        return this.texto;
    }

    public Notificaciones texto(Long texto) {
        this.setTexto(texto);
        return this;
    }

    public void setTexto(Long texto) {
        this.texto = texto;
    }

    public String getPiePagina() {
        return this.piePagina;
    }

    public Notificaciones piePagina(String piePagina) {
        this.setPiePagina(piePagina);
        return this;
    }

    public void setPiePagina(String piePagina) {
        this.piePagina = piePagina;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public Notificaciones usuario(String usuario) {
        this.setUsuario(usuario);
        return this;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public LocalDate getFechaMod() {
        return this.fechaMod;
    }

    public Notificaciones fechaMod(LocalDate fechaMod) {
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

    public Notificaciones registinstit(RegistInstit registInstit) {
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

    public Notificaciones tipocfdi(TipoCFDI tipoCFDI) {
        this.setTipocfdi(tipoCFDI);
        return this;
    }

    public TipoRecibo getTiporecibo() {
        return this.tiporecibo;
    }

    public void setTiporecibo(TipoRecibo tipoRecibo) {
        this.tiporecibo = tipoRecibo;
        this.tiporeciboId = tipoRecibo != null ? tipoRecibo.getId() : null;
    }

    public Notificaciones tiporecibo(TipoRecibo tipoRecibo) {
        this.setTiporecibo(tipoRecibo);
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

    public Long getTiporeciboId() {
        return this.tiporeciboId;
    }

    public void setTiporeciboId(Long tipoRecibo) {
        this.tiporeciboId = tipoRecibo;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Notificaciones)) {
            return false;
        }
        return id != null && id.equals(((Notificaciones) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Notificaciones{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", idInstit=" + getIdInstit() +
            ", nivel=" + getNivel() +
            ", tipoNot='" + getTipoNot() + "'" +
            ", clave='" + getClave() + "'" +
            ", asunto='" + getAsunto() + "'" +
            ", texto=" + getTexto() +
            ", piePagina='" + getPiePagina() + "'" +
            ", usuario='" + getUsuario() + "'" +
            ", fechaMod='" + getFechaMod() + "'" +
            "}";
    }
}
