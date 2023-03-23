package mx.conacyt.crip.proyecto03.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mx.conacyt.crip.proyecto03.domain.RepGrafica;
import mx.conacyt.crip.proyecto03.repository.RepGraficaRepository;
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
 * REST controller for managing {@link mx.conacyt.crip.proyecto03.domain.RepGrafica}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RepGraficaResource {

    private final Logger log = LoggerFactory.getLogger(RepGraficaResource.class);

    private static final String ENTITY_NAME = "repGrafica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RepGraficaRepository repGraficaRepository;

    public RepGraficaResource(RepGraficaRepository repGraficaRepository) {
        this.repGraficaRepository = repGraficaRepository;
    }

    /**
     * {@code POST  /rep-graficas} : Create a new repGrafica.
     *
     * @param repGrafica the repGrafica to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new repGrafica, or with status {@code 400 (Bad Request)} if the repGrafica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rep-graficas")
    public Mono<ResponseEntity<RepGrafica>> createRepGrafica(@RequestBody RepGrafica repGrafica) throws URISyntaxException {
        log.debug("REST request to save RepGrafica : {}", repGrafica);
        if (repGrafica.getId() != null) {
            throw new BadRequestAlertException("A new repGrafica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return repGraficaRepository
            .save(repGrafica)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/rep-graficas/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /rep-graficas/:id} : Updates an existing repGrafica.
     *
     * @param id the id of the repGrafica to save.
     * @param repGrafica the repGrafica to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated repGrafica,
     * or with status {@code 400 (Bad Request)} if the repGrafica is not valid,
     * or with status {@code 500 (Internal Server Error)} if the repGrafica couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rep-graficas/{id}")
    public Mono<ResponseEntity<RepGrafica>> updateRepGrafica(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RepGrafica repGrafica
    ) throws URISyntaxException {
        log.debug("REST request to update RepGrafica : {}, {}", id, repGrafica);
        if (repGrafica.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, repGrafica.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return repGraficaRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return repGraficaRepository
                    .save(repGrafica)
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
     * {@code PATCH  /rep-graficas/:id} : Partial updates given fields of an existing repGrafica, field will ignore if it is null
     *
     * @param id the id of the repGrafica to save.
     * @param repGrafica the repGrafica to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated repGrafica,
     * or with status {@code 400 (Bad Request)} if the repGrafica is not valid,
     * or with status {@code 404 (Not Found)} if the repGrafica is not found,
     * or with status {@code 500 (Internal Server Error)} if the repGrafica couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rep-graficas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<RepGrafica>> partialUpdateRepGrafica(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RepGrafica repGrafica
    ) throws URISyntaxException {
        log.debug("REST request to partial update RepGrafica partially : {}, {}", id, repGrafica);
        if (repGrafica.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, repGrafica.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return repGraficaRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<RepGrafica> result = repGraficaRepository
                    .findById(repGrafica.getId())
                    .map(existingRepGrafica -> {
                        if (repGrafica.getFecha() != null) {
                            existingRepGrafica.setFecha(repGrafica.getFecha());
                        }
                        if (repGrafica.getRepGrafica() != null) {
                            existingRepGrafica.setRepGrafica(repGrafica.getRepGrafica());
                        }
                        if (repGrafica.getEstatus() != null) {
                            existingRepGrafica.setEstatus(repGrafica.getEstatus());
                        }
                        if (repGrafica.getUsuario() != null) {
                            existingRepGrafica.setUsuario(repGrafica.getUsuario());
                        }
                        if (repGrafica.getFechaMod() != null) {
                            existingRepGrafica.setFechaMod(repGrafica.getFechaMod());
                        }

                        return existingRepGrafica;
                    })
                    .flatMap(repGraficaRepository::save);

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
     * {@code GET  /rep-graficas} : get all the repGraficas.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of repGraficas in body.
     */
    @GetMapping("/rep-graficas")
    public Mono<ResponseEntity<List<RepGrafica>>> getAllRepGraficas(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of RepGraficas");
        return repGraficaRepository
            .count()
            .zipWith(repGraficaRepository.findAllBy(pageable).collectList())
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
     * {@code GET  /rep-graficas/:id} : get the "id" repGrafica.
     *
     * @param id the id of the repGrafica to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the repGrafica, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rep-graficas/{id}")
    public Mono<ResponseEntity<RepGrafica>> getRepGrafica(@PathVariable Long id) {
        log.debug("REST request to get RepGrafica : {}", id);
        Mono<RepGrafica> repGrafica = repGraficaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(repGrafica);
    }

    /**
     * {@code DELETE  /rep-graficas/:id} : delete the "id" repGrafica.
     *
     * @param id the id of the repGrafica to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rep-graficas/{id}")
    public Mono<ResponseEntity<Void>> deleteRepGrafica(@PathVariable Long id) {
        log.debug("REST request to delete RepGrafica : {}", id);
        return repGraficaRepository
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
