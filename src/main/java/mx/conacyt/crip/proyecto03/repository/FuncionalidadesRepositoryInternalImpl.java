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
import mx.conacyt.crip.proyecto03.domain.Funcionalidades;
import mx.conacyt.crip.proyecto03.domain.enumeration.Funcionalidad;
import mx.conacyt.crip.proyecto03.repository.rowmapper.FuncionalidadesRowMapper;
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
import org.springframework.data.relational.core.sql.SelectBuilder.SelectFromAndJoin;
import org.springframework.data.relational.core.sql.Table;
import org.springframework.data.relational.repository.support.MappingRelationalEntityInformation;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.RowsFetchSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC custom repository implementation for the Funcionalidades entity.
 */
@SuppressWarnings("unused")
class FuncionalidadesRepositoryInternalImpl
    extends SimpleR2dbcRepository<Funcionalidades, Long>
    implements FuncionalidadesRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final FuncionalidadesRowMapper funcionalidadesMapper;

    private static final Table entityTable = Table.aliased("funcionalidades", EntityManager.ENTITY_ALIAS);

    public FuncionalidadesRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        FuncionalidadesRowMapper funcionalidadesMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(Funcionalidades.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.funcionalidadesMapper = funcionalidadesMapper;
    }

    @Override
    public Flux<Funcionalidades> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<Funcionalidades> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = FuncionalidadesSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        SelectFromAndJoin selectFrom = Select.builder().select(columns).from(entityTable);
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, Funcionalidades.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<Funcionalidades> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<Funcionalidades> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private Funcionalidades process(Row row, RowMetadata metadata) {
        Funcionalidades entity = funcionalidadesMapper.apply(row, "e");
        return entity;
    }

    @Override
    public <S extends Funcionalidades> Mono<S> save(S entity) {
        return super.save(entity);
    }
}
