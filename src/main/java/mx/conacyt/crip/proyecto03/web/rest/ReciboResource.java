package mx.conacyt.crip.proyecto03.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import mx.conacyt.crip.proyecto03.domain.Recibo;
import mx.conacyt.crip.proyecto03.repository.ReciboRepository;
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
 * REST controller for managing {@link mx.conacyt.crip.proyecto03.domain.Recibo}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ReciboResource {

    private final Logger log = LoggerFactory.getLogger(ReciboResource.class);

    private static final String ENTITY_NAME = "recibo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReciboRepository reciboRepository;

    public ReciboResource(ReciboRepository reciboRepository) {
        this.reciboRepository = reciboRepository;
    }

    /**
     * {@code POST  /recibos} : Create a new recibo.
     *
     * @param recibo the recibo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recibo, or with status {@code 400 (Bad Request)} if the recibo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/recibos")
    public Mono<ResponseEntity<Recibo>> createRecibo(@Valid @RequestBody Recibo recibo) throws URISyntaxException {
        log.debug("REST request to save Recibo : {}", recibo);
        if (recibo.getId() != null) {
            throw new BadRequestAlertException("A new recibo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return reciboRepository
            .save(recibo)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/recibos/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /recibos/:id} : Updates an existing recibo.
     *
     * @param id the id of the recibo to save.
     * @param recibo the recibo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recibo,
     * or with status {@code 400 (Bad Request)} if the recibo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recibo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/recibos/{id}")
    public Mono<ResponseEntity<Recibo>> updateRecibo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Recibo recibo
    ) throws URISyntaxException {
        log.debug("REST request to update Recibo : {}, {}", id, recibo);
        if (recibo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recibo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return reciboRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return reciboRepository
                    .save(recibo)
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
     * {@code PATCH  /recibos/:id} : Partial updates given fields of an existing recibo, field will ignore if it is null
     *
     * @param id the id of the recibo to save.
     * @param recibo the recibo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recibo,
     * or with status {@code 400 (Bad Request)} if the recibo is not valid,
     * or with status {@code 404 (Not Found)} if the recibo is not found,
     * or with status {@code 500 (Internal Server Error)} if the recibo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/recibos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<Recibo>> partialUpdateRecibo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Recibo recibo
    ) throws URISyntaxException {
        log.debug("REST request to partial update Recibo partially : {}, {}", id, recibo);
        if (recibo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recibo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return reciboRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<Recibo> result = reciboRepository
                    .findById(recibo.getId())
                    .map(existingRecibo -> {
                        if (recibo.getFecha() != null) {
                            existingRecibo.setFecha(recibo.getFecha());
                        }
                        if (recibo.getIdInstit() != null) {
                            existingRecibo.setIdInstit(recibo.getIdInstit());
                        }
                        if (recibo.getNivel() != null) {
                            existingRecibo.setNivel(recibo.getNivel());
                        }
                        if (recibo.getTipoCFDI() != null) {
                            existingRecibo.setTipoCFDI(recibo.getTipoCFDI());
                        }
                        if (recibo.getClave() != null) {
                            existingRecibo.setClave(recibo.getClave());
                        }
                        if (recibo.getEnvioConCFDI() != null) {
                            existingRecibo.setEnvioConCFDI(recibo.getEnvioConCFDI());
                        }
                        if (recibo.getEnvioSinCFDI() != null) {
                            existingRecibo.setEnvioSinCFDI(recibo.getEnvioSinCFDI());
                        }
                        if (recibo.getAplicaLeyenda() != null) {
                            existingRecibo.setAplicaLeyenda(recibo.getAplicaLeyenda());
                        }
                        if (recibo.getLeyenda() != null) {
                            existingRecibo.setLeyenda(recibo.getLeyenda());
                        }
                        if (recibo.getUsuario() != null) {
                            existingRecibo.setUsuario(recibo.getUsuario());
                        }
                        if (recibo.getFechaMod() != null) {
                            existingRecibo.setFechaMod(recibo.getFechaMod());
                        }

                        return existingRecibo;
                    })
                    .flatMap(reciboRepository::save);

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
     * {@code GET  /recibos} : get all the recibos.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recibos in body.
     */
    @GetMapping("/recibos")
    public Mono<ResponseEntity<List<Recibo>>> getAllRecibos(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of Recibos");
        return reciboRepository
            .count()
            .zipWith(reciboRepository.findAllBy(pageable).collectList())
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
     * {@code GET  /recibos/:id} : get the "id" recibo.
     *
     * @param id the id of the recibo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recibo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/recibos/{id}")
    public Mono<ResponseEntity<Recibo>> getRecibo(@PathVariable Long id) {
        log.debug("REST request to get Recibo : {}", id);
        Mono<Recibo> recibo = reciboRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(recibo);
    }

    /**
     * {@code DELETE  /recibos/:id} : delete the "id" recibo.
     *
     * @param id the id of the recibo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/recibos/{id}")
    public Mono<ResponseEntity<Void>> deleteRecibo(@PathVariable Long id) {
        log.debug("REST request to delete Recibo : {}", id);
        return reciboRepository
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
