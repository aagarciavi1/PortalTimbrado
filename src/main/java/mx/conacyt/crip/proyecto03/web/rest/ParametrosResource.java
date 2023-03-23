package mx.conacyt.crip.proyecto03.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import mx.conacyt.crip.proyecto03.domain.Parametros;
import mx.conacyt.crip.proyecto03.repository.ParametrosRepository;
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
 * REST controller for managing {@link mx.conacyt.crip.proyecto03.domain.Parametros}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ParametrosResource {

    private final Logger log = LoggerFactory.getLogger(ParametrosResource.class);

    private static final String ENTITY_NAME = "parametros";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParametrosRepository parametrosRepository;

    public ParametrosResource(ParametrosRepository parametrosRepository) {
        this.parametrosRepository = parametrosRepository;
    }

    /**
     * {@code POST  /parametros} : Create a new parametros.
     *
     * @param parametros the parametros to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parametros, or with status {@code 400 (Bad Request)} if the parametros has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/parametros")
    public Mono<ResponseEntity<Parametros>> createParametros(@Valid @RequestBody Parametros parametros) throws URISyntaxException {
        log.debug("REST request to save Parametros : {}", parametros);
        if (parametros.getId() != null) {
            throw new BadRequestAlertException("A new parametros cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return parametrosRepository
            .save(parametros)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/parametros/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /parametros/:id} : Updates an existing parametros.
     *
     * @param id the id of the parametros to save.
     * @param parametros the parametros to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parametros,
     * or with status {@code 400 (Bad Request)} if the parametros is not valid,
     * or with status {@code 500 (Internal Server Error)} if the parametros couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/parametros/{id}")
    public Mono<ResponseEntity<Parametros>> updateParametros(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Parametros parametros
    ) throws URISyntaxException {
        log.debug("REST request to update Parametros : {}, {}", id, parametros);
        if (parametros.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parametros.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return parametrosRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return parametrosRepository
                    .save(parametros)
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
     * {@code PATCH  /parametros/:id} : Partial updates given fields of an existing parametros, field will ignore if it is null
     *
     * @param id the id of the parametros to save.
     * @param parametros the parametros to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parametros,
     * or with status {@code 400 (Bad Request)} if the parametros is not valid,
     * or with status {@code 404 (Not Found)} if the parametros is not found,
     * or with status {@code 500 (Internal Server Error)} if the parametros couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/parametros/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<Parametros>> partialUpdateParametros(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Parametros parametros
    ) throws URISyntaxException {
        log.debug("REST request to partial update Parametros partially : {}, {}", id, parametros);
        if (parametros.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parametros.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return parametrosRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<Parametros> result = parametrosRepository
                    .findById(parametros.getId())
                    .map(existingParametros -> {
                        if (parametros.getFecha() != null) {
                            existingParametros.setFecha(parametros.getFecha());
                        }
                        if (parametros.getIdInstit() != null) {
                            existingParametros.setIdInstit(parametros.getIdInstit());
                        }
                        if (parametros.getNivel() != null) {
                            existingParametros.setNivel(parametros.getNivel());
                        }
                        if (parametros.getUsuario() != null) {
                            existingParametros.setUsuario(parametros.getUsuario());
                        }
                        if (parametros.getFechaMod() != null) {
                            existingParametros.setFechaMod(parametros.getFechaMod());
                        }

                        return existingParametros;
                    })
                    .flatMap(parametrosRepository::save);

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
     * {@code GET  /parametros} : get all the parametros.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parametros in body.
     */
    @GetMapping("/parametros")
    public Mono<ResponseEntity<List<Parametros>>> getAllParametros(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of Parametros");
        return parametrosRepository
            .count()
            .zipWith(parametrosRepository.findAllBy(pageable).collectList())
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
     * {@code GET  /parametros/:id} : get the "id" parametros.
     *
     * @param id the id of the parametros to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parametros, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/parametros/{id}")
    public Mono<ResponseEntity<Parametros>> getParametros(@PathVariable Long id) {
        log.debug("REST request to get Parametros : {}", id);
        Mono<Parametros> parametros = parametrosRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(parametros);
    }

    /**
     * {@code DELETE  /parametros/:id} : delete the "id" parametros.
     *
     * @param id the id of the parametros to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/parametros/{id}")
    public Mono<ResponseEntity<Void>> deleteParametros(@PathVariable Long id) {
        log.debug("REST request to delete Parametros : {}", id);
        return parametrosRepository
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
