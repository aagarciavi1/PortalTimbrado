package mx.conacyt.crip.proyecto03.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class UsuariosSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("fecha", table, columnPrefix + "_fecha"));
        columns.add(Column.aliased("usuario", table, columnPrefix + "_usuario"));
        columns.add(Column.aliased("descr", table, columnPrefix + "_descr"));
        columns.add(Column.aliased("id_instit", table, columnPrefix + "_id_instit"));
        columns.add(Column.aliased("correo", table, columnPrefix + "_correo"));
        columns.add(Column.aliased("contrasena", table, columnPrefix + "_contrasena"));
        columns.add(Column.aliased("confirmar_contras", table, columnPrefix + "_confirmar_contras"));
        columns.add(Column.aliased("estatus", table, columnPrefix + "_estatus"));
        columns.add(Column.aliased("usuario_crea", table, columnPrefix + "_usuario_crea"));

        columns.add(Column.aliased("registinstit_id", table, columnPrefix + "_registinstit_id"));
        columns.add(Column.aliased("perfil_id", table, columnPrefix + "_perfil_id"));
        columns.add(Column.aliased("tipocfdi_id", table, columnPrefix + "_tipocfdi_id"));
        columns.add(Column.aliased("tiporecibo_id", table, columnPrefix + "_tiporecibo_id"));
        return columns;
    }
}
