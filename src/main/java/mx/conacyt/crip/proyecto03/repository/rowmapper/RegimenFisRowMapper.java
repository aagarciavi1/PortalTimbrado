package mx.conacyt.crip.proyecto03.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.time.LocalDate;
import java.util.function.BiFunction;
import mx.conacyt.crip.proyecto03.domain.RegimenFis;
import mx.conacyt.crip.proyecto03.domain.enumeration.Estatus;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link RegimenFis}, with proper type conversions.
 */
@Service
public class RegimenFisRowMapper implements BiFunction<Row, String, RegimenFis> {

    private final ColumnConverter converter;

    public RegimenFisRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link RegimenFis} stored in the database.
     */
    @Override
    public RegimenFis apply(Row row, String prefix) {
        RegimenFis entity = new RegimenFis();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setFecha(converter.fromRow(row, prefix + "_fecha", LocalDate.class));
        entity.setRegimenF(converter.fromRow(row, prefix + "_regimen_f", String.class));
        entity.setEstatus(converter.fromRow(row, prefix + "_estatus", Estatus.class));
        entity.setUsuario(converter.fromRow(row, prefix + "_usuario", String.class));
        entity.setFechaMod(converter.fromRow(row, prefix + "_fecha_mod", String.class));
        return entity;
    }
}
