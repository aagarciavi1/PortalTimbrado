package mx.conacyt.crip.proyecto03.repository;

import mx.conacyt.crip.proyecto03.domain.RegimenFis;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the RegimenFis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegimenFisRepository extends ReactiveCrudRepository<RegimenFis, Long>, RegimenFisRepositoryInternal {
    Flux<RegimenFis> findAllBy(Pageable pageable);

    @Override
    <S extends RegimenFis> Mono<S> save(S entity);

    @Override
    Flux<RegimenFis> findAll();

    @Override
    Mono<RegimenFis> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface RegimenFisRepositoryInternal {
    <S extends RegimenFis> Mono<S> save(S entity);

    Flux<RegimenFis> findAllBy(Pageable pageable);

    Flux<RegimenFis> findAll();

    Mono<RegimenFis> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<RegimenFis> findAllBy(Pageable pageable, Criteria criteria);

}
