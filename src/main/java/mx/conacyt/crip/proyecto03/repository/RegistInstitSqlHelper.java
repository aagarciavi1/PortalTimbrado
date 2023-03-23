package mx.conacyt.crip.proyecto03.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class RegistInstitSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("fecha", table, columnPrefix + "_fecha"));
        columns.add(Column.aliased("id_instit", table, columnPrefix + "_id_instit"));
        columns.add(Column.aliased("estatus", table, columnPrefix + "_estatus"));
        columns.add(Column.aliased("nivel", table, columnPrefix + "_nivel"));
        columns.add(Column.aliased("razon_social", table, columnPrefix + "_razon_social"));
        columns.add(Column.aliased("r_fc", table, columnPrefix + "_r_fc"));
        columns.add(Column.aliased("pais", table, columnPrefix + "_pais"));
        columns.add(Column.aliased("municipio", table, columnPrefix + "_municipio"));
        columns.add(Column.aliased("localidad", table, columnPrefix + "_localidad"));
        columns.add(Column.aliased("codigo_postal", table, columnPrefix + "_codigo_postal"));
        columns.add(Column.aliased("colonia", table, columnPrefix + "_colonia"));
        columns.add(Column.aliased("calle", table, columnPrefix + "_calle"));
        columns.add(Column.aliased("num_ext", table, columnPrefix + "_num_ext"));
        columns.add(Column.aliased("num_int", table, columnPrefix + "_num_int"));
        columns.add(Column.aliased("logo", table, columnPrefix + "_logo"));
        columns.add(Column.aliased("logo_content_type", table, columnPrefix + "_logo_content_type"));
        columns.add(Column.aliased("certificado", table, columnPrefix + "_certificado"));
        columns.add(Column.aliased("certificado_content_type", table, columnPrefix + "_certificado_content_type"));
        columns.add(Column.aliased("llave_priv", table, columnPrefix + "_llave_priv"));
        columns.add(Column.aliased("llave_priv_content_type", table, columnPrefix + "_llave_priv_content_type"));
        columns.add(Column.aliased("contrasena", table, columnPrefix + "_contrasena"));
        columns.add(Column.aliased("fecha_exp", table, columnPrefix + "_fecha_exp"));
        columns.add(Column.aliased("dias", table, columnPrefix + "_dias"));
        columns.add(Column.aliased("correo", table, columnPrefix + "_correo"));
        columns.add(Column.aliased("usuario", table, columnPrefix + "_usuario"));
        columns.add(Column.aliased("fecha_mod", table, columnPrefix + "_fecha_mod"));

        columns.add(Column.aliased("regimenfis_id", table, columnPrefix + "_regimenfis_id"));
        columns.add(Column.aliased("estado_id", table, columnPrefix + "_estado_id"));
        columns.add(Column.aliased("registinstit_id", table, columnPrefix + "_registinstit_id"));
        columns.add(Column.aliased("registinstiti_id", table, columnPrefix + "_registinstiti_id"));
        return columns;
    }
}
