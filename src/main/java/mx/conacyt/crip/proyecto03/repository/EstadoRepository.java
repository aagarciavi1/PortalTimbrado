package mx.conacyt.crip.proyecto03.repository;

import mx.conacyt.crip.proyecto03.domain.Estado;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Estado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstadoRepository extends ReactiveCrudRepository<Estado, Long>, EstadoRepositoryInternal {
    Flux<Estado> findAllBy(Pageable pageable);

    @Override
    <S extends Estado> Mono<S> save(S entity);

    @Override
    Flux<Estado> findAll();

    @Override
    Mono<Estado> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface EstadoRepositoryInternal {
    <S extends Estado> Mono<S> save(S entity);

    Flux<Estado> findAllBy(Pageable pageable);

    Flux<Estado> findAll();

    Mono<Estado> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Estado> findAllBy(Pageable pageable, Criteria criteria);

}
