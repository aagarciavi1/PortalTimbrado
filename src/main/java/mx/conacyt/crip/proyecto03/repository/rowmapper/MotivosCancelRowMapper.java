package mx.conacyt.crip.proyecto03.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.time.LocalDate;
import java.util.function.BiFunction;
import mx.conacyt.crip.proyecto03.domain.MotivosCancel;
import mx.conacyt.crip.proyecto03.domain.enumeration.Estatus;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link MotivosCancel}, with proper type conversions.
 */
@Service
public class MotivosCancelRowMapper implements BiFunction<Row, String, MotivosCancel> {

    private final ColumnConverter converter;

    public MotivosCancelRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link MotivosCancel} stored in the database.
     */
    @Override
    public MotivosCancel apply(Row row, String prefix) {
        MotivosCancel entity = new MotivosCancel();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setFecha(converter.fromRow(row, prefix + "_fecha", LocalDate.class));
        entity.setMotivoCancel(converter.fromRow(row, prefix + "_motivo_cancel", String.class));
        entity.setEstatus(converter.fromRow(row, prefix + "_estatus", Estatus.class));
        entity.setUsuario(converter.fromRow(row, prefix + "_usuario", String.class));
        entity.setFechaMod(converter.fromRow(row, prefix + "_fecha_mod", LocalDate.class));
        return entity;
    }
}
