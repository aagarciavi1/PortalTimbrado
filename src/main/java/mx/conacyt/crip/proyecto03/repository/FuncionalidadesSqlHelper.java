package mx.conacyt.crip.proyecto03.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class FuncionalidadesSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("fecha", table, columnPrefix + "_fecha"));
        columns.add(Column.aliased("perfil", table, columnPrefix + "_perfil"));
        columns.add(Column.aliased("funcionalidad", table, columnPrefix + "_funcionalidad"));
        columns.add(Column.aliased("alta", table, columnPrefix + "_alta"));
        columns.add(Column.aliased("editar", table, columnPrefix + "_editar"));
        columns.add(Column.aliased("activar_inact", table, columnPrefix + "_activar_inact"));
        columns.add(Column.aliased("consulta", table, columnPrefix + "_consulta"));
        columns.add(Column.aliased("usuario", table, columnPrefix + "_usuario"));
        columns.add(Column.aliased("fecha_mod", table, columnPrefix + "_fecha_mod"));

        return columns;
    }
}
