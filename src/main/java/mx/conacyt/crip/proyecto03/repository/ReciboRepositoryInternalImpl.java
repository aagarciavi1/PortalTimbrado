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
import mx.conacyt.crip.proyecto03.domain.Recibo;
import mx.conacyt.crip.proyecto03.repository.rowmapper.ParametrosRowMapper;
import mx.conacyt.crip.proyecto03.repository.rowmapper.ReciboRowMapper;
import mx.conacyt.crip.proyecto03.repository.rowmapper.RepGraficaRowMapper;
import mx.conacyt.crip.proyecto03.repository.rowmapper.TipoReciboRowMapper;
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
 * Spring Data R2DBC custom repository implementation for the Recibo entity.
 */
@SuppressWarnings("unused")
class ReciboRepositoryInternalImpl extends SimpleR2dbcRepository<Recibo, Long> implements ReciboRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final TipoReciboRowMapper tiporeciboMapper;
    private final RepGraficaRowMapper repgraficaMapper;
    private final ParametrosRowMapper parametrosMapper;
    private final ReciboRowMapper reciboMapper;

    private static final Table entityTable = Table.aliased("recibo", EntityManager.ENTITY_ALIAS);
    private static final Table tiporeciboTable = Table.aliased("tipo_recibo", "tiporecibo");
    private static final Table repgraficaTable = Table.aliased("rep_grafica", "repgrafica");
    private static final Table parametrosTable = Table.aliased("parametros", "parametros");

    public ReciboRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        TipoReciboRowMapper tiporeciboMapper,
        RepGraficaRowMapper repgraficaMapper,
        ParametrosRowMapper parametrosMapper,
        ReciboRowMapper reciboMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(Recibo.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.tiporeciboMapper = tiporeciboMapper;
        this.repgraficaMapper = repgraficaMapper;
        this.parametrosMapper = parametrosMapper;
        this.reciboMapper = reciboMapper;
    }

    @Override
    public Flux<Recibo> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<Recibo> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = ReciboSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(TipoReciboSqlHelper.getColumns(tiporeciboTable, "tiporecibo"));
        columns.addAll(RepGraficaSqlHelper.getColumns(repgraficaTable, "repgrafica"));
        columns.addAll(ParametrosSqlHelper.getColumns(parametrosTable, "parametros"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(tiporeciboTable)
            .on(Column.create("tiporecibo_id", entityTable))
            .equals(Column.create("id", tiporeciboTable))
            .leftOuterJoin(repgraficaTable)
            .on(Column.create("repgrafica_id", entityTable))
            .equals(Column.create("id", repgraficaTable))
            .leftOuterJoin(parametrosTable)
            .on(Column.create("parametros_id", entityTable))
            .equals(Column.create("id", parametrosTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, Recibo.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<Recibo> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<Recibo> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private Recibo process(Row row, RowMetadata metadata) {
        Recibo entity = reciboMapper.apply(row, "e");
        entity.setTiporecibo(tiporeciboMapper.apply(row, "tiporecibo"));
        entity.setRepgrafica(repgraficaMapper.apply(row, "repgrafica"));
        entity.setParametros(parametrosMapper.apply(row, "parametros"));
        return entity;
    }

    @Override
    public <S extends Recibo> Mono<S> save(S entity) {
        return super.save(entity);
    }
}
