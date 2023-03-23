package mx.conacyt.crip.proyecto03.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mx.conacyt.crip.proyecto03.domain.TipoRecibo;
import mx.conacyt.crip.proyecto03.repository.TipoReciboRepository;
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
 * REST controller for managing {@link mx.conacyt.crip.proyecto03.domain.TipoRecibo}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TipoReciboResource {

    private final Logger log = LoggerFactory.getLogger(TipoReciboResource.class);

    private static final String ENTITY_NAME = "tipoRecibo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoReciboRepository tipoReciboRepository;

    public TipoReciboResource(TipoReciboRepository tipoReciboRepository) {
        this.tipoReciboRepository = tipoReciboRepository;
    }

    /**
     * {@code POST  /tipo-recibos} : Create a new tipoRecibo.
     *
     * @param tipoRecibo the tipoRecibo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoRecibo, or with status {@code 400 (Bad Request)} if the tipoRecibo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-recibos")
    public Mono<ResponseEntity<TipoRecibo>> createTipoRecibo(@RequestBody TipoRecibo tipoRecibo) throws URISyntaxException {
        log.debug("REST request to save TipoRecibo : {}", tipoRecibo);
        if (tipoRecibo.getId() != null) {
            throw new BadRequestAlertException("A new tipoRecibo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return tipoReciboRepository
            .save(tipoRecibo)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/tipo-recibos/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /tipo-recibos/:id} : Updates an existing tipoRecibo.
     *
     * @param id the id of the tipoRecibo to save.
     * @param tipoRecibo the tipoRecibo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoRecibo,
     * or with status {@code 400 (Bad Request)} if the tipoRecibo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoRecibo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-recibos/{id}")
    public Mono<ResponseEntity<TipoRecibo>> updateTipoRecibo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TipoRecibo tipoRecibo
    ) throws URISyntaxException {
        log.debug("REST request to update TipoRecibo : {}, {}", id, tipoRecibo);
        if (tipoRecibo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoRecibo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return tipoReciboRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return tipoReciboRepository
                    .save(tipoRecibo)
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
     * {@code PATCH  /tipo-recibos/:id} : Partial updates given fields of an existing tipoRecibo, field will ignore if it is null
     *
     * @param id the id of the tipoRecibo to save.
     * @param tipoRecibo the tipoRecibo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoRecibo,
     * or with status {@code 400 (Bad Request)} if the tipoRecibo is not valid,
     * or with status {@code 404 (Not Found)} if the tipoRecibo is not found,
     * or with status {@code 500 (Internal Server Error)} if the tipoRecibo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tipo-recibos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<TipoRecibo>> partialUpdateTipoRecibo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TipoRecibo tipoRecibo
    ) throws URISyntaxException {
        log.debug("REST request to partial update TipoRecibo partially : {}, {}", id, tipoRecibo);
        if (tipoRecibo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoRecibo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return tipoReciboRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<TipoRecibo> result = tipoReciboRepository
                    .findById(tipoRecibo.getId())
                    .map(existingTipoRecibo -> {
                        if (tipoRecibo.getFecha() != null) {
                            existingTipoRecibo.setFecha(tipoRecibo.getFecha());
                        }
                        if (tipoRecibo.getTipoRecibo() != null) {
                            existingTipoRecibo.setTipoRecibo(tipoRecibo.getTipoRecibo());
                        }
                        if (tipoRecibo.getClave() != null) {
                            existingTipoRecibo.setClave(tipoRecibo.getClave());
                        }
                        if (tipoRecibo.getEstatus() != null) {
                            existingTipoRecibo.setEstatus(tipoRecibo.getEstatus());
                        }
                        if (tipoRecibo.getUsuario() != null) {
                            existingTipoRecibo.setUsuario(tipoRecibo.getUsuario());
                        }
                        if (tipoRecibo.getFechaMod() != null) {
                            existingTipoRecibo.setFechaMod(tipoRecibo.getFechaMod());
                        }

                        return existingTipoRecibo;
                    })
                    .flatMap(tipoReciboRepository::save);

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
     * {@code GET  /tipo-recibos} : get all the tipoRecibos.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoRecibos in body.
     */
    @GetMapping("/tipo-recibos")
    public Mono<ResponseEntity<List<TipoRecibo>>> getAllTipoRecibos(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of TipoRecibos");
        return tipoReciboRepository
            .count()
            .zipWith(tipoReciboRepository.findAllBy(pageable).collectList())
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
     * {@code GET  /tipo-recibos/:id} : get the "id" tipoRecibo.
     *
     * @param id the id of the tipoRecibo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoRecibo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-recibos/{id}")
    public Mono<ResponseEntity<TipoRecibo>> getTipoRecibo(@PathVariable Long id) {
        log.debug("REST request to get TipoRecibo : {}", id);
        Mono<TipoRecibo> tipoRecibo = tipoReciboRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tipoRecibo);
    }

    /**
     * {@code DELETE  /tipo-recibos/:id} : delete the "id" tipoRecibo.
     *
     * @param id the id of the tipoRecibo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-recibos/{id}")
    public Mono<ResponseEntity<Void>> deleteTipoRecibo(@PathVariable Long id) {
        log.debug("REST request to delete TipoRecibo : {}", id);
        return tipoReciboRepository
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
