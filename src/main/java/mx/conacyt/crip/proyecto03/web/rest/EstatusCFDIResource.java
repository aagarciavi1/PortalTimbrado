package mx.conacyt.crip.proyecto03.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mx.conacyt.crip.proyecto03.domain.EstatusCFDI;
import mx.conacyt.crip.proyecto03.repository.EstatusCFDIRepository;
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
 * REST controller for managing {@link mx.conacyt.crip.proyecto03.domain.EstatusCFDI}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EstatusCFDIResource {

    private final Logger log = LoggerFactory.getLogger(EstatusCFDIResource.class);

    private static final String ENTITY_NAME = "estatusCFDI";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstatusCFDIRepository estatusCFDIRepository;

    public EstatusCFDIResource(EstatusCFDIRepository estatusCFDIRepository) {
        this.estatusCFDIRepository = estatusCFDIRepository;
    }

    /**
     * {@code POST  /estatus-cfdis} : Create a new estatusCFDI.
     *
     * @param estatusCFDI the estatusCFDI to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estatusCFDI, or with status {@code 400 (Bad Request)} if the estatusCFDI has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estatus-cfdis")
    public Mono<ResponseEntity<EstatusCFDI>> createEstatusCFDI(@RequestBody EstatusCFDI estatusCFDI) throws URISyntaxException {
        log.debug("REST request to save EstatusCFDI : {}", estatusCFDI);
        if (estatusCFDI.getId() != null) {
            throw new BadRequestAlertException("A new estatusCFDI cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return estatusCFDIRepository
            .save(estatusCFDI)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/estatus-cfdis/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /estatus-cfdis/:id} : Updates an existing estatusCFDI.
     *
     * @param id the id of the estatusCFDI to save.
     * @param estatusCFDI the estatusCFDI to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estatusCFDI,
     * or with status {@code 400 (Bad Request)} if the estatusCFDI is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estatusCFDI couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estatus-cfdis/{id}")
    public Mono<ResponseEntity<EstatusCFDI>> updateEstatusCFDI(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EstatusCFDI estatusCFDI
    ) throws URISyntaxException {
        log.debug("REST request to update EstatusCFDI : {}, {}", id, estatusCFDI);
        if (estatusCFDI.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estatusCFDI.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return estatusCFDIRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return estatusCFDIRepository
                    .save(estatusCFDI)
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
     * {@code PATCH  /estatus-cfdis/:id} : Partial updates given fields of an existing estatusCFDI, field will ignore if it is null
     *
     * @param id the id of the estatusCFDI to save.
     * @param estatusCFDI the estatusCFDI to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estatusCFDI,
     * or with status {@code 400 (Bad Request)} if the estatusCFDI is not valid,
     * or with status {@code 404 (Not Found)} if the estatusCFDI is not found,
     * or with status {@code 500 (Internal Server Error)} if the estatusCFDI couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/estatus-cfdis/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<EstatusCFDI>> partialUpdateEstatusCFDI(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EstatusCFDI estatusCFDI
    ) throws URISyntaxException {
        log.debug("REST request to partial update EstatusCFDI partially : {}, {}", id, estatusCFDI);
        if (estatusCFDI.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estatusCFDI.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return estatusCFDIRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<EstatusCFDI> result = estatusCFDIRepository
                    .findById(estatusCFDI.getId())
                    .map(existingEstatusCFDI -> {
                        if (estatusCFDI.getFecha() != null) {
                            existingEstatusCFDI.setFecha(estatusCFDI.getFecha());
                        }
                        if (estatusCFDI.getDescEstCFDI() != null) {
                            existingEstatusCFDI.setDescEstCFDI(estatusCFDI.getDescEstCFDI());
                        }
                        if (estatusCFDI.getEstatus() != null) {
                            existingEstatusCFDI.setEstatus(estatusCFDI.getEstatus());
                        }
                        if (estatusCFDI.getUsuario() != null) {
                            existingEstatusCFDI.setUsuario(estatusCFDI.getUsuario());
                        }
                        if (estatusCFDI.getFechaMod() != null) {
                            existingEstatusCFDI.setFechaMod(estatusCFDI.getFechaMod());
                        }

                        return existingEstatusCFDI;
                    })
                    .flatMap(estatusCFDIRepository::save);

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
     * {@code GET  /estatus-cfdis} : get all the estatusCFDIS.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estatusCFDIS in body.
     */
    @GetMapping("/estatus-cfdis")
    public Mono<ResponseEntity<List<EstatusCFDI>>> getAllEstatusCFDIS(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of EstatusCFDIS");
        return estatusCFDIRepository
            .count()
            .zipWith(estatusCFDIRepository.findAllBy(pageable).collectList())
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
     * {@code GET  /estatus-cfdis/:id} : get the "id" estatusCFDI.
     *
     * @param id the id of the estatusCFDI to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estatusCFDI, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estatus-cfdis/{id}")
    public Mono<ResponseEntity<EstatusCFDI>> getEstatusCFDI(@PathVariable Long id) {
        log.debug("REST request to get EstatusCFDI : {}", id);
        Mono<EstatusCFDI> estatusCFDI = estatusCFDIRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(estatusCFDI);
    }

    /**
     * {@code DELETE  /estatus-cfdis/:id} : delete the "id" estatusCFDI.
     *
     * @param id the id of the estatusCFDI to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estatus-cfdis/{id}")
    public Mono<ResponseEntity<Void>> deleteEstatusCFDI(@PathVariable Long id) {
        log.debug("REST request to delete EstatusCFDI : {}", id);
        return estatusCFDIRepository
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
