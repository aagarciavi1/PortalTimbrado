package mx.conacyt.crip.proyecto03.repository;

import static org.springframework.data.relational.core.query.Criteria.where;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.BiFunction;
import mx.conacyt.crip.proyecto03.domain.Usuarios;
import mx.conacyt.crip.proyecto03.domain.enumeration.Estatus;
import mx.conacyt.crip.proyecto03.repository.rowmapper.PerfilesRowMapper;
import mx.conacyt.crip.proyecto03.repository.rowmapper.RegistInstitRowMapper;
import mx.conacyt.crip.proyecto03.repository.rowmapper.TipoCFDIRowMapper;
import mx.conacyt.crip.proyecto03.repository.rowmapper.TipoReciboRowMapper;
import mx.conacyt.crip.proyecto03.repository.rowmapper.UsuariosRowMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.convert.R2dbcConverter;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.repository.support.SimpleR2dbcRepository;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Comparison;
import org.springframework.data.relational.core.sql.Condition;
import org.springframework.data.relational.core.sql.Conditions;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Select;
import org.springframework.data.relational.core.sql.SelectBuilder.SelectFromAndJoinCondition;
import org.springframework.data.relational.core.sql.Table;
import org.springframework.data.relational.repository.support.MappingRelationalEntityInformation;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.RowsFetchSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC custom repository implementation for the Usuarios entity.
 */
@SuppressWarnings("unused")
class UsuariosRepositoryInternalImpl extends SimpleR2dbcRepository<Usuarios, Long> implements UsuariosRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final RegistInstitRowMapper registinstitMapper;
    private final PerfilesRowMapper perfilesMapper;
    private final TipoCFDIRowMapper tipocfdiMapper;
    private final TipoReciboRowMapper tiporeciboMapper;
    private final UsuariosRowMapper usuariosMapper;

    private static final Table entityTable = Table.aliased("usuarios", EntityManager.ENTITY_ALIAS);
    private static final Table registinstitTable = Table.aliased("regist_instit", "registinstit");
    private static final Table perfilTable = Table.aliased("perfiles", "perfil");
    private static final Table tipocfdiTable = Table.aliased("tipo_cfdi", "tipocfdi");
    private static final Table tiporeciboTable = Table.aliased("tipo_recibo", "tiporecibo");

    public UsuariosRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        RegistInstitRowMapper registinstitMapper,
        PerfilesRowMapper perfilesMapper,
        TipoCFDIRowMapper tipocfdiMapper,
        TipoReciboRowMapper tiporeciboMapper,
        UsuariosRowMapper usuariosMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(Usuarios.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.registinstitMapper = registinstitMapper;
        this.perfilesMapper = perfilesMapper;
        this.tipocfdiMapper = tipocfdiMapper;
        this.tiporeciboMapper = tiporeciboMapper;
        this.usuariosMapper = usuariosMapper;
    }

    @Override
    public Flux<Usuarios> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<Usuarios> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = UsuariosSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(RegistInstitSqlHelper.getColumns(registinstitTable, "registinstit"));
        columns.addAll(PerfilesSqlHelper.getColumns(perfilTable, "perfil"));
        columns.addAll(TipoCFDISqlHelper.getColumns(tipocfdiTable, "tipocfdi"));
        columns.addAll(TipoReciboSqlHelper.getColumns(tiporeciboTable, "tiporecibo"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(registinstitTable)
            .on(Column.create("registinstit_id", entityTable))
            .equals(Column.create("id", registinstitTable))
            .leftOuterJoin(perfilTable)
            .on(Column.create("perfil_id", entityTable))
            .equals(Column.create("id", perfilTable))
            .leftOuterJoin(tipocfdiTable)
            .on(Column.create("tipocfdi_id", entityTable))
            .equals(Column.create("id", tipocfdiTable))
            .leftOuterJoin(tiporeciboTable)
            .on(Column.create("tiporecibo_id", entityTable))
            .equals(Column.create("id", tiporeciboTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, Usuarios.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<Usuarios> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<Usuarios> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private Usuarios process(Row row, RowMetadata metadata) {
        Usuarios entity = usuariosMapper.apply(row, "e");
        entity.setRegistinstit(registinstitMapper.apply(row, "registinstit"));
        entity.setPerfil(perfilesMapper.apply(row, "perfil"));
        entity.setTipocfdi(tipocfdiMapper.apply(row, "tipocfdi"));
        entity.setTiporecibo(tiporeciboMapper.apply(row, "tiporecibo"));
        return entity;
    }

    @Override
    public <S extends Usuarios> Mono<S> save(S entity) {
        return super.save(entity);
    }
}
