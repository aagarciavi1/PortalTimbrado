package mx.conacyt.crip.proyecto03.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.time.LocalDate;
import java.util.function.BiFunction;
import mx.conacyt.crip.proyecto03.domain.FTP;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link FTP}, with proper type conversions.
 */
@Service
public class FTPRowMapper implements BiFunction<Row, String, FTP> {

    private final ColumnConverter converter;

    public FTPRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link FTP} stored in the database.
     */
    @Override
    public FTP apply(Row row, String prefix) {
        FTP entity = new FTP();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setFecha(converter.fromRow(row, prefix + "_fecha", LocalDate.class));
        entity.setIdInstit(converter.fromRow(row, prefix + "_id_instit", Integer.class));
        entity.setNivel(converter.fromRow(row, prefix + "_nivel", Integer.class));
        entity.setClave(converter.fromRow(row, prefix + "_clave", String.class));
        entity.setCarpetaFTP(converter.fromRow(row, prefix + "_carpeta_ftp", String.class));
        entity.setSubcarpetaFTP(converter.fromRow(row, prefix + "_subcarpeta_ftp", String.class));
        entity.setDescripcionFTP(converter.fromRow(row, prefix + "_descripcion_ftp", String.class));
        entity.setIpFTP(converter.fromRow(row, prefix + "_ip_ftp", String.class));
        entity.setPuerto(converter.fromRow(row, prefix + "_puerto", Integer.class));
        entity.setUsuarioFTP(converter.fromRow(row, prefix + "_usuario_ftp", String.class));
        entity.setContrasena(converter.fromRow(row, prefix + "_contrasena", String.class));
        entity.setUsuario(converter.fromRow(row, prefix + "_usuario", String.class));
        entity.setFechaMod(converter.fromRow(row, prefix + "_fecha_mod", LocalDate.class));
        entity.setRegistinstitId(converter.fromRow(row, prefix + "_registinstit_id", Long.class));
        entity.setTipocfdiId(converter.fromRow(row, prefix + "_tipocfdi_id", Long.class));
        entity.setTiporeciboId(converter.fromRow(row, prefix + "_tiporecibo_id", Long.class));
        return entity;
    }
}
