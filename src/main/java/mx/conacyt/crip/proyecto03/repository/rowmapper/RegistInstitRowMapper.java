package mx.conacyt.crip.proyecto03.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.time.LocalDate;
import java.util.function.BiFunction;
import mx.conacyt.crip.proyecto03.domain.RegistInstit;
import mx.conacyt.crip.proyecto03.domain.enumeration.Estatus;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link RegistInstit}, with proper type conversions.
 */
@Service
public class RegistInstitRowMapper implements BiFunction<Row, String, RegistInstit> {

    private final ColumnConverter converter;

    public RegistInstitRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link RegistInstit} stored in the database.
     */
    @Override
    public RegistInstit apply(Row row, String prefix) {
        RegistInstit entity = new RegistInstit();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setFecha(converter.fromRow(row, prefix + "_fecha", LocalDate.class));
        entity.setIdInstit(converter.fromRow(row, prefix + "_id_instit", Integer.class));
        entity.setEstatus(converter.fromRow(row, prefix + "_estatus", Estatus.class));
        entity.setNivel(converter.fromRow(row, prefix + "_nivel", Integer.class));
        entity.setRazonSocial(converter.fromRow(row, prefix + "_razon_social", String.class));
        entity.setrFC(converter.fromRow(row, prefix + "_r_fc", String.class));
        entity.setPais(converter.fromRow(row, prefix + "_pais", String.class));
        entity.setMunicipio(converter.fromRow(row, prefix + "_municipio", String.class));
        entity.setLocalidad(converter.fromRow(row, prefix + "_localidad", String.class));
        entity.setCodigoPostal(converter.fromRow(row, prefix + "_codigo_postal", Integer.class));
        entity.setColonia(converter.fromRow(row, prefix + "_colonia", String.class));
        entity.setCalle(converter.fromRow(row, prefix + "_calle", String.class));
        entity.setNumExt(converter.fromRow(row, prefix + "_num_ext", Integer.class));
        entity.setNumInt(converter.fromRow(row, prefix + "_num_int", Integer.class));
        entity.setLogoContentType(converter.fromRow(row, prefix + "_logo_content_type", String.class));
        entity.setLogo(converter.fromRow(row, prefix + "_logo", byte[].class));
        entity.setCertificadoContentType(converter.fromRow(row, prefix + "_certificado_content_type", String.class));
        entity.setCertificado(converter.fromRow(row, prefix + "_certificado", byte[].class));
        entity.setLlavePrivContentType(converter.fromRow(row, prefix + "_llave_priv_content_type", String.class));
        entity.setLlavePriv(converter.fromRow(row, prefix + "_llave_priv", byte[].class));
        entity.setContrasena(converter.fromRow(row, prefix + "_contrasena", String.class));
        entity.setFechaExp(converter.fromRow(row, prefix + "_fecha_exp", LocalDate.class));
        entity.setDias(converter.fromRow(row, prefix + "_dias", Integer.class));
        entity.setCorreo(converter.fromRow(row, prefix + "_correo", String.class));
        entity.setUsuario(converter.fromRow(row, prefix + "_usuario", String.class));
        entity.setFechaMod(converter.fromRow(row, prefix + "_fecha_mod", LocalDate.class));
        entity.setRegimenfisId(converter.fromRow(row, prefix + "_regimenfis_id", Long.class));
        entity.setEstadoId(converter.fromRow(row, prefix + "_estado_id", Long.class));
        entity.setRegistinstitId(converter.fromRow(row, prefix + "_registinstit_id", Long.class));
        entity.setRegistinstitiId(converter.fromRow(row, prefix + "_registinstiti_id", Long.class));
        return entity;
    }
}
