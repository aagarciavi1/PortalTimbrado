package mx.conacyt.crip.proyecto03.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import mx.conacyt.crip.proyecto03.domain.Notificaciones;
import mx.conacyt.crip.proyecto03.repository.NotificacionesRepository;
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
 * REST controller for managing {@link mx.conacyt.crip.proyecto03.domain.Notificaciones}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NotificacionesResource {

    private final Logger log = LoggerFactory.getLogger(NotificacionesResource.class);

    private static final String ENTITY_NAME = "notificaciones";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NotificacionesRepository notificacionesRepository;

    public NotificacionesResource(NotificacionesRepository notificacionesRepository) {
        this.notificacionesRepository = notificacionesRepository;
    }

    /**
     * {@code POST  /notificaciones} : Create a new notificaciones.
     *
     * @param notificaciones the notificaciones to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new notificaciones, or with status {@code 400 (Bad Request)} if the notificaciones has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/notificaciones")
    public Mono<ResponseEntity<Notificaciones>> createNotificaciones(@Valid @RequestBody Notificaciones notificaciones)
        throws URISyntaxException {
        log.debug("REST request to save Notificaciones : {}", notificaciones);
        if (notificaciones.getId() != null) {
            throw new BadRequestAlertException("A new notificaciones cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return notificacionesRepository
            .save(notificaciones)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/notificaciones/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /notificaciones/:id} : Updates an existing notificaciones.
     *
     * @param id the id of the notificaciones to save.
     * @param notificaciones the notificaciones to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notificaciones,
     * or with status {@code 400 (Bad Request)} if the notificaciones is not valid,
     * or with status {@code 500 (Internal Server Error)} if the notificaciones couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/notificaciones/{id}")
    public Mono<ResponseEntity<Notificaciones>> updateNotificaciones(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Notificaciones notificaciones
    ) throws URISyntaxException {
        log.debug("REST request to update Notificaciones : {}, {}", id, notificaciones);
        if (notificaciones.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, notificaciones.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return notificacionesRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return notificacionesRepository
                    .save(notificaciones)
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
     * {@code PATCH  /notificaciones/:id} : Partial updates given fields of an existing notificaciones, field will ignore if it is null
     *
     * @param id the id of the notificaciones to save.
     * @param notificaciones the notificaciones to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notificaciones,
     * or with status {@code 400 (Bad Request)} if the notificaciones is not valid,
     * or with status {@code 404 (Not Found)} if the notificaciones is not found,
     * or with status {@code 500 (Internal Server Error)} if the notificaciones couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/notificaciones/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<Notificaciones>> partialUpdateNotificaciones(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Notificaciones notificaciones
    ) throws URISyntaxException {
        log.debug("REST request to partial update Notificaciones partially : {}, {}", id, notificaciones);
        if (notificaciones.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, notificaciones.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return notificacionesRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<Notificaciones> result = notificacionesRepository
                    .findById(notificaciones.getId())
                    .map(existingNotificaciones -> {
                        if (notificaciones.getFecha() != null) {
                            existingNotificaciones.setFecha(notificaciones.getFecha());
                        }
                        if (notificaciones.getIdInstit() != null) {
                            existingNotificaciones.setIdInstit(notificaciones.getIdInstit());
                        }
                        if (notificaciones.getNivel() != null) {
                            existingNotificaciones.setNivel(notificaciones.getNivel());
                        }
                        if (notificaciones.getTipoNot() != null) {
                            existingNotificaciones.setTipoNot(notificaciones.getTipoNot());
                        }
                        if (notificaciones.getClave() != null) {
                            existingNotificaciones.setClave(notificaciones.getClave());
                        }
                        if (notificaciones.getAsunto() != null) {
                            existingNotificaciones.setAsunto(notificaciones.getAsunto());
                        }
                        if (notificaciones.getTexto() != null) {
                            existingNotificaciones.setTexto(notificaciones.getTexto());
                        }
                        if (notificaciones.getPiePagina() != null) {
                            existingNotificaciones.setPiePagina(notificaciones.getPiePagina());
                        }
                        if (notificaciones.getUsuario() != null) {
                            existingNotificaciones.setUsuario(notificaciones.getUsuario());
                        }
                        if (notificaciones.getFechaMod() != null) {
                            existingNotificaciones.setFechaMod(notificaciones.getFechaMod());
                        }

                        return existingNotificaciones;
                    })
                    .flatMap(notificacionesRepository::save);

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
     * {@code GET  /notificaciones} : get all the notificaciones.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of notificaciones in body.
     */
    @GetMapping("/notificaciones")
    public Mono<ResponseEntity<List<Notificaciones>>> getAllNotificaciones(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of Notificaciones");
        return notificacionesRepository
            .count()
            .zipWith(notificacionesRepository.findAllBy(pageable).collectList())
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
     * {@code GET  /notificaciones/:id} : get the "id" notificaciones.
     *
     * @param id the id of the notificaciones to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the notificaciones, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/notificaciones/{id}")
    public Mono<ResponseEntity<Notificaciones>> getNotificaciones(@PathVariable Long id) {
        log.debug("REST request to get Notificaciones : {}", id);
        Mono<Notificaciones> notificaciones = notificacionesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(notificaciones);
    }

    /**
     * {@code DELETE  /notificaciones/:id} : delete the "id" notificaciones.
     *
     * @param id the id of the notificaciones to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/notificaciones/{id}")
    public Mono<ResponseEntity<Void>> deleteNotificaciones(@PathVariable Long id) {
        log.debug("REST request to delete Notificaciones : {}", id);
        return notificacionesRepository
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
