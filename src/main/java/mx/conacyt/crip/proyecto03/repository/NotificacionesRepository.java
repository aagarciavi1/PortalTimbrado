package mx.conacyt.crip.proyecto03.repository;

import mx.conacyt.crip.proyecto03.domain.Notificaciones;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Notificaciones entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotificacionesRepository extends ReactiveCrudRepository<Notificaciones, Long>, NotificacionesRepositoryInternal {
    Flux<Notificaciones> findAllBy(Pageable pageable);

    @Query("SELECT * FROM notificaciones entity WHERE entity.registinstit_id = :id")
    Flux<Notificaciones> findByRegistinstit(Long id);

    @Query("SELECT * FROM notificaciones entity WHERE entity.registinstit_id IS NULL")
    Flux<Notificaciones> findAllWhereRegistinstitIsNull();

    @Query("SELECT * FROM notificaciones entity WHERE entity.tipocfdi_id = :id")
    Flux<Notificaciones> findByTipocfdi(Long id);

    @Query("SELECT * FROM notificaciones entity WHERE entity.tipocfdi_id IS NULL")
    Flux<Notificaciones> findAllWhereTipocfdiIsNull();

    @Query("SELECT * FROM notificaciones entity WHERE entity.tiporecibo_id = :id")
    Flux<Notificaciones> findByTiporecibo(Long id);

    @Query("SELECT * FROM notificaciones entity WHERE entity.tiporecibo_id IS NULL")
    Flux<Notificaciones> findAllWhereTiporeciboIsNull();

    @Override
    <S extends Notificaciones> Mono<S> save(S entity);

    @Override
    Flux<Notificaciones> findAll();

    @Override
    Mono<Notificaciones> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface NotificacionesRepositoryInternal {
    <S extends Notificaciones> Mono<S> save(S entity);

    Flux<Notificaciones> findAllBy(Pageable pageable);

    Flux<Notificaciones> findAll();

    Mono<Notificaciones> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Notificaciones> findAllBy(Pageable pageable, Criteria criteria);

}
