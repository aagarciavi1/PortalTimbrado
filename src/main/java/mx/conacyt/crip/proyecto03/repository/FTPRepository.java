package mx.conacyt.crip.proyecto03.repository;

import mx.conacyt.crip.proyecto03.domain.FTP;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the FTP entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FTPRepository extends ReactiveCrudRepository<FTP, Long>, FTPRepositoryInternal {
    Flux<FTP> findAllBy(Pageable pageable);

    @Query("SELECT * FROM ftp entity WHERE entity.registinstit_id = :id")
    Flux<FTP> findByRegistinstit(Long id);

    @Query("SELECT * FROM ftp entity WHERE entity.registinstit_id IS NULL")
    Flux<FTP> findAllWhereRegistinstitIsNull();

    @Query("SELECT * FROM ftp entity WHERE entity.tipocfdi_id = :id")
    Flux<FTP> findByTipocfdi(Long id);

    @Query("SELECT * FROM ftp entity WHERE entity.tipocfdi_id IS NULL")
    Flux<FTP> findAllWhereTipocfdiIsNull();

    @Query("SELECT * FROM ftp entity WHERE entity.tiporecibo_id = :id")
    Flux<FTP> findByTiporecibo(Long id);

    @Query("SELECT * FROM ftp entity WHERE entity.tiporecibo_id IS NULL")
    Flux<FTP> findAllWhereTiporeciboIsNull();

    @Override
    <S extends FTP> Mono<S> save(S entity);

    @Override
    Flux<FTP> findAll();

    @Override
    Mono<FTP> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface FTPRepositoryInternal {
    <S extends FTP> Mono<S> save(S entity);

    Flux<FTP> findAllBy(Pageable pageable);

    Flux<FTP> findAll();

    Mono<FTP> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<FTP> findAllBy(Pageable pageable, Criteria criteria);

}
