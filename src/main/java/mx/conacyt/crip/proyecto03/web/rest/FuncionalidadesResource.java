package mx.conacyt.crip.proyecto03.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import mx.conacyt.crip.proyecto03.domain.Funcionalidades;
import mx.conacyt.crip.proyecto03.repository.FuncionalidadesRepository;
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
 * REST controller for managing {@link mx.conacyt.crip.proyecto03.domain.Funcionalidades}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FuncionalidadesResource {

    private final Logger log = LoggerFactory.getLogger(FuncionalidadesResource.class);

    private static final String ENTITY_NAME = "funcionalidades";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FuncionalidadesRepository funcionalidadesRepository;

    public FuncionalidadesResource(FuncionalidadesRepository funcionalidadesRepository) {
        this.funcionalidadesRepository = funcionalidadesRepository;
    }

    /**
     * {@code POST  /funcionalidades} : Create a new funcionalidades.
     *
     * @param funcionalidades the funcionalidades to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new funcionalidades, or with status {@code 400 (Bad Request)} if the funcionalidades has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/funcionalidades")
    public Mono<ResponseEntity<Funcionalidades>> createFuncionalidades(@Valid @RequestBody Funcionalidades funcionalidades)
        throws URISyntaxException {
        log.debug("REST request to save Funcionalidades : {}", funcionalidades);
        if (funcionalidades.getId() != null) {
            throw new BadRequestAlertException("A new funcionalidades cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return funcionalidadesRepository
            .save(funcionalidades)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/funcionalidades/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /funcionalidades/:id} : Updates an existing funcionalidades.
     *
     * @param id the id of the funcionalidades to save.
     * @param funcionalidades the funcionalidades to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated funcionalidades,
     * or with status {@code 400 (Bad Request)} if the funcionalidades is not valid,
     * or with status {@code 500 (Internal Server Error)} if the funcionalidades couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/funcionalidades/{id}")
    public Mono<ResponseEntity<Funcionalidades>> updateFuncionalidades(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Funcionalidades funcionalidades
    ) throws URISyntaxException {
        log.debug("REST request to update Funcionalidades : {}, {}", id, funcionalidades);
        if (funcionalidades.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, funcionalidades.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return funcionalidadesRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return funcionalidadesRepository
                    .save(funcionalidades)
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
     * {@code PATCH  /funcionalidades/:id} : Partial updates given fields of an existing funcionalidades, field will ignore if it is null
     *
     * @param id the id of the funcionalidades to save.
     * @param funcionalidades the funcionalidades to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated funcionalidades,
     * or with status {@code 400 (Bad Request)} if the funcionalidades is not valid,
     * or with status {@code 404 (Not Found)} if the funcionalidades is not found,
     * or with status {@code 500 (Internal Server Error)} if the funcionalidades couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/funcionalidades/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<Funcionalidades>> partialUpdateFuncionalidades(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Funcionalidades funcionalidades
    ) throws URISyntaxException {
        log.debug("REST request to partial update Funcionalidades partially : {}, {}", id, funcionalidades);
        if (funcionalidades.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, funcionalidades.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return funcionalidadesRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<Funcionalidades> result = funcionalidadesRepository
                    .findById(funcionalidades.getId())
                    .map(existingFuncionalidades -> {
                        if (funcionalidades.getFecha() != null) {
                            existingFuncionalidades.setFecha(funcionalidades.getFecha());
                        }
                        if (funcionalidades.getPerfil() != null) {
                            existingFuncionalidades.setPerfil(funcionalidades.getPerfil());
                        }
                        if (funcionalidades.getFuncionalidad() != null) {
                            existingFuncionalidades.setFuncionalidad(funcionalidades.getFuncionalidad());
                        }
                        if (funcionalidades.getAlta() != null) {
                            existingFuncionalidades.setAlta(funcionalidades.getAlta());
                        }
                        if (funcionalidades.getEditar() != null) {
                            existingFuncionalidades.setEditar(funcionalidades.getEditar());
                        }
                        if (funcionalidades.getActivarInact() != null) {
                            existingFuncionalidades.setActivarInact(funcionalidades.getActivarInact());
                        }
                        if (funcionalidades.getConsulta() != null) {
                            existingFuncionalidades.setConsulta(funcionalidades.getConsulta());
                        }
                        if (funcionalidades.getUsuario() != null) {
                            existingFuncionalidades.setUsuario(funcionalidades.getUsuario());
                        }
                        if (funcionalidades.getFechaMod() != null) {
                            existingFuncionalidades.setFechaMod(funcionalidades.getFechaMod());
                        }

                        return existingFuncionalidades;
                    })
                    .flatMap(funcionalidadesRepository::save);

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
     * {@code GET  /funcionalidades} : get all the funcionalidades.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of funcionalidades in body.
     */
    @GetMapping("/funcionalidades")
    public Mono<ResponseEntity<List<Funcionalidades>>> getAllFuncionalidades(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of Funcionalidades");
        return funcionalidadesRepository
            .count()
            .zipWith(funcionalidadesRepository.findAllBy(pageable).collectList())
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
     * {@code GET  /funcionalidades/:id} : get the "id" funcionalidades.
     *
     * @param id the id of the funcionalidades to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the funcionalidades, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/funcionalidades/{id}")
    public Mono<ResponseEntity<Funcionalidades>> getFuncionalidades(@PathVariable Long id) {
        log.debug("REST request to get Funcionalidades : {}", id);
        Mono<Funcionalidades> funcionalidades = funcionalidadesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(funcionalidades);
    }

    /**
     * {@code DELETE  /funcionalidades/:id} : delete the "id" funcionalidades.
     *
     * @param id the id of the funcionalidades to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/funcionalidades/{id}")
    public Mono<ResponseEntity<Void>> deleteFuncionalidades(@PathVariable Long id) {
        log.debug("REST request to delete Funcionalidades : {}", id);
        return funcionalidadesRepository
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
