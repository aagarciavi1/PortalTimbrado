package mx.conacyt.crip.proyecto03.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import mx.conacyt.crip.proyecto03.domain.RegistInstit;
import mx.conacyt.crip.proyecto03.repository.RegistInstitRepository;
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
 * REST controller for managing {@link mx.conacyt.crip.proyecto03.domain.RegistInstit}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RegistInstitResource {

    private final Logger log = LoggerFactory.getLogger(RegistInstitResource.class);

    private static final String ENTITY_NAME = "registInstit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegistInstitRepository registInstitRepository;

    public RegistInstitResource(RegistInstitRepository registInstitRepository) {
        this.registInstitRepository = registInstitRepository;
    }

    /**
     * {@code POST  /regist-instits} : Create a new registInstit.
     *
     * @param registInstit the registInstit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new registInstit, or with status {@code 400 (Bad Request)} if the registInstit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/regist-instits")
    public Mono<ResponseEntity<RegistInstit>> createRegistInstit(@Valid @RequestBody RegistInstit registInstit) throws URISyntaxException {
        log.debug("REST request to save RegistInstit : {}", registInstit);
        if (registInstit.getId() != null) {
            throw new BadRequestAlertException("A new registInstit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return registInstitRepository
            .save(registInstit)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/regist-instits/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /regist-instits/:id} : Updates an existing registInstit.
     *
     * @param id the id of the registInstit to save.
     * @param registInstit the registInstit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated registInstit,
     * or with status {@code 400 (Bad Request)} if the registInstit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the registInstit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/regist-instits/{id}")
    public Mono<ResponseEntity<RegistInstit>> updateRegistInstit(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RegistInstit registInstit
    ) throws URISyntaxException {
        log.debug("REST request to update RegistInstit : {}, {}", id, registInstit);
        if (registInstit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, registInstit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return registInstitRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return registInstitRepository
                    .save(registInstit)
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
     * {@code PATCH  /regist-instits/:id} : Partial updates given fields of an existing registInstit, field will ignore if it is null
     *
     * @param id the id of the registInstit to save.
     * @param registInstit the registInstit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated registInstit,
     * or with status {@code 400 (Bad Request)} if the registInstit is not valid,
     * or with status {@code 404 (Not Found)} if the registInstit is not found,
     * or with status {@code 500 (Internal Server Error)} if the registInstit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/regist-instits/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<RegistInstit>> partialUpdateRegistInstit(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RegistInstit registInstit
    ) throws URISyntaxException {
        log.debug("REST request to partial update RegistInstit partially : {}, {}", id, registInstit);
        if (registInstit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, registInstit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return registInstitRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<RegistInstit> result = registInstitRepository
                    .findById(registInstit.getId())
                    .map(existingRegistInstit -> {
                        if (registInstit.getFecha() != null) {
                            existingRegistInstit.setFecha(registInstit.getFecha());
                        }
                        if (registInstit.getIdInstit() != null) {
                            existingRegistInstit.setIdInstit(registInstit.getIdInstit());
                        }
                        if (registInstit.getEstatus() != null) {
                            existingRegistInstit.setEstatus(registInstit.getEstatus());
                        }
                        if (registInstit.getNivel() != null) {
                            existingRegistInstit.setNivel(registInstit.getNivel());
                        }
                        if (registInstit.getRazonSocial() != null) {
                            existingRegistInstit.setRazonSocial(registInstit.getRazonSocial());
                        }
                        if (registInstit.getrFC() != null) {
                            existingRegistInstit.setrFC(registInstit.getrFC());
                        }
                        if (registInstit.getPais() != null) {
                            existingRegistInstit.setPais(registInstit.getPais());
                        }
                        if (registInstit.getMunicipio() != null) {
                            existingRegistInstit.setMunicipio(registInstit.getMunicipio());
                        }
                        if (registInstit.getLocalidad() != null) {
                            existingRegistInstit.setLocalidad(registInstit.getLocalidad());
                        }
                        if (registInstit.getCodigoPostal() != null) {
                            existingRegistInstit.setCodigoPostal(registInstit.getCodigoPostal());
                        }
                        if (registInstit.getColonia() != null) {
                            existingRegistInstit.setColonia(registInstit.getColonia());
                        }
                        if (registInstit.getCalle() != null) {
                            existingRegistInstit.setCalle(registInstit.getCalle());
                        }
                        if (registInstit.getNumExt() != null) {
                            existingRegistInstit.setNumExt(registInstit.getNumExt());
                        }
                        if (registInstit.getNumInt() != null) {
                            existingRegistInstit.setNumInt(registInstit.getNumInt());
                        }
                        if (registInstit.getLogo() != null) {
                            existingRegistInstit.setLogo(registInstit.getLogo());
                        }
                        if (registInstit.getLogoContentType() != null) {
                            existingRegistInstit.setLogoContentType(registInstit.getLogoContentType());
                        }
                        if (registInstit.getCertificado() != null) {
                            existingRegistInstit.setCertificado(registInstit.getCertificado());
                        }
                        if (registInstit.getCertificadoContentType() != null) {
                            existingRegistInstit.setCertificadoContentType(registInstit.getCertificadoContentType());
                        }
                        if (registInstit.getLlavePriv() != null) {
                            existingRegistInstit.setLlavePriv(registInstit.getLlavePriv());
                        }
                        if (registInstit.getLlavePrivContentType() != null) {
                            existingRegistInstit.setLlavePrivContentType(registInstit.getLlavePrivContentType());
                        }
                        if (registInstit.getContrasena() != null) {
                            existingRegistInstit.setContrasena(registInstit.getContrasena());
                        }
                        if (registInstit.getFechaExp() != null) {
                            existingRegistInstit.setFechaExp(registInstit.getFechaExp());
                        }
                        if (registInstit.getDias() != null) {
                            existingRegistInstit.setDias(registInstit.getDias());
                        }
                        if (registInstit.getCorreo() != null) {
                            existingRegistInstit.setCorreo(registInstit.getCorreo());
                        }
                        if (registInstit.getUsuario() != null) {
                            existingRegistInstit.setUsuario(registInstit.getUsuario());
                        }
                        if (registInstit.getFechaMod() != null) {
                            existingRegistInstit.setFechaMod(registInstit.getFechaMod());
                        }

                        return existingRegistInstit;
                    })
                    .flatMap(registInstitRepository::save);

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
     * {@code GET  /regist-instits} : get all the registInstits.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of registInstits in body.
     */
    @GetMapping("/regist-instits")
    public Mono<ResponseEntity<List<RegistInstit>>> getAllRegistInstits(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of RegistInstits");
        return registInstitRepository
            .count()
            .zipWith(registInstitRepository.findAllBy(pageable).collectList())
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
     * {@code GET  /regist-instits/:id} : get the "id" registInstit.
     *
     * @param id the id of the registInstit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the registInstit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/regist-instits/{id}")
    public Mono<ResponseEntity<RegistInstit>> getRegistInstit(@PathVariable Long id) {
        log.debug("REST request to get RegistInstit : {}", id);
        Mono<RegistInstit> registInstit = registInstitRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(registInstit);
    }

    /**
     * {@code DELETE  /regist-instits/:id} : delete the "id" registInstit.
     *
     * @param id the id of the registInstit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/regist-instits/{id}")
    public Mono<ResponseEntity<Void>> deleteRegistInstit(@PathVariable Long id) {
        log.debug("REST request to delete RegistInstit : {}", id);
        return registInstitRepository
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
