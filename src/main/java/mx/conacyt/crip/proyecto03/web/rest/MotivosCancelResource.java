package mx.conacyt.crip.proyecto03.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mx.conacyt.crip.proyecto03.domain.MotivosCancel;
import mx.conacyt.crip.proyecto03.repository.MotivosCancelRepository;
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
 * REST controller for managing {@link mx.conacyt.crip.proyecto03.domain.MotivosCancel}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MotivosCancelResource {

    private final Logger log = LoggerFactory.getLogger(MotivosCancelResource.class);

    private static final String ENTITY_NAME = "motivosCancel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MotivosCancelRepository motivosCancelRepository;

    public MotivosCancelResource(MotivosCancelRepository motivosCancelRepository) {
        this.motivosCancelRepository = motivosCancelRepository;
    }

    /**
     * {@code POST  /motivos-cancels} : Create a new motivosCancel.
     *
     * @param motivosCancel the motivosCancel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new motivosCancel, or with status {@code 400 (Bad Request)} if the motivosCancel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/motivos-cancels")
    public Mono<ResponseEntity<MotivosCancel>> createMotivosCancel(@RequestBody MotivosCancel motivosCancel) throws URISyntaxException {
        log.debug("REST request to save MotivosCancel : {}", motivosCancel);
        if (motivosCancel.getId() != null) {
            throw new BadRequestAlertException("A new motivosCancel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return motivosCancelRepository
            .save(motivosCancel)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/motivos-cancels/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /motivos-cancels/:id} : Updates an existing motivosCancel.
     *
     * @param id the id of the motivosCancel to save.
     * @param motivosCancel the motivosCancel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated motivosCancel,
     * or with status {@code 400 (Bad Request)} if the motivosCancel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the motivosCancel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/motivos-cancels/{id}")
    public Mono<ResponseEntity<MotivosCancel>> updateMotivosCancel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MotivosCancel motivosCancel
    ) throws URISyntaxException {
        log.debug("REST request to update MotivosCancel : {}, {}", id, motivosCancel);
        if (motivosCancel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, motivosCancel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return motivosCancelRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return motivosCancelRepository
                    .save(motivosCancel)
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
     * {@code PATCH  /motivos-cancels/:id} : Partial updates given fields of an existing motivosCancel, field will ignore if it is null
     *
     * @param id the id of the motivosCancel to save.
     * @param motivosCancel the motivosCancel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated motivosCancel,
     * or with status {@code 400 (Bad Request)} if the motivosCancel is not valid,
     * or with status {@code 404 (Not Found)} if the motivosCancel is not found,
     * or with status {@code 500 (Internal Server Error)} if the motivosCancel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/motivos-cancels/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<MotivosCancel>> partialUpdateMotivosCancel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MotivosCancel motivosCancel
    ) throws URISyntaxException {
        log.debug("REST request to partial update MotivosCancel partially : {}, {}", id, motivosCancel);
        if (motivosCancel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, motivosCancel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return motivosCancelRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<MotivosCancel> result = motivosCancelRepository
                    .findById(motivosCancel.getId())
                    .map(existingMotivosCancel -> {
                        if (motivosCancel.getFecha() != null) {
                            existingMotivosCancel.setFecha(motivosCancel.getFecha());
                        }
                        if (motivosCancel.getMotivoCancel() != null) {
                            existingMotivosCancel.setMotivoCancel(motivosCancel.getMotivoCancel());
                        }
                        if (motivosCancel.getEstatus() != null) {
                            existingMotivosCancel.setEstatus(motivosCancel.getEstatus());
                        }
                        if (motivosCancel.getUsuario() != null) {
                            existingMotivosCancel.setUsuario(motivosCancel.getUsuario());
                        }
                        if (motivosCancel.getFechaMod() != null) {
                            existingMotivosCancel.setFechaMod(motivosCancel.getFechaMod());
                        }

                        return existingMotivosCancel;
                    })
                    .flatMap(motivosCancelRepository::save);

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
     * {@code GET  /motivos-cancels} : get all the motivosCancels.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of motivosCancels in body.
     */
    @GetMapping("/motivos-cancels")
    public Mono<ResponseEntity<List<MotivosCancel>>> getAllMotivosCancels(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of MotivosCancels");
        return motivosCancelRepository
            .count()
            .zipWith(motivosCancelRepository.findAllBy(pageable).collectList())
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
     * {@code GET  /motivos-cancels/:id} : get the "id" motivosCancel.
     *
     * @param id the id of the motivosCancel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the motivosCancel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/motivos-cancels/{id}")
    public Mono<ResponseEntity<MotivosCancel>> getMotivosCancel(@PathVariable Long id) {
        log.debug("REST request to get MotivosCancel : {}", id);
        Mono<MotivosCancel> motivosCancel = motivosCancelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(motivosCancel);
    }

    /**
     * {@code DELETE  /motivos-cancels/:id} : delete the "id" motivosCancel.
     *
     * @param id the id of the motivosCancel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/motivos-cancels/{id}")
    public Mono<ResponseEntity<Void>> deleteMotivosCancel(@PathVariable Long id) {
        log.debug("REST request to delete MotivosCancel : {}", id);
        return motivosCancelRepository
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
