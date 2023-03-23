package mx.conacyt.crip.proyecto03.repository;

import mx.conacyt.crip.proyecto03.domain.EstatusCFDI;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the EstatusCFDI entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstatusCFDIRepository extends ReactiveCrudRepository<EstatusCFDI, Long>, EstatusCFDIRepositoryInternal {
    Flux<EstatusCFDI> findAllBy(Pageable pageable);

    @Override
    <S extends EstatusCFDI> Mono<S> save(S entity);

    @Override
    Flux<EstatusCFDI> findAll();

    @Override
    Mono<EstatusCFDI> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface EstatusCFDIRepositoryInternal {
    <S extends EstatusCFDI> Mono<S> save(S entity);

    Flux<EstatusCFDI> findAllBy(Pageable pageable);

    Flux<EstatusCFDI> findAll();

    Mono<EstatusCFDI> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<EstatusCFDI> findAllBy(Pageable pageable, Criteria criteria);

}
