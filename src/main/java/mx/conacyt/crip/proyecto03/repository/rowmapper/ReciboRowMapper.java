package mx.conacyt.crip.proyecto03.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.time.LocalDate;
import java.util.function.BiFunction;
import mx.conacyt.crip.proyecto03.domain.Recibo;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Recibo}, with proper type conversions.
 */
@Service
public class ReciboRowMapper implements BiFunction<Row, String, Recibo> {

    private final ColumnConverter converter;

    public ReciboRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Recibo} stored in the database.
     */
    @Override
    public Recibo apply(Row row, String prefix) {
        Recibo entity = new Recibo();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setFecha(converter.fromRow(row, prefix + "_fecha", LocalDate.class));
        entity.setIdInstit(converter.fromRow(row, prefix + "_id_instit", Integer.class));
        entity.setNivel(converter.fromRow(row, prefix + "_nivel", Integer.class));
        entity.setTipoCFDI(converter.fromRow(row, prefix + "_tipo_cfdi", String.class));
        entity.setClave(converter.fromRow(row, prefix + "_clave", String.class));
        entity.setEnvioConCFDI(converter.fromRow(row, prefix + "_envio_con_cfdi", String.class));
        entity.setEnvioSinCFDI(converter.fromRow(row, prefix + "_envio_sin_cfdi", String.class));
        entity.setAplicaLeyenda(converter.fromRow(row, prefix + "_aplica_leyenda", String.class));
        entity.setLeyenda(converter.fromRow(row, prefix + "_leyenda", Long.class));
        entity.setUsuario(converter.fromRow(row, prefix + "_usuario", String.class));
        entity.setFechaMod(converter.fromRow(row, prefix + "_fecha_mod", LocalDate.class));
        entity.setTiporeciboId(converter.fromRow(row, prefix + "_tiporecibo_id", Long.class));
        entity.setRepgraficaId(converter.fromRow(row, prefix + "_repgrafica_id", Long.class));
        entity.setParametrosId(converter.fromRow(row, prefix + "_parametros_id", Long.class));
        return entity;
    }
}
