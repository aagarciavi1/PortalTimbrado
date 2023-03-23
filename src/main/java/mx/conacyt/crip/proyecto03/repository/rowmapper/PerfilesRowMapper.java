package mx.conacyt.crip.proyecto03.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.time.LocalDate;
import java.util.function.BiFunction;
import mx.conacyt.crip.proyecto03.domain.Perfiles;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Perfiles}, with proper type conversions.
 */
@Service
public class PerfilesRowMapper implements BiFunction<Row, String, Perfiles> {

    private final ColumnConverter converter;

    public PerfilesRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Perfiles} stored in the database.
     */
    @Override
    public Perfiles apply(Row row, String prefix) {
        Perfiles entity = new Perfiles();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setFecha(converter.fromRow(row, prefix + "_fecha", LocalDate.class));
        entity.setPerfil(converter.fromRow(row, prefix + "_perfil", String.class));
        entity.setUsuario(converter.fromRow(row, prefix + "_usuario", String.class));
        entity.setFechaMod(converter.fromRow(row, prefix + "_fecha_mod", LocalDate.class));
        entity.setFuncionalidadesId(converter.fromRow(row, prefix + "_funcionalidades_id", Long.class));
        return entity;
    }
}
