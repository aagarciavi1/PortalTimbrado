package mx.conacyt.crip.proyecto03.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import mx.conacyt.crip.proyecto03.domain.FTP;
import mx.conacyt.crip.proyecto03.repository.FTPRepository;
import mx.conacyt.crip.proyecto03.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link mx.conacyt.crip.proyecto03.domain.FTP}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FTPResource {

    private final Logger log = LoggerFactory.getLogger(FTPResource.class);

    private static final String ENTITY_NAME = "fTP";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FTPRepository fTPRepository;

    public FTPResource(FTPRepository fTPRepository) {
        this.fTPRepository = fTPRepository;
    }

    /**
     * {@code POST  /ftps} : Create a new fTP.
     *
     * @param fTP the fTP to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fTP, or with status {@code 400 (Bad Request)} if the fTP has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ftps")
    public Mono<ResponseEntity<FTP>> createFTP(@Valid @RequestBody FTP fTP) throws URISyntaxException {
        log.debug("REST request to save FTP : {}", fTP);
        if (fTP.getId() != null) {
            throw new BadRequestAlertException("A new fTP cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return fTPRepository
            .save(fTP)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/ftps/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /ftps/:id} : Updates an existing fTP.
     *
     * @param id the id of the fTP to save.
     * @param fTP the fTP to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fTP,
     * or with status {@code 400 (Bad Request)} if the fTP is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fTP couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ftps/{id}")
    public Mono<ResponseEntity<FTP>> updateFTP(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody FTP fTP)
        throws URISyntaxException {
        log.debug("REST request to update FTP : {}, {}", id, fTP);
        if (fTP.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fTP.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return fTPRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return fTPRepository
                    .save(fTP)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /ftps/:id} : Partial updates given fields of an existing fTP, field will ignore if it is null
     *
     * @param id the id of the fTP to save.
     * @param fTP the fTP to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fTP,
     * or with status {@code 400 (Bad Request)} if the fTP is not valid,
     * or with status {@code 404 (Not Found)} if the fTP is not found,
     * or with status {@code 500 (Internal Server Error)} if the fTP couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ftps/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<FTP>> partialUpdateFTP(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FTP fTP
    ) throws URISyntaxException {
        log.debug("REST request to partial update FTP partially : {}, {}", id, fTP);
        if (fTP.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fTP.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return fTPRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<FTP> result = fTPRepository
                    .findById(fTP.getId())
                    .map(existingFTP -> {
                        if (fTP.getFecha() != null) {
                            existingFTP.setFecha(fTP.getFecha());
                        }
                        if (fTP.getIdInstit() != null) {
                            existingFTP.setIdInstit(fTP.getIdInstit());
                        }
                        if (fTP.getNivel() != null) {
                            existingFTP.setNivel(fTP.getNivel());
                        }
                        if (fTP.getClave() != null) {
                            existingFTP.setClave(fTP.getClave());
                        }
                        if (fTP.getCarpetaFTP() != null) {
                            existingFTP.setCarpetaFTP(fTP.getCarpetaFTP());
                        }
                        if (fTP.getSubcarpetaFTP() != null) {
                            existingFTP.setSubcarpetaFTP(fTP.getSubcarpetaFTP());
                        }
                        if (fTP.getDescripcionFTP() != null) {
                            existingFTP.setDescripcionFTP(fTP.getDescripcionFTP());
                        }
                        if (fTP.getIpFTP() != null) {
                            existingFTP.setIpFTP(fTP.getIpFTP());
                        }
                        if (fTP.getPuerto() != null) {
                            existingFTP.setPuerto(fTP.getPuerto());
                        }
                        if (fTP.getUsuarioFTP() != null) {
                            existingFTP.setUsuarioFTP(fTP.getUsuarioFTP());
                        }
                        if (fTP.getContrasena() != null) {
                            existingFTP.setContrasena(fTP.getContrasena());
                        }
                        if (fTP.getUsuario() != null) {
                            existingFTP.setUsuario(fTP.getUsuario());
                        }
                        if (fTP.getFechaMod() != null) {
                            existingFTP.setFechaMod(fTP.getFechaMod());
                        }

                        return existingFTP;
                    })
                    .flatMap(fTPRepository::save);

                return result
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(res ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, res.getId().toString()))
                            .body(res)
                    );
            });
    }

    /**
     * {@code GET  /ftps} : get all the fTPS.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fTPS in body.
     */
    @GetMapping("/ftps")
    public Mono<ResponseEntity<List<FTP>>> getAllFTPS(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of FTPS");
        return fTPRepository
            .count()
            .zipWith(fTPRepository.findAllBy(pageable).collectList())
            .map(countWithEntities ->
                ResponseEntity
                    .ok()
                    .headers(
                        PaginationUtil.generatePaginationHttpHeaders(
                            UriComponentsBuilder.fromHttpRequest(request),
                            new PageImpl<>(countWithEntities.getT2(), pageable, countWithEntities.getT1())
                        )
                    )
                    .body(countWithEntities.getT2())
            );
    }

    /**
     * {@code GET  /ftps/:id} : get the "id" fTP.
     *
     * @param id the id of the fTP to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fTP, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ftps/{id}")
    public Mono<ResponseEntity<FTP>> getFTP(@PathVariable Long id) {
        log.debug("REST request to get FTP : {}", id);
        Mono<FTP> fTP = fTPRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(fTP);
    }

    /**
     * {@code DELETE  /ftps/:id} : delete the "id" fTP.
     *
     * @param id the id of the fTP to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ftps/{id}")
    public Mono<ResponseEntity<Void>> deleteFTP(@PathVariable Long id) {
        log.debug("REST request to delete FTP : {}", id);
        return fTPRepository
            .deleteById(id)
            .then(
                Mono.just(
                    ResponseEntity
                        .noContent()
                        .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                        .build()
                )
            );
    }
}
