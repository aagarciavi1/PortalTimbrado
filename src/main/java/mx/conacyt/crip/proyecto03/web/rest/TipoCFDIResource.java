package mx.conacyt.crip.proyecto03.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mx.conacyt.crip.proyecto03.domain.TipoCFDI;
import mx.conacyt.crip.proyecto03.repository.TipoCFDIRepository;
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
 * REST controller for managing {@link mx.conacyt.crip.proyecto03.domain.TipoCFDI}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TipoCFDIResource {

    private final Logger log = LoggerFactory.getLogger(TipoCFDIResource.class);

    private static final String ENTITY_NAME = "tipoCFDI";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoCFDIRepository tipoCFDIRepository;

    public TipoCFDIResource(TipoCFDIRepository tipoCFDIRepository) {
        this.tipoCFDIRepository = tipoCFDIRepository;
    }

    /**
     * {@code POST  /tipo-cfdis} : Create a new tipoCFDI.
     *
     * @param tipoCFDI the tipoCFDI to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoCFDI, or with status {@code 400 (Bad Request)} if the tipoCFDI has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-cfdis")
    public Mono<ResponseEntity<TipoCFDI>> createTipoCFDI(@RequestBody TipoCFDI tipoCFDI) throws URISyntaxException {
        log.debug("REST request to save TipoCFDI : {}", tipoCFDI);
        if (tipoCFDI.getId() != null) {
            throw new BadRequestAlertException("A new tipoCFDI cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return tipoCFDIRepository
            .save(tipoCFDI)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/tipo-cfdis/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /tipo-cfdis/:id} : Updates an existing tipoCFDI.
     *
     * @param id the id of the tipoCFDI to save.
     * @param tipoCFDI the tipoCFDI to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoCFDI,
     * or with status {@code 400 (Bad Request)} if the tipoCFDI is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoCFDI couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-cfdis/{id}")
    public Mono<ResponseEntity<TipoCFDI>> updateTipoCFDI(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TipoCFDI tipoCFDI
    ) throws URISyntaxException {
        log.debug("REST request to update TipoCFDI : {}, {}", id, tipoCFDI);
        if (tipoCFDI.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoCFDI.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return tipoCFDIRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return tipoCFDIRepository
                    .save(tipoCFDI)
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
     * {@code PATCH  /tipo-cfdis/:id} : Partial updates given fields of an existing tipoCFDI, field will ignore if it is null
     *
     * @param id the id of the tipoCFDI to save.
     * @param tipoCFDI the tipoCFDI to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoCFDI,
     * or with status {@code 400 (Bad Request)} if the tipoCFDI is not valid,
     * or with status {@code 404 (Not Found)} if the tipoCFDI is not found,
     * or with status {@code 500 (Internal Server Error)} if the tipoCFDI couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tipo-cfdis/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<TipoCFDI>> partialUpdateTipoCFDI(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TipoCFDI tipoCFDI
    ) throws URISyntaxException {
        log.debug("REST request to partial update TipoCFDI partially : {}, {}", id, tipoCFDI);
        if (tipoCFDI.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoCFDI.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return tipoCFDIRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<TipoCFDI> result = tipoCFDIRepository
                    .findById(tipoCFDI.getId())
                    .map(existingTipoCFDI -> {
                        if (tipoCFDI.getFecha() != null) {
                            existingTipoCFDI.setFecha(tipoCFDI.getFecha());
                        }
                        if (tipoCFDI.getTipoCFDI() != null) {
                            existingTipoCFDI.setTipoCFDI(tipoCFDI.getTipoCFDI());
                        }
                        if (tipoCFDI.getEstatus() != null) {
                            existingTipoCFDI.setEstatus(tipoCFDI.getEstatus());
                        }
                        if (tipoCFDI.getUsuario() != null) {
                            existingTipoCFDI.setUsuario(tipoCFDI.getUsuario());
                        }
                        if (tipoCFDI.getFechaMod() != null) {
                            existingTipoCFDI.setFechaMod(tipoCFDI.getFechaMod());
                        }

                        return existingTipoCFDI;
                    })
                    .flatMap(tipoCFDIRepository::save);

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
     * {@code GET  /tipo-cfdis} : get all the tipoCFDIS.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoCFDIS in body.
     */
    @GetMapping("/tipo-cfdis")
    public Mono<ResponseEntity<List<TipoCFDI>>> getAllTipoCFDIS(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of TipoCFDIS");
        return tipoCFDIRepository
            .count()
            .zipWith(tipoCFDIRepository.findAllBy(pageable).collectList())
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
     * {@code GET  /tipo-cfdis/:id} : get the "id" tipoCFDI.
     *
     * @param id the id of the tipoCFDI to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoCFDI, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-cfdis/{id}")
    public Mono<ResponseEntity<TipoCFDI>> getTipoCFDI(@PathVariable Long id) {
        log.debug("REST request to get TipoCFDI : {}", id);
        Mono<TipoCFDI> tipoCFDI = tipoCFDIRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tipoCFDI);
    }

    /**
     * {@code DELETE  /tipo-cfdis/:id} : delete the "id" tipoCFDI.
     *
     * @param id the id of the tipoCFDI to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-cfdis/{id}")
    public Mono<ResponseEntity<Void>> deleteTipoCFDI(@PathVariable Long id) {
        log.debug("REST request to delete TipoCFDI : {}", id);
        return tipoCFDIRepository
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
