package mx.conacyt.crip.proyecto03.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class FTPSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("fecha", table, columnPrefix + "_fecha"));
        columns.add(Column.aliased("id_instit", table, columnPrefix + "_id_instit"));
        columns.add(Column.aliased("nivel", table, columnPrefix + "_nivel"));
        columns.add(Column.aliased("clave", table, columnPrefix + "_clave"));
        columns.add(Column.aliased("carpeta_ftp", table, columnPrefix + "_carpeta_ftp"));
        columns.add(Column.aliased("subcarpeta_ftp", table, columnPrefix + "_subcarpeta_ftp"));
        columns.add(Column.aliased("descripcion_ftp", table, columnPrefix + "_descripcion_ftp"));
        columns.add(Column.aliased("ip_ftp", table, columnPrefix + "_ip_ftp"));
        columns.add(Column.aliased("puerto", table, columnPrefix + "_puerto"));
        columns.add(Column.aliased("usuario_ftp", table, columnPrefix + "_usuario_ftp"));
        columns.add(Column.aliased("contrasena", table, columnPrefix + "_contrasena"));
        columns.add(Column.aliased("usuario", table, columnPrefix + "_usuario"));
        columns.add(Column.aliased("fecha_mod", table, columnPrefix + "_fecha_mod"));

        columns.add(Column.aliased("registinstit_id", table, columnPrefix + "_registinstit_id"));
        columns.add(Column.aliased("tipocfdi_id", table, columnPrefix + "_tipocfdi_id"));
        columns.add(Column.aliased("tiporecibo_id", table, columnPrefix + "_tiporecibo_id"));
        return columns;
    }
}
