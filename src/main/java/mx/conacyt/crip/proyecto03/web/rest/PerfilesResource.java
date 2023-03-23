package mx.conacyt.crip.proyecto03.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import mx.conacyt.crip.proyecto03.domain.Perfiles;
import mx.conacyt.crip.proyecto03.repository.PerfilesRepository;
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
 * REST controller for managing {@link mx.conacyt.crip.proyecto03.domain.Perfiles}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PerfilesResource {

    private final Logger log = LoggerFactory.getLogger(PerfilesResource.class);

    private static final String ENTITY_NAME = "perfiles";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PerfilesRepository perfilesRepository;

    public PerfilesResource(PerfilesRepository perfilesRepository) {
        this.perfilesRepository = perfilesRepository;
    }

    /**
     * {@code POST  /perfiles} : Create a new perfiles.
     *
     * @param perfiles the perfiles to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new perfiles, or with status {@code 400 (Bad Request)} if the perfiles has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/perfiles")
    public Mono<ResponseEntity<Perfiles>> createPerfiles(@Valid @RequestBody Perfiles perfiles) throws URISyntaxException {
        log.debug("REST request to save Perfiles : {}", perfiles);
        if (perfiles.getId() != null) {
            throw new BadRequestAlertException("A new perfiles cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return perfilesRepository
            .save(perfiles)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/perfiles/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /perfiles/:id} : Updates an existing perfiles.
     *
     * @param id the id of the perfiles to save.
     * @param perfiles the perfiles to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated perfiles,
     * or with status {@code 400 (Bad Request)} if the perfiles is not valid,
     * or with status {@code 500 (Internal Server Error)} if the perfiles couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/perfiles/{id}")
    public Mono<ResponseEntity<Perfiles>> updatePerfiles(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Perfiles perfiles
    ) throws URISyntaxException {
        log.debug("REST request to update Perfiles : {}, {}", id, perfiles);
        if (perfiles.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, perfiles.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return perfilesRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return perfilesRepository
                    .save(perfiles)
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
     * {@code PATCH  /perfiles/:id} : Partial updates given fields of an existing perfiles, field will ignore if it is null
     *
     * @param id the id of the perfiles to save.
     * @param perfiles the perfiles to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated perfiles,
     * or with status {@code 400 (Bad Request)} if the perfiles is not valid,
     * or with status {@code 404 (Not Found)} if the perfiles is not found,
     * or with status {@code 500 (Internal Server Error)} if the perfiles couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/perfiles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<Perfiles>> partialUpdatePerfiles(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Perfiles perfiles
    ) throws URISyntaxException {
        log.debug("REST request to partial update Perfiles partially : {}, {}", id, perfiles);
        if (perfiles.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, perfiles.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return perfilesRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<Perfiles> result = perfilesRepository
                    .findById(perfiles.getId())
                    .map(existingPerfiles -> {
                        if (perfiles.getFecha() != null) {
                            existingPerfiles.setFecha(perfiles.getFecha());
                        }
                        if (perfiles.getPerfil() != null) {
                            existingPerfiles.setPerfil(perfiles.getPerfil());
                        }
                        if (perfiles.getUsuario() != null) {
                            existingPerfiles.setUsuario(perfiles.getUsuario());
                        }
                        if (perfiles.getFechaMod() != null) {
                            existingPerfiles.setFechaMod(perfiles.getFechaMod());
                        }

                        return existingPerfiles;
                    })
                    .flatMap(perfilesRepository::save);

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
     * {@code GET  /perfiles} : get all the perfiles.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of perfiles in body.
     */
    @GetMapping("/perfiles")
    public Mono<ResponseEntity<List<Perfiles>>> getAllPerfiles(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of Perfiles");
        return perfilesRepository
            .count()
            .zipWith(perfilesRepository.findAllBy(pageable).collectList())
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
     * {@code GET  /perfiles/:id} : get the "id" perfiles.
     *
     * @param id the id of the perfiles to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the perfiles, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/perfiles/{id}")
    public Mono<ResponseEntity<Perfiles>> getPerfiles(@PathVariable Long id) {
        log.debug("REST request to get Perfiles : {}", id);
        Mono<Perfiles> perfiles = perfilesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(perfiles);
    }

    /**
     * {@code DELETE  /perfiles/:id} : delete the "id" perfiles.
     *
     * @param id the id of the perfiles to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/perfiles/{id}")
    public Mono<ResponseEntity<Void>> deletePerfiles(@PathVariable Long id) {
        log.debug("REST request to delete Perfiles : {}", id);
        return perfilesRepository
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
