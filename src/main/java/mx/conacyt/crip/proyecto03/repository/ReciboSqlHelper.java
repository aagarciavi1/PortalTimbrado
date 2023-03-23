package mx.conacyt.crip.proyecto03.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class ReciboSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("fecha", table, columnPrefix + "_fecha"));
        columns.add(Column.aliased("id_instit", table, columnPrefix + "_id_instit"));
        columns.add(Column.aliased("nivel", table, columnPrefix + "_nivel"));
        columns.add(Column.aliased("tipo_cfdi", table, columnPrefix + "_tipo_cfdi"));
        columns.add(Column.aliased("clave", table, columnPrefix + "_clave"));
        columns.add(Column.aliased("envio_con_cfdi", table, columnPrefix + "_envio_con_cfdi"));
        columns.add(Column.aliased("envio_sin_cfdi", table, columnPrefix + "_envio_sin_cfdi"));
        columns.add(Column.aliased("aplica_leyenda", table, columnPrefix + "_aplica_leyenda"));
        columns.add(Column.aliased("leyenda", table, columnPrefix + "_leyenda"));
        columns.add(Column.aliased("usuario", table, columnPrefix + "_usuario"));
        columns.add(Column.aliased("fecha_mod", table, columnPrefix + "_fecha_mod"));

        columns.add(Column.aliased("tiporecibo_id", table, columnPrefix + "_tiporecibo_id"));
        columns.add(Column.aliased("repgrafica_id", table, columnPrefix + "_repgrafica_id"));
        columns.add(Column.aliased("parametros_id", table, columnPrefix + "_parametros_id"));
        return columns;
    }
}
