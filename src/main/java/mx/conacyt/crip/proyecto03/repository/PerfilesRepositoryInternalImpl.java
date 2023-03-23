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
import mx.conacyt.crip.proyecto03.domain.Perfiles;
import mx.conacyt.crip.proyecto03.repository.rowmapper.FuncionalidadesRowMapper;
import mx.conacyt.crip.proyecto03.repository.rowmapper.PerfilesRowMapper;
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
 * Spring Data R2DBC custom repository implementation for the Perfiles entity.
 */
@SuppressWarnings("unused")
class PerfilesRepositoryInternalImpl extends SimpleR2dbcRepository<Perfiles, Long> implements PerfilesRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final FuncionalidadesRowMapper funcionalidadesMapper;
    private final PerfilesRowMapper perfilesMapper;

    private static final Table entityTable = Table.aliased("perfiles", EntityManager.ENTITY_ALIAS);
    private static final Table funcionalidadesTable = Table.aliased("funcionalidades", "funcionalidades");

    public PerfilesRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        FuncionalidadesRowMapper funcionalidadesMapper,
        PerfilesRowMapper perfilesMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(Perfiles.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.funcionalidadesMapper = funcionalidadesMapper;
        this.perfilesMapper = perfilesMapper;
    }

    @Override
    public Flux<Perfiles> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<Perfiles> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = PerfilesSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(FuncionalidadesSqlHelper.getColumns(funcionalidadesTable, "funcionalidades"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(funcionalidadesTable)
            .on(Column.create("funcionalidades_id", entityTable))
            .equals(Column.create("id", funcionalidadesTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, Perfiles.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<Perfiles> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<Perfiles> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private Perfiles process(Row row, RowMetadata metadata) {
        Perfiles entity = perfilesMapper.apply(row, "e");
        entity.setFuncionalidades(funcionalidadesMapper.apply(row, "funcionalidades"));
        return entity;
    }

    @Override
    public <S extends Perfiles> Mono<S> save(S entity) {
        return super.save(entity);
    }
}
