package mx.conacyt.crip.proyecto03.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.time.LocalDate;
import java.util.function.BiFunction;
import mx.conacyt.crip.proyecto03.domain.Parametros;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Parametros}, with proper type conversions.
 */
@Service
public class ParametrosRowMapper implements BiFunction<Row, String, Parametros> {

    private final ColumnConverter converter;

    public ParametrosRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Parametros} stored in the database.
     */
    @Override
    public Parametros apply(Row row, String prefix) {
        Parametros entity = new Parametros();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setFecha(converter.fromRow(row, prefix + "_fecha", LocalDate.class));
        entity.setIdInstit(converter.fromRow(row, prefix + "_id_instit", Integer.class));
        entity.setNivel(converter.fromRow(row, prefix + "_nivel", Integer.class));
        entity.setUsuario(converter.fromRow(row, prefix + "_usuario", String.class));
        entity.setFechaMod(converter.fromRow(row, prefix + "_fecha_mod", LocalDate.class));
        entity.setRegistinstitId(converter.fromRow(row, prefix + "_registinstit_id", Long.class));
        entity.setTipocfdiId(converter.fromRow(row, prefix + "_tipocfdi_id", Long.class));
        return entity;
    }
}
