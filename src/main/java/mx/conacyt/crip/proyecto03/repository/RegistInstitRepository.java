package mx.conacyt.crip.proyecto03.repository;

import mx.conacyt.crip.proyecto03.domain.RegistInstit;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the RegistInstit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegistInstitRepository extends ReactiveCrudRepository<RegistInstit, Long>, RegistInstitRepositoryInternal {
    Flux<RegistInstit> findAllBy(Pageable pageable);

    @Query("SELECT * FROM regist_instit entity WHERE entity.regimenfis_id = :id")
    Flux<RegistInstit> findByRegimenfis(Long id);

    @Query("SELECT * FROM regist_instit entity WHERE entity.regimenfis_id IS NULL")
    Flux<RegistInstit> findAllWhereRegimenfisIsNull();

    @Query("SELECT * FROM regist_instit entity WHERE entity.estado_id = :id")
    Flux<RegistInstit> findByEstado(Long id);

    @Query("SELECT * FROM regist_instit entity WHERE entity.estado_id IS NULL")
    Flux<RegistInstit> findAllWhereEstadoIsNull();

    @Query("SELECT * FROM regist_instit entity WHERE entity.registinstit_id = :id")
    Flux<RegistInstit> findByRegistinstit(Long id);

    @Query("SELECT * FROM regist_instit entity WHERE entity.registinstit_id IS NULL")
    Flux<RegistInstit> findAllWhereRegistinstitIsNull();

    @Query("SELECT * FROM regist_instit entity WHERE entity.registinstiti_id = :id")
    Flux<RegistInstit> findByRegistinstiti(Long id);

    @Query("SELECT * FROM regist_instit entity WHERE entity.registinstiti_id IS NULL")
    Flux<RegistInstit> findAllWhereRegistinstitiIsNull();

    @Override
    <S extends RegistInstit> Mono<S> save(S entity);

    @Override
    Flux<RegistInstit> findAll();

    @Override
    Mono<RegistInstit> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface RegistInstitRepositoryInternal {
    <S extends RegistInstit> Mono<S> save(S entity);

    Flux<RegistInstit> findAllBy(Pageable pageable);

    Flux<RegistInstit> findAll();

    Mono<RegistInstit> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<RegistInstit> findAllBy(Pageable pageable, Criteria criteria);

}
