package mx.conacyt.crip.proyecto03.repository;

import mx.conacyt.crip.proyecto03.domain.Recibo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Recibo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReciboRepository extends ReactiveCrudRepository<Recibo, Long>, ReciboRepositoryInternal {
    Flux<Recibo> findAllBy(Pageable pageable);

    @Query("SELECT * FROM recibo entity WHERE entity.tiporecibo_id = :id")
    Flux<Recibo> findByTiporecibo(Long id);

    @Query("SELECT * FROM recibo entity WHERE entity.tiporecibo_id IS NULL")
    Flux<Recibo> findAllWhereTiporeciboIsNull();

    @Query("SELECT * FROM recibo entity WHERE entity.repgrafica_id = :id")
    Flux<Recibo> findByRepgrafica(Long id);

    @Query("SELECT * FROM recibo entity WHERE entity.repgrafica_id IS NULL")
    Flux<Recibo> findAllWhereRepgraficaIsNull();

    @Query("SELECT * FROM recibo entity WHERE entity.parametros_id = :id")
    Flux<Recibo> findByParametros(Long id);

    @Query("SELECT * FROM recibo entity WHERE entity.parametros_id IS NULL")
    Flux<Recibo> findAllWhereParametrosIsNull();

    @Override
    <S extends Recibo> Mono<S> save(S entity);

    @Override
    Flux<Recibo> findAll();

    @Override
    Mono<Recibo> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface ReciboRepositoryInternal {
    <S extends Recibo> Mono<S> save(S entity);

    Flux<Recibo> findAllBy(Pageable pageable);

    Flux<Recibo> findAll();

    Mono<Recibo> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Recibo> findAllBy(Pageable pageable, Criteria criteria);

}
