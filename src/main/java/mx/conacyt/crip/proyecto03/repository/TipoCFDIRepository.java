package mx.conacyt.crip.proyecto03.repository;

import mx.conacyt.crip.proyecto03.domain.TipoCFDI;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the TipoCFDI entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoCFDIRepository extends ReactiveCrudRepository<TipoCFDI, Long>, TipoCFDIRepositoryInternal {
    Flux<TipoCFDI> findAllBy(Pageable pageable);

    @Override
    <S extends TipoCFDI> Mono<S> save(S entity);

    @Override
    Flux<TipoCFDI> findAll();

    @Override
    Mono<TipoCFDI> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface TipoCFDIRepositoryInternal {
    <S extends TipoCFDI> Mono<S> save(S entity);

    Flux<TipoCFDI> findAllBy(Pageable pageable);

    Flux<TipoCFDI> findAll();

    Mono<TipoCFDI> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<TipoCFDI> findAllBy(Pageable pageable, Criteria criteria);

}
