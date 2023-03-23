package mx.conacyt.crip.proyecto03.repository;

import mx.conacyt.crip.proyecto03.domain.Usuarios;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Usuarios entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsuariosRepository extends ReactiveCrudRepository<Usuarios, Long>, UsuariosRepositoryInternal {
    Flux<Usuarios> findAllBy(Pageable pageable);

    @Query("SELECT * FROM usuarios entity WHERE entity.registinstit_id = :id")
    Flux<Usuarios> findByRegistinstit(Long id);

    @Query("SELECT * FROM usuarios entity WHERE entity.registinstit_id IS NULL")
    Flux<Usuarios> findAllWhereRegistinstitIsNull();

    @Query("SELECT * FROM usuarios entity WHERE entity.perfil_id = :id")
    Flux<Usuarios> findByPerfil(Long id);

    @Query("SELECT * FROM usuarios entity WHERE entity.perfil_id IS NULL")
    Flux<Usuarios> findAllWherePerfilIsNull();

    @Query("SELECT * FROM usuarios entity WHERE entity.tipocfdi_id = :id")
    Flux<Usuarios> findByTipocfdi(Long id);

    @Query("SELECT * FROM usuarios entity WHERE entity.tipocfdi_id IS NULL")
    Flux<Usuarios> findAllWhereTipocfdiIsNull();

    @Query("SELECT * FROM usuarios entity WHERE entity.tiporecibo_id = :id")
    Flux<Usuarios> findByTiporecibo(Long id);

    @Query("SELECT * FROM usuarios entity WHERE entity.tiporecibo_id IS NULL")
    Flux<Usuarios> findAllWhereTiporeciboIsNull();

    @Override
    <S extends Usuarios> Mono<S> save(S entity);

    @Override
    Flux<Usuarios> findAll();

    @Override
    Mono<Usuarios> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface UsuariosRepositoryInternal {
    <S extends Usuarios> Mono<S> save(S entity);

    Flux<Usuarios> findAllBy(Pageable pageable);

    Flux<Usuarios> findAll();

    Mono<Usuarios> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Usuarios> findAllBy(Pageable pageable, Criteria criteria);

}
