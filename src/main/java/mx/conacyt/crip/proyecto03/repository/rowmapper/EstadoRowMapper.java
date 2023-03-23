package mx.conacyt.crip.proyecto03.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.time.LocalDate;
import java.util.function.BiFunction;
import mx.conacyt.crip.proyecto03.domain.Estado;
import mx.conacyt.crip.proyecto03.domain.enumeration.Estatus;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Estado}, with proper type conversions.
 */
@Service
public class EstadoRowMapper implements BiFunction<Row, String, Estado> {

    private final ColumnConverter converter;

    public EstadoRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Estado} stored in the database.
     */
    @Override
    public Estado apply(Row row, String prefix) {
        Estado entity = new Estado();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setFecha(converter.fromRow(row, prefix + "_fecha", LocalDate.class));
        entity.setPais(converter.fromRow(row, prefix + "_pais", String.class));
        entity.setEstatdo(converter.fromRow(row, prefix + "_estatdo", String.class));
        entity.setDescEstado(converter.fromRow(row, prefix + "_desc_estado", String.class));
        entity.setEstatus(converter.fromRow(row, prefix + "_estatus", Estatus.class));
        entity.setUsuario(converter.fromRow(row, prefix + "_usuario", String.class));
        entity.setFechaMod(converter.fromRow(row, prefix + "_fecha_mod", LocalDate.class));
        return entity;
    }
}
