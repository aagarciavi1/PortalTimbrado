package mx.conacyt.crip.proyecto03.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class TipoReciboSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("fecha", table, columnPrefix + "_fecha"));
        columns.add(Column.aliased("tipo_recibo", table, columnPrefix + "_tipo_recibo"));
        columns.add(Column.aliased("clave", table, columnPrefix + "_clave"));
        columns.add(Column.aliased("estatus", table, columnPrefix + "_estatus"));
        columns.add(Column.aliased("usuario", table, columnPrefix + "_usuario"));
        columns.add(Column.aliased("fecha_mod", table, columnPrefix + "_fecha_mod"));

        return columns;
    }
}
