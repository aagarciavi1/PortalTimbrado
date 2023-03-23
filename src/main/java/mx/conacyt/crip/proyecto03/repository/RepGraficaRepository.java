package mx.conacyt.crip.proyecto03.repository;

import mx.conacyt.crip.proyecto03.domain.RepGrafica;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the RepGrafica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RepGraficaRepository extends ReactiveCrudRepository<RepGrafica, Long>, RepGraficaRepositoryInternal {
    Flux<RepGrafica> findAllBy(Pageable pageable);

    @Override
    <S extends RepGrafica> Mono<S> save(S entity);

    @Override
    Flux<RepGrafica> findAll();

    @Override
    Mono<RepGrafica> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface RepGraficaRepositoryInternal {
    <S extends RepGrafica> Mono<S> save(S entity);

    Flux<RepGrafica> findAllBy(Pageable pageable);

    Flux<RepGrafica> findAll();

    Mono<RepGrafica> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<RepGrafica> findAllBy(Pageable pageable, Criteria criteria);

}
