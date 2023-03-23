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
 * A Usuarios.
 */
@Table("usuarios")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Usuarios implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @NotNull(message = "must not be null")
    @Column("fecha")
    private LocalDate fecha;

    @NotNull(message = "must not be null")
    @Column("usuario")
    private String usuario;

    @NotNull(message = "must not be null")
    @Column("descr")
    private String descr;

    @NotNull(message = "must not be null")
    @Column("id_instit")
    private Integer idInstit;

    @Column("correo")
    private String correo;

    @Column("contrasena")
    private String contrasena;

    @Column("confirmar_contras")
    private String confirmarContras;

    @Column("estatus")
    private Estatus estatus;

    @Column("usuario_crea")
    private String usuarioCrea;

    @Transient
    private RegistInstit registinstit;

    @Transient
    private Perfiles perfil;

    @Transient
    private TipoCFDI tipocfdi;

    @Transient
    private TipoRecibo tiporecibo;

    @Column("registinstit_id")
    private Long registinstitId;

    @Column("perfil_id")
    private Long perfilId;

    @Column("tipocfdi_id")
    private Long tipocfdiId;

    @Column("tiporecibo_id")
    private Long tiporeciboId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Usuarios id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public Usuarios fecha(LocalDate fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public Usuarios usuario(String usuario) {
        this.setUsuario(usuario);
        return this;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getDescr() {
        return this.descr;
    }

    public Usuarios descr(String descr) {
        this.setDescr(descr);
        return this;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Integer getIdInstit() {
        return this.idInstit;
    }

    public Usuarios idInstit(Integer idInstit) {
        this.setIdInstit(idInstit);
        return this;
    }

    public void setIdInstit(Integer idInstit) {
        this.idInstit = idInstit;
    }

    public String getCorreo() {
        return this.correo;
    }

    public Usuarios correo(String correo) {
        this.setCorreo(correo);
        return this;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return this.contrasena;
    }

    public Usuarios contrasena(String contrasena) {
        this.setContrasena(contrasena);
        return this;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getConfirmarContras() {
        return this.confirmarContras;
    }

    public Usuarios confirmarContras(String confirmarContras) {
        this.setConfirmarContras(confirmarContras);
        return this;
    }

    public void setConfirmarContras(String confirmarContras) {
        this.confirmarContras = confirmarContras;
    }

    public Estatus getEstatus() {
        return this.estatus;
    }

    public Usuarios estatus(Estatus estatus) {
        this.setEstatus(estatus);
        return this;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

    public String getUsuarioCrea() {
        return this.usuarioCrea;
    }

    public Usuarios usuarioCrea(String usuarioCrea) {
        this.setUsuarioCrea(usuarioCrea);
        return this;
    }

    public void setUsuarioCrea(String usuarioCrea) {
        this.usuarioCrea = usuarioCrea;
    }

    public RegistInstit getRegistinstit() {
        return this.registinstit;
    }

    public void setRegistinstit(RegistInstit registInstit) {
        this.registinstit = registInstit;
        this.registinstitId = registInstit != null ? registInstit.getId() : null;
    }

    public Usuarios registinstit(RegistInstit registInstit) {
        this.setRegistinstit(registInstit);
        return this;
    }

    public Perfiles getPerfil() {
        return this.perfil;
    }

    public void setPerfil(Perfiles perfiles) {
        this.perfil = perfiles;
        this.perfilId = perfiles != null ? perfiles.getId() : null;
    }

    public Usuarios perfil(Perfiles perfiles) {
        this.setPerfil(perfiles);
        return this;
    }

    public TipoCFDI getTipocfdi() {
        return this.tipocfdi;
    }

    public void setTipocfdi(TipoCFDI tipoCFDI) {
        this.tipocfdi = tipoCFDI;
        this.tipocfdiId = tipoCFDI != null ? tipoCFDI.getId() : null;
    }

    public Usuarios tipocfdi(TipoCFDI tipoCFDI) {
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

    public Usuarios tiporecibo(TipoRecibo tipoRecibo) {
        this.setTiporecibo(tipoRecibo);
        return this;
    }

    public Long getRegistinstitId() {
        return this.registinstitId;
    }

    public void setRegistinstitId(Long registInstit) {
        this.registinstitId = registInstit;
    }

    public Long getPerfilId() {
        return this.perfilId;
    }

    public void setPerfilId(Long perfiles) {
        this.perfilId = perfiles;
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
        if (!(o instanceof Usuarios)) {
            return false;
        }
        return id != null && id.equals(((Usuarios) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Usuarios{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", usuario='" + getUsuario() + "'" +
            ", descr='" + getDescr() + "'" +
            ", idInstit=" + getIdInstit() +
            ", correo='" + getCorreo() + "'" +
            ", contrasena='" + getContrasena() + "'" +
            ", confirmarContras='" + getConfirmarContras() + "'" +
            ", estatus='" + getEstatus() + "'" +
            ", usuarioCrea='" + getUsuarioCrea() + "'" +
            "}";
    }
}
