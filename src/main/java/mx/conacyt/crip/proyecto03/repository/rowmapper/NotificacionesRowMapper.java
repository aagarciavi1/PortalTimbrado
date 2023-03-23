package mx.conacyt.crip.proyecto03.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.time.LocalDate;
import java.util.function.BiFunction;
import mx.conacyt.crip.proyecto03.domain.Notificaciones;
import mx.conacyt.crip.proyecto03.domain.enumeration.TipoNot;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Notificaciones}, with proper type conversions.
 */
@Service
public class NotificacionesRowMapper implements BiFunction<Row, String, Notificaciones> {

    private final ColumnConverter converter;

    public NotificacionesRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Notificaciones} stored in the database.
     */
    @Override
    public Notificaciones apply(Row row, String prefix) {
        Notificaciones entity = new Notificaciones();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setFecha(converter.fromRow(row, prefix + "_fecha", LocalDate.class));
        entity.setIdInstit(converter.fromRow(row, prefix + "_id_instit", Integer.class));
        entity.setNivel(converter.fromRow(row, prefix + "_nivel", Integer.class));
        entity.setTipoNot(converter.fromRow(row, prefix + "_tipo_not", TipoNot.class));
        entity.setClave(converter.fromRow(row, prefix + "_clave", String.class));
        entity.setAsunto(converter.fromRow(row, prefix + "_asunto", String.class));
        entity.setTexto(converter.fromRow(row, prefix + "_texto", Long.class));
        entity.setPiePagina(converter.fromRow(row, prefix + "_pie_pagina", String.class));
        entity.setUsuario(converter.fromRow(row, prefix + "_usuario", String.class));
        entity.setFechaMod(converter.fromRow(row, prefix + "_fecha_mod", LocalDate.class));
        entity.setRegistinstitId(converter.fromRow(row, prefix + "_registinstit_id", Long.class));
        entity.setTipocfdiId(converter.fromRow(row, prefix + "_tipocfdi_id", Long.class));
        entity.setTiporeciboId(converter.fromRow(row, prefix + "_tiporecibo_id", Long.class));
        return entity;
    }
}
