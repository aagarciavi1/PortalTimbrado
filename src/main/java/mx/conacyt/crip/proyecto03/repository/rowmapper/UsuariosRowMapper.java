package mx.conacyt.crip.proyecto03.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.time.LocalDate;
import java.util.function.BiFunction;
import mx.conacyt.crip.proyecto03.domain.Usuarios;
import mx.conacyt.crip.proyecto03.domain.enumeration.Estatus;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Usuarios}, with proper type conversions.
 */
@Service
public class UsuariosRowMapper implements BiFunction<Row, String, Usuarios> {

    private final ColumnConverter converter;

    public UsuariosRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Usuarios} stored in the database.
     */
    @Override
    public Usuarios apply(Row row, String prefix) {
        Usuarios entity = new Usuarios();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setFecha(converter.fromRow(row, prefix + "_fecha", LocalDate.class));
        entity.setUsuario(converter.fromRow(row, prefix + "_usuario", String.class));
        entity.setDescr(converter.fromRow(row, prefix + "_descr", String.class));
        entity.setIdInstit(converter.fromRow(row, prefix + "_id_instit", Integer.class));
        entity.setCorreo(converter.fromRow(row, prefix + "_correo", String.class));
        entity.setContrasena(converter.fromRow(row, prefix + "_contrasena", String.class));
        entity.setConfirmarContras(converter.fromRow(row, prefix + "_confirmar_contras", String.class));
        entity.setEstatus(converter.fromRow(row, prefix + "_estatus", Estatus.class));
        entity.setUsuarioCrea(converter.fromRow(row, prefix + "_usuario_crea", String.class));
        entity.setRegistinstitId(converter.fromRow(row, prefix + "_registinstit_id", Long.class));
        entity.setPerfilId(converter.fromRow(row, prefix + "_perfil_id", Long.class));
        entity.setTipocfdiId(converter.fromRow(row, prefix + "_tipocfdi_id", Long.class));
        entity.setTiporeciboId(converter.fromRow(row, prefix + "_tiporecibo_id", Long.class));
        return entity;
    }
}
