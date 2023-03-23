package mx.conacyt.crip.proyecto03.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.*;
import mx.conacyt.crip.proyecto03.domain.enumeration.Estatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A RegistInstit.
 */
@Table("regist_instit")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RegistInstit implements Serializable {

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

    @Column("estatus")
    private Estatus estatus;

    @NotNull(message = "must not be null")
    @Column("nivel")
    private Integer nivel;

    @NotNull(message = "must not be null")
    @Column("razon_social")
    private String razonSocial;

    @NotNull(message = "must not be null")
    @Column("r_fc")
    private String rFC;

    @Column("pais")
    private String pais;

    @Column("municipio")
    private String municipio;

    @Column("localidad")
    private String localidad;

    @Column("codigo_postal")
    private Integer codigoPostal;

    @Column("colonia")
    private String colonia;

    @Column("calle")
    private String calle;

    @Column("num_ext")
    private Integer numExt;

    @Column("num_int")
    private Integer numInt;

    @Column("logo")
    private byte[] logo;

    @Column("logo_content_type")
    private String logoContentType;

    @Column("certificado")
    private byte[] certificado;

    @Column("certificado_content_type")
    private String certificadoContentType;

    @Column("llave_priv")
    private byte[] llavePriv;

    @Column("llave_priv_content_type")
    private String llavePrivContentType;

    @Column("contrasena")
    private String contrasena;

    @Column("fecha_exp")
    private LocalDate fechaExp;

    @Column("dias")
    private Integer dias;

    @Column("correo")
    private String correo;

    @Column("usuario")
    private String usuario;

    @Column("fecha_mod")
    private LocalDate fechaMod;

    @Transient
    private RegimenFis regimenfis;

    @Transient
    private Estado estado;

    @Transient
    private RegistInstit registinstit;

    @Transient
    private RegistInstit registinstiti;

    @Column("regimenfis_id")
    private Long regimenfisId;

    @Column("estado_id")
    private Long estadoId;

    @Column("registinstit_id")
    private Long registinstitId;

    @Column("registinstiti_id")
    private Long registinstitiId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RegistInstit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public RegistInstit fecha(LocalDate fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Integer getIdInstit() {
        return this.idInstit;
    }

    public RegistInstit idInstit(Integer idInstit) {
        this.setIdInstit(idInstit);
        return this;
    }

    public void setIdInstit(Integer idInstit) {
        this.idInstit = idInstit;
    }

    public Estatus getEstatus() {
        return this.estatus;
    }

    public RegistInstit estatus(Estatus estatus) {
        this.setEstatus(estatus);
        return this;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

    public Integer getNivel() {
        return this.nivel;
    }

    public RegistInstit nivel(Integer nivel) {
        this.setNivel(nivel);
        return this;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public String getRazonSocial() {
        return this.razonSocial;
    }

    public RegistInstit razonSocial(String razonSocial) {
        this.setRazonSocial(razonSocial);
        return this;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getrFC() {
        return this.rFC;
    }

    public RegistInstit rFC(String rFC) {
        this.setrFC(rFC);
        return this;
    }

    public void setrFC(String rFC) {
        this.rFC = rFC;
    }

    public String getPais() {
        return this.pais;
    }

    public RegistInstit pais(String pais) {
        this.setPais(pais);
        return this;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getMunicipio() {
        return this.municipio;
    }

    public RegistInstit municipio(String municipio) {
        this.setMunicipio(municipio);
        return this;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getLocalidad() {
        return this.localidad;
    }

    public RegistInstit localidad(String localidad) {
        this.setLocalidad(localidad);
        return this;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public Integer getCodigoPostal() {
        return this.codigoPostal;
    }

    public RegistInstit codigoPostal(Integer codigoPostal) {
        this.setCodigoPostal(codigoPostal);
        return this;
    }

    public void setCodigoPostal(Integer codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getColonia() {
        return this.colonia;
    }

    public RegistInstit colonia(String colonia) {
        this.setColonia(colonia);
        return this;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCalle() {
        return this.calle;
    }

    public RegistInstit calle(String calle) {
        this.setCalle(calle);
        return this;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Integer getNumExt() {
        return this.numExt;
    }

    public RegistInstit numExt(Integer numExt) {
        this.setNumExt(numExt);
        return this;
    }

    public void setNumExt(Integer numExt) {
        this.numExt = numExt;
    }

    public Integer getNumInt() {
        return this.numInt;
    }

    public RegistInstit numInt(Integer numInt) {
        this.setNumInt(numInt);
        return this;
    }

    public void setNumInt(Integer numInt) {
        this.numInt = numInt;
    }

    public byte[] getLogo() {
        return this.logo;
    }

    public RegistInstit logo(byte[] logo) {
        this.setLogo(logo);
        return this;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return this.logoContentType;
    }

    public RegistInstit logoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
        return this;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public byte[] getCertificado() {
        return this.certificado;
    }

    public RegistInstit certificado(byte[] certificado) {
        this.setCertificado(certificado);
        return this;
    }

    public void setCertificado(byte[] certificado) {
        this.certificado = certificado;
    }

    public String getCertificadoContentType() {
        return this.certificadoContentType;
    }

    public RegistInstit certificadoContentType(String certificadoContentType) {
        this.certificadoContentType = certificadoContentType;
        return this;
    }

    public void setCertificadoContentType(String certificadoContentType) {
        this.certificadoContentType = certificadoContentType;
    }

    public byte[] getLlavePriv() {
        return this.llavePriv;
    }

    public RegistInstit llavePriv(byte[] llavePriv) {
        this.setLlavePriv(llavePriv);
        return this;
    }

    public void setLlavePriv(byte[] llavePriv) {
        this.llavePriv = llavePriv;
    }

    public String getLlavePrivContentType() {
        return this.llavePrivContentType;
    }

    public RegistInstit llavePrivContentType(String llavePrivContentType) {
        this.llavePrivContentType = llavePrivContentType;
        return this;
    }

    public void setLlavePrivContentType(String llavePrivContentType) {
        this.llavePrivContentType = llavePrivContentType;
    }

    public String getContrasena() {
        return this.contrasena;
    }

    public RegistInstit contrasena(String contrasena) {
        this.setContrasena(contrasena);
        return this;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public LocalDate getFechaExp() {
        return this.fechaExp;
    }

    public RegistInstit fechaExp(LocalDate fechaExp) {
        this.setFechaExp(fechaExp);
        return this;
    }

    public void setFechaExp(LocalDate fechaExp) {
        this.fechaExp = fechaExp;
    }

    public Integer getDias() {
        return this.dias;
    }

    public RegistInstit dias(Integer dias) {
        this.setDias(dias);
        return this;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }

    public String getCorreo() {
        return this.correo;
    }

    public RegistInstit correo(String correo) {
        this.setCorreo(correo);
        return this;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public RegistInstit usuario(String usuario) {
        this.setUsuario(usuario);
        return this;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public LocalDate getFechaMod() {
        return this.fechaMod;
    }

    public RegistInstit fechaMod(LocalDate fechaMod) {
        this.setFechaMod(fechaMod);
        return this;
    }

    public void setFechaMod(LocalDate fechaMod) {
        this.fechaMod = fechaMod;
    }

    public RegimenFis getRegimenfis() {
        return this.regimenfis;
    }

    public void setRegimenfis(RegimenFis regimenFis) {
        this.regimenfis = regimenFis;
        this.regimenfisId = regimenFis != null ? regimenFis.getId() : null;
    }

    public RegistInstit regimenfis(RegimenFis regimenFis) {
        this.setRegimenfis(regimenFis);
        return this;
    }

    public Estado getEstado() {
        return this.estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
        this.estadoId = estado != null ? estado.getId() : null;
    }

    public RegistInstit estado(Estado estado) {
        this.setEstado(estado);
        return this;
    }

    public RegistInstit getRegistinstit() {
        return this.registinstit;
    }

    public void setRegistinstit(RegistInstit registInstit) {
        this.registinstit = registInstit;
        this.registinstitId = registInstit != null ? registInstit.getId() : null;
    }

    public RegistInstit registinstit(RegistInstit registInstit) {
        this.setRegistinstit(registInstit);
        return this;
    }

    public RegistInstit getRegistinstiti() {
        return this.registinstiti;
    }

    public void setRegistinstiti(RegistInstit registInstit) {
        this.registinstiti = registInstit;
        this.registinstitiId = registInstit != null ? registInstit.getId() : null;
    }

    public RegistInstit registinstiti(RegistInstit registInstit) {
        this.setRegistinstiti(registInstit);
        return this;
    }

    public Long getRegimenfisId() {
        return this.regimenfisId;
    }

    public void setRegimenfisId(Long regimenFis) {
        this.regimenfisId = regimenFis;
    }

    public Long getEstadoId() {
        return this.estadoId;
    }

    public void setEstadoId(Long estado) {
        this.estadoId = estado;
    }

    public Long getRegistinstitId() {
        return this.registinstitId;
    }

    public void setRegistinstitId(Long registInstit) {
        this.registinstitId = registInstit;
    }

    public Long getRegistinstitiId() {
        return this.registinstitiId;
    }

    public void setRegistinstitiId(Long registInstit) {
        this.registinstitiId = registInstit;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RegistInstit)) {
            return false;
        }
        return id != null && id.equals(((RegistInstit) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RegistInstit{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", idInstit=" + getIdInstit() +
            ", estatus='" + getEstatus() + "'" +
            ", nivel=" + getNivel() +
            ", razonSocial='" + getRazonSocial() + "'" +
            ", rFC='" + getrFC() + "'" +
            ", pais='" + getPais() + "'" +
            ", municipio='" + getMunicipio() + "'" +
            ", localidad='" + getLocalidad() + "'" +
            ", codigoPostal=" + getCodigoPostal() +
            ", colonia='" + getColonia() + "'" +
            ", calle='" + getCalle() + "'" +
            ", numExt=" + getNumExt() +
            ", numInt=" + getNumInt() +
            ", logo='" + getLogo() + "'" +
            ", logoContentType='" + getLogoContentType() + "'" +
            ", certificado='" + getCertificado() + "'" +
            ", certificadoContentType='" + getCertificadoContentType() + "'" +
            ", llavePriv='" + getLlavePriv() + "'" +
            ", llavePrivContentType='" + getLlavePrivContentType() + "'" +
            ", contrasena='" + getContrasena() + "'" +
            ", fechaExp='" + getFechaExp() + "'" +
            ", dias=" + getDias() +
            ", correo='" + getCorreo() + "'" +
            ", usuario='" + getUsuario() + "'" +
            ", fechaMod='" + getFechaMod() + "'" +
            "}";
    }
}
