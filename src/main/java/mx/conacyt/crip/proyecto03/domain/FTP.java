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
 * A FTP.
 */
@Table("ftp")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FTP implements Serializable {

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

    @Column("clave")
    private String clave;

    @Column("carpeta_ftp")
    private String carpetaFTP;

    @Column("subcarpeta_ftp")
    private String subcarpetaFTP;

    @Column("descripcion_ftp")
    private String descripcionFTP;

    @Column("ip_ftp")
    private String ipFTP;

    @Column("puerto")
    private Integer puerto;

    @Column("usuario_ftp")
    private String usuarioFTP;

    @Column("contrasena")
    private String contrasena;

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

    public FTP id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public FTP fecha(LocalDate fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Integer getIdInstit() {
        return this.idInstit;
    }

    public FTP idInstit(Integer idInstit) {
        this.setIdInstit(idInstit);
        return this;
    }

    public void setIdInstit(Integer idInstit) {
        this.idInstit = idInstit;
    }

    public Integer getNivel() {
        return this.nivel;
    }

    public FTP nivel(Integer nivel) {
        this.setNivel(nivel);
        return this;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public String getClave() {
        return this.clave;
    }

    public FTP clave(String clave) {
        this.setClave(clave);
        return this;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCarpetaFTP() {
        return this.carpetaFTP;
    }

    public FTP carpetaFTP(String carpetaFTP) {
        this.setCarpetaFTP(carpetaFTP);
        return this;
    }

    public void setCarpetaFTP(String carpetaFTP) {
        this.carpetaFTP = carpetaFTP;
    }

    public String getSubcarpetaFTP() {
        return this.subcarpetaFTP;
    }

    public FTP subcarpetaFTP(String subcarpetaFTP) {
        this.setSubcarpetaFTP(subcarpetaFTP);
        return this;
    }

    public void setSubcarpetaFTP(String subcarpetaFTP) {
        this.subcarpetaFTP = subcarpetaFTP;
    }

    public String getDescripcionFTP() {
        return this.descripcionFTP;
    }

    public FTP descripcionFTP(String descripcionFTP) {
        this.setDescripcionFTP(descripcionFTP);
        return this;
    }

    public void setDescripcionFTP(String descripcionFTP) {
        this.descripcionFTP = descripcionFTP;
    }

    public String getIpFTP() {
        return this.ipFTP;
    }

    public FTP ipFTP(String ipFTP) {
        this.setIpFTP(ipFTP);
        return this;
    }

    public void setIpFTP(String ipFTP) {
        this.ipFTP = ipFTP;
    }

    public Integer getPuerto() {
        return this.puerto;
    }

    public FTP puerto(Integer puerto) {
        this.setPuerto(puerto);
        return this;
    }

    public void setPuerto(Integer puerto) {
        this.puerto = puerto;
    }

    public String getUsuarioFTP() {
        return this.usuarioFTP;
    }

    public FTP usuarioFTP(String usuarioFTP) {
        this.setUsuarioFTP(usuarioFTP);
        return this;
    }

    public void setUsuarioFTP(String usuarioFTP) {
        this.usuarioFTP = usuarioFTP;
    }

    public String getContrasena() {
        return this.contrasena;
    }

    public FTP contrasena(String contrasena) {
        this.setContrasena(contrasena);
        return this;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public FTP usuario(String usuario) {
        this.setUsuario(usuario);
        return this;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public LocalDate getFechaMod() {
        return this.fechaMod;
    }

    public FTP fechaMod(LocalDate fechaMod) {
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

    public FTP registinstit(RegistInstit registInstit) {
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

    public FTP tipocfdi(TipoCFDI tipoCFDI) {
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

    public FTP tiporecibo(TipoRecibo tipoRecibo) {
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
        if (!(o instanceof FTP)) {
            return false;
        }
        return id != null && id.equals(((FTP) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FTP{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", idInstit=" + getIdInstit() +
            ", nivel=" + getNivel() +
            ", clave='" + getClave() + "'" +
            ", carpetaFTP='" + getCarpetaFTP() + "'" +
            ", subcarpetaFTP='" + getSubcarpetaFTP() + "'" +
            ", descripcionFTP='" + getDescripcionFTP() + "'" +
            ", ipFTP='" + getIpFTP() + "'" +
            ", puerto=" + getPuerto() +
            ", usuarioFTP='" + getUsuarioFTP() + "'" +
            ", contrasena='" + getContrasena() + "'" +
            ", usuario='" + getUsuario() + "'" +
            ", fechaMod='" + getFechaMod() + "'" +
            "}";
    }
}
