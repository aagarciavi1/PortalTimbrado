package mx.conacyt.crip.proyecto03.repository;

import mx.conacyt.crip.proyecto03.domain.Perfiles;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Perfiles entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PerfilesRepository extends ReactiveCrudRepository<Perfiles, Long>, PerfilesRepositoryInternal {
    Flux<Perfiles> findAllBy(Pageable pageable);

    @Query("SELECT * FROM perfiles entity WHERE entity.funcionalidades_id = :id")
    Flux<Perfiles> findByFuncionalidades(Long id);

    @Query("SELECT * FROM perfiles entity WHERE entity.funcionalidades_id IS NULL")
    Flux<Perfiles> findAllWhereFuncionalidadesIsNull();

    @Override
    <S extends Perfiles> Mono<S> save(S entity);

    @Override
    Flux<Perfiles> findAll();

    @Override
    Mono<Perfiles> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface PerfilesRepositoryInternal {
    <S extends Perfiles> Mono<S> save(S entity);

    Flux<Perfiles> findAllBy(Pageable pageable);

    Flux<Perfiles> findAll();

    Mono<Perfiles> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Perfiles> findAllBy(Pageable pageable, Criteria criteria);

}
