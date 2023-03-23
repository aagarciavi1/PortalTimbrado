package mx.conacyt.crip.proyecto03.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.time.LocalDate;
import java.util.function.BiFunction;
import mx.conacyt.crip.proyecto03.domain.EstatusCFDI;
import mx.conacyt.crip.proyecto03.domain.enumeration.Estatus;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link EstatusCFDI}, with proper type conversions.
 */
@Service
public class EstatusCFDIRowMapper implements BiFunction<Row, String, EstatusCFDI> {

    private final ColumnConverter converter;

    public EstatusCFDIRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link EstatusCFDI} stored in the database.
     */
    @Override
    public EstatusCFDI apply(Row row, String prefix) {
        EstatusCFDI entity = new EstatusCFDI();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setFecha(converter.fromRow(row, prefix + "_fecha", LocalDate.class));
        entity.setDescEstCFDI(converter.fromRow(row, prefix + "_desc_est_cfdi", String.class));
        entity.setEstatus(converter.fromRow(row, prefix + "_estatus", Estatus.class));
        entity.setUsuario(converter.fromRow(row, prefix + "_usuario", String.class));
        entity.setFechaMod(converter.fromRow(row, prefix + "_fecha_mod", LocalDate.class));
        return entity;
    }
}
