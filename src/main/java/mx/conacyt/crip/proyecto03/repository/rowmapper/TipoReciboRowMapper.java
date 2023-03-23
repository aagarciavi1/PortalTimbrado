package mx.conacyt.crip.proyecto03.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.time.LocalDate;
import java.util.function.BiFunction;
import mx.conacyt.crip.proyecto03.domain.TipoRecibo;
import mx.conacyt.crip.proyecto03.domain.enumeration.Estatus;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link TipoRecibo}, with proper type conversions.
 */
@Service
public class TipoReciboRowMapper implements BiFunction<Row, String, TipoRecibo> {

    private final ColumnConverter converter;

    public TipoReciboRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link TipoRecibo} stored in the database.
     */
    @Override
    public TipoRecibo apply(Row row, String prefix) {
        TipoRecibo entity = new TipoRecibo();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setFecha(converter.fromRow(row, prefix + "_fecha", LocalDate.class));
        entity.setTipoRecibo(converter.fromRow(row, prefix + "_tipo_recibo", String.class));
        entity.setClave(converter.fromRow(row, prefix + "_clave", String.class));
        entity.setEstatus(converter.fromRow(row, prefix + "_estatus", Estatus.class));
        entity.setUsuario(converter.fromRow(row, prefix + "_usuario", String.class));
        entity.setFechaMod(converter.fromRow(row, prefix + "_fecha_mod", LocalDate.class));
        return entity;
    }
}
