package mx.conacyt.crip.proyecto03.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.time.LocalDate;
import java.util.function.BiFunction;
import mx.conacyt.crip.proyecto03.domain.Funcionalidades;
import mx.conacyt.crip.proyecto03.domain.enumeration.Funcionalidad;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Funcionalidades}, with proper type conversions.
 */
@Service
public class FuncionalidadesRowMapper implements BiFunction<Row, String, Funcionalidades> {

    private final ColumnConverter converter;

    public FuncionalidadesRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Funcionalidades} stored in the database.
     */
    @Override
    public Funcionalidades apply(Row row, String prefix) {
        Funcionalidades entity = new Funcionalidades();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setFecha(converter.fromRow(row, prefix + "_fecha", LocalDate.class));
        entity.setPerfil(converter.fromRow(row, prefix + "_perfil", String.class));
        entity.setFuncionalidad(converter.fromRow(row, prefix + "_funcionalidad", Funcionalidad.class));
        entity.setAlta(converter.fromRow(row, prefix + "_alta", String.class));
        entity.setEditar(converter.fromRow(row, prefix + "_editar", String.class));
        entity.setActivarInact(converter.fromRow(row, prefix + "_activar_inact", String.class));
        entity.setConsulta(converter.fromRow(row, prefix + "_consulta", String.class));
        entity.setUsuario(converter.fromRow(row, prefix + "_usuario", String.class));
        entity.setFechaMod(converter.fromRow(row, prefix + "_fecha_mod", LocalDate.class));
        return entity;
    }
}
