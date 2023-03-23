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
import mx.conacyt.crip.proyecto03.domain.RegistInstit;
import mx.conacyt.crip.proyecto03.domain.enumeration.Estatus;
import mx.conacyt.crip.proyecto03.repository.rowmapper.EstadoRowMapper;
import mx.conacyt.crip.proyecto03.repository.rowmapper.RegimenFisRowMapper;
import mx.conacyt.crip.proyecto03.repository.rowmapper.RegistInstitRowMapper;
import mx.conacyt.crip.proyecto03.repository.rowmapper.RegistInstitRowMapper;
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
 * Spring Data R2DBC custom repository implementation for the RegistInstit entity.
 */
@SuppressWarnings("unused")
class RegistInstitRepositoryInternalImpl extends SimpleR2dbcRepository<RegistInstit, Long> implements RegistInstitRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final RegimenFisRowMapper regimenfisMapper;
    private final EstadoRowMapper estadoMapper;
    private final RegistInstitRowMapper registinstitMapper;

    private static final Table entityTable = Table.aliased("regist_instit", EntityManager.ENTITY_ALIAS);
    private static final Table regimenfisTable = Table.aliased("regimen_fis", "regimenfis");
    private static final Table estadoTable = Table.aliased("estado", "estado");
    private static final Table registinstitTable = Table.aliased("regist_instit", "registinstit");
    private static final Table registinstitiTable = Table.aliased("regist_instit", "registinstiti");

    public RegistInstitRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        RegimenFisRowMapper regimenfisMapper,
        EstadoRowMapper estadoMapper,
        RegistInstitRowMapper registinstitMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(RegistInstit.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.regimenfisMapper = regimenfisMapper;
        this.estadoMapper = estadoMapper;
        this.registinstitMapper = registinstitMapper;
    }

    @Override
    public Flux<RegistInstit> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<RegistInstit> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = RegistInstitSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(RegimenFisSqlHelper.getColumns(regimenfisTable, "regimenfis"));
        columns.addAll(EstadoSqlHelper.getColumns(estadoTable, "estado"));
        columns.addAll(RegistInstitSqlHelper.getColumns(registinstitTable, "registinstit"));
        columns.addAll(RegistInstitSqlHelper.getColumns(registinstitiTable, "registinstiti"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(regimenfisTable)
            .on(Column.create("regimenfis_id", entityTable))
            .equals(Column.create("id", regimenfisTable))
            .leftOuterJoin(estadoTable)
            .on(Column.create("estado_id", entityTable))
            .equals(Column.create("id", estadoTable))
            .leftOuterJoin(registinstitTable)
            .on(Column.create("registinstit_id", entityTable))
            .equals(Column.create("id", registinstitTable))
            .leftOuterJoin(registinstitiTable)
            .on(Column.create("registinstiti_id", entityTable))
            .equals(Column.create("id", registinstitiTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, RegistInstit.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<RegistInstit> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<RegistInstit> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private RegistInstit process(Row row, RowMetadata metadata) {
        RegistInstit entity = registinstitMapper.apply(row, "e");
        entity.setRegimenfis(regimenfisMapper.apply(row, "regimenfis"));
        entity.setEstado(estadoMapper.apply(row, "estado"));
        entity.setRegistinstit(registinstitMapper.apply(row, "registinstit"));
        entity.setRegistinstiti(registinstitMapper.apply(row, "registinstiti"));
        return entity;
    }

    @Override
    public <S extends RegistInstit> Mono<S> save(S entity) {
        return super.save(entity);
    }
}
