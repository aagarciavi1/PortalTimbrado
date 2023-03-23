package mx.conacyt.crip.proyecto03.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mx.conacyt.crip.proyecto03.domain.RegimenFis;
import mx.conacyt.crip.proyecto03.repository.RegimenFisRepository;
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
 * REST controller for managing {@link mx.conacyt.crip.proyecto03.domain.RegimenFis}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RegimenFisResource {

    private final Logger log = LoggerFactory.getLogger(RegimenFisResource.class);

    private static final String ENTITY_NAME = "regimenFis";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegimenFisRepository regimenFisRepository;

    public RegimenFisResource(RegimenFisRepository regimenFisRepository) {
        this.regimenFisRepository = regimenFisRepository;
    }

    /**
     * {@code POST  /regimen-fis} : Create a new regimenFis.
     *
     * @param regimenFis the regimenFis to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new regimenFis, or with status {@code 400 (Bad Request)} if the regimenFis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/regimen-fis")
    public Mono<ResponseEntity<RegimenFis>> createRegimenFis(@RequestBody RegimenFis regimenFis) throws URISyntaxException {
        log.debug("REST request to save RegimenFis : {}", regimenFis);
        if (regimenFis.getId() != null) {
            throw new BadRequestAlertException("A new regimenFis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return regimenFisRepository
            .save(regimenFis)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/regimen-fis/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /regimen-fis/:id} : Updates an existing regimenFis.
     *
     * @param id the id of the regimenFis to save.
     * @param regimenFis the regimenFis to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated regimenFis,
     * or with status {@code 400 (Bad Request)} if the regimenFis is not valid,
     * or with status {@code 500 (Internal Server Error)} if the regimenFis couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/regimen-fis/{id}")
    public Mono<ResponseEntity<RegimenFis>> updateRegimenFis(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RegimenFis regimenFis
    ) throws URISyntaxException {
        log.debug("REST request to update RegimenFis : {}, {}", id, regimenFis);
        if (regimenFis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, regimenFis.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return regimenFisRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return regimenFisRepository
                    .save(regimenFis)
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
     * {@code PATCH  /regimen-fis/:id} : Partial updates given fields of an existing regimenFis, field will ignore if it is null
     *
     * @param id the id of the regimenFis to save.
     * @param regimenFis the regimenFis to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated regimenFis,
     * or with status {@code 400 (Bad Request)} if the regimenFis is not valid,
     * or with status {@code 404 (Not Found)} if the regimenFis is not found,
     * or with status {@code 500 (Internal Server Error)} if the regimenFis couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/regimen-fis/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<RegimenFis>> partialUpdateRegimenFis(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RegimenFis regimenFis
    ) throws URISyntaxException {
        log.debug("REST request to partial update RegimenFis partially : {}, {}", id, regimenFis);
        if (regimenFis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, regimenFis.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return regimenFisRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<RegimenFis> result = regimenFisRepository
                    .findById(regimenFis.getId())
                    .map(existingRegimenFis -> {
                        if (regimenFis.getFecha() != null) {
                            existingRegimenFis.setFecha(regimenFis.getFecha());
                        }
                        if (regimenFis.getRegimenF() != null) {
                            existingRegimenFis.setRegimenF(regimenFis.getRegimenF());
                        }
                        if (regimenFis.getEstatus() != null) {
                            existingRegimenFis.setEstatus(regimenFis.getEstatus());
                        }
                        if (regimenFis.getUsuario() != null) {
                            existingRegimenFis.setUsuario(regimenFis.getUsuario());
                        }
                        if (regimenFis.getFechaMod() != null) {
                            existingRegimenFis.setFechaMod(regimenFis.getFechaMod());
                        }

                        return existingRegimenFis;
                    })
                    .flatMap(regimenFisRepository::save);

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
     * {@code GET  /regimen-fis} : get all the regimenFis.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of regimenFis in body.
     */
    @GetMapping("/regimen-fis")
    public Mono<ResponseEntity<List<RegimenFis>>> getAllRegimenFis(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of RegimenFis");
        return regimenFisRepository
            .count()
            .zipWith(regimenFisRepository.findAllBy(pageable).collectList())
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
     * {@code GET  /regimen-fis/:id} : get the "id" regimenFis.
     *
     * @param id the id of the regimenFis to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the regimenFis, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/regimen-fis/{id}")
    public Mono<ResponseEntity<RegimenFis>> getRegimenFis(@PathVariable Long id) {
        log.debug("REST request to get RegimenFis : {}", id);
        Mono<RegimenFis> regimenFis = regimenFisRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(regimenFis);
    }

    /**
     * {@code DELETE  /regimen-fis/:id} : delete the "id" regimenFis.
     *
     * @param id the id of the regimenFis to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/regimen-fis/{id}")
    public Mono<ResponseEntity<Void>> deleteRegimenFis(@PathVariable Long id) {
        log.debug("REST request to delete RegimenFis : {}", id);
        return regimenFisRepository
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
