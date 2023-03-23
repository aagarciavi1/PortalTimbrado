package mx.conacyt.crip.proyecto03.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import mx.conacyt.crip.proyecto03.IntegrationTest;
import mx.conacyt.crip.proyecto03.domain.EstatusCFDI;
import mx.conacyt.crip.proyecto03.domain.enumeration.Estatus;
import mx.conacyt.crip.proyecto03.repository.EntityManager;
import mx.conacyt.crip.proyecto03.repository.EstatusCFDIRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link EstatusCFDIResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class EstatusCFDIResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESC_EST_CFDI = "AAAAAAAAAA";
    private static final String UPDATED_DESC_EST_CFDI = "BBBBBBBBBB";

    private static final Estatus DEFAULT_ESTATUS = Estatus.Activo;
    private static final Estatus UPDATED_ESTATUS = Estatus.Inactivo;

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_MOD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_MOD = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/estatus-cfdis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EstatusCFDIRepository estatusCFDIRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private EstatusCFDI estatusCFDI;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstatusCFDI createEntity(EntityManager em) {
        EstatusCFDI estatusCFDI = new EstatusCFDI()
            .fecha(DEFAULT_FECHA)
            .descEstCFDI(DEFAULT_DESC_EST_CFDI)
            .estatus(DEFAULT_ESTATUS)
            .usuario(DEFAULT_USUARIO)
            .fechaMod(DEFAULT_FECHA_MOD);
        return estatusCFDI;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstatusCFDI createUpdatedEntity(EntityManager em) {
        EstatusCFDI estatusCFDI = new EstatusCFDI()
            .fecha(UPDATED_FECHA)
            .descEstCFDI(UPDATED_DESC_EST_CFDI)
            .estatus(UPDATED_ESTATUS)
            .usuario(UPDATED_USUARIO)
            .fechaMod(UPDATED_FECHA_MOD);
        return estatusCFDI;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(EstatusCFDI.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
    }

    @AfterEach
    public void cleanup() {
        deleteEntities(em);
    }

    @BeforeEach
    public void initTest() {
        deleteEntities(em);
        estatusCFDI = createEntity(em);
    }

    @Test
    void createEstatusCFDI() throws Exception {
        int databaseSizeBeforeCreate = estatusCFDIRepository.findAll().collectList().block().size();
        // Create the EstatusCFDI
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(estatusCFDI))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the EstatusCFDI in the database
        List<EstatusCFDI> estatusCFDIList = estatusCFDIRepository.findAll().collectList().block();
        assertThat(estatusCFDIList).hasSize(databaseSizeBeforeCreate + 1);
        EstatusCFDI testEstatusCFDI = estatusCFDIList.get(estatusCFDIList.size() - 1);
        assertThat(testEstatusCFDI.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testEstatusCFDI.getDescEstCFDI()).isEqualTo(DEFAULT_DESC_EST_CFDI);
        assertThat(testEstatusCFDI.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
        assertThat(testEstatusCFDI.getUsuario()).isEqualTo(DEFAULT_USUARIO);
        assertThat(testEstatusCFDI.getFechaMod()).isEqualTo(DEFAULT_FECHA_MOD);
    }

    @Test
    void createEstatusCFDIWithExistingId() throws Exception {
        // Create the EstatusCFDI with an existing ID
        estatusCFDI.setId(1L);

        int databaseSizeBeforeCreate = estatusCFDIRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(estatusCFDI))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the EstatusCFDI in the database
        List<EstatusCFDI> estatusCFDIList = estatusCFDIRepository.findAll().collectList().block();
        assertThat(estatusCFDIList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllEstatusCFDIS() {
        // Initialize the database
        estatusCFDIRepository.save(estatusCFDI).block();

        // Get all the estatusCFDIList
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id")
            .value(hasItem(estatusCFDI.getId().intValue()))
            .jsonPath("$.[*].fecha")
            .value(hasItem(DEFAULT_FECHA.toString()))
            .jsonPath("$.[*].descEstCFDI")
            .value(hasItem(DEFAULT_DESC_EST_CFDI))
            .jsonPath("$.[*].estatus")
            .value(hasItem(DEFAULT_ESTATUS.toString()))
            .jsonPath("$.[*].usuario")
            .value(hasItem(DEFAULT_USUARIO))
            .jsonPath("$.[*].fechaMod")
            .value(hasItem(DEFAULT_FECHA_MOD.toString()));
    }

    @Test
    void getEstatusCFDI() {
        // Initialize the database
        estatusCFDIRepository.save(estatusCFDI).block();

        // Get the estatusCFDI
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, estatusCFDI.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(estatusCFDI.getId().intValue()))
            .jsonPath("$.fecha")
            .value(is(DEFAULT_FECHA.toString()))
            .jsonPath("$.descEstCFDI")
            .value(is(DEFAULT_DESC_EST_CFDI))
            .jsonPath("$.estatus")
            .value(is(DEFAULT_ESTATUS.toString()))
            .jsonPath("$.usuario")
            .value(is(DEFAULT_USUARIO))
            .jsonPath("$.fechaMod")
            .value(is(DEFAULT_FECHA_MOD.toString()));
    }

    @Test
    void getNonExistingEstatusCFDI() {
        // Get the estatusCFDI
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingEstatusCFDI() throws Exception {
        // Initialize the database
        estatusCFDIRepository.save(estatusCFDI).block();

        int databaseSizeBeforeUpdate = estatusCFDIRepository.findAll().collectList().block().size();

        // Update the estatusCFDI
        EstatusCFDI updatedEstatusCFDI = estatusCFDIRepository.findById(estatusCFDI.getId()).block();
        updatedEstatusCFDI
            .fecha(UPDATED_FECHA)
            .descEstCFDI(UPDATED_DESC_EST_CFDI)
            .estatus(UPDATED_ESTATUS)
            .usuario(UPDATED_USUARIO)
            .fechaMod(UPDATED_FECHA_MOD);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedEstatusCFDI.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedEstatusCFDI))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the EstatusCFDI in the database
        List<EstatusCFDI> estatusCFDIList = estatusCFDIRepository.findAll().collectList().block();
        assertThat(estatusCFDIList).hasSize(databaseSizeBeforeUpdate);
        EstatusCFDI testEstatusCFDI = estatusCFDIList.get(estatusCFDIList.size() - 1);
        assertThat(testEstatusCFDI.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testEstatusCFDI.getDescEstCFDI()).isEqualTo(UPDATED_DESC_EST_CFDI);
        assertThat(testEstatusCFDI.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testEstatusCFDI.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testEstatusCFDI.getFechaMod()).isEqualTo(UPDATED_FECHA_MOD);
    }

    @Test
    void putNonExistingEstatusCFDI() throws Exception {
        int databaseSizeBeforeUpdate = estatusCFDIRepository.findAll().collectList().block().size();
        estatusCFDI.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, estatusCFDI.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(estatusCFDI))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the EstatusCFDI in the database
        List<EstatusCFDI> estatusCFDIList = estatusCFDIRepository.findAll().collectList().block();
        assertThat(estatusCFDIList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchEstatusCFDI() throws Exception {
        int databaseSizeBeforeUpdate = estatusCFDIRepository.findAll().collectList().block().size();
        estatusCFDI.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(estatusCFDI))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the EstatusCFDI in the database
        List<EstatusCFDI> estatusCFDIList = estatusCFDIRepository.findAll().collectList().block();
        assertThat(estatusCFDIList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamEstatusCFDI() throws Exception {
        int databaseSizeBeforeUpdate = estatusCFDIRepository.findAll().collectList().block().size();
        estatusCFDI.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(estatusCFDI))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the EstatusCFDI in the database
        List<EstatusCFDI> estatusCFDIList = estatusCFDIRepository.findAll().collectList().block();
        assertThat(estatusCFDIList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateEstatusCFDIWithPatch() throws Exception {
        // Initialize the database
        estatusCFDIRepository.save(estatusCFDI).block();

        int databaseSizeBeforeUpdate = estatusCFDIRepository.findAll().collectList().block().size();

        // Update the estatusCFDI using partial update
        EstatusCFDI partialUpdatedEstatusCFDI = new EstatusCFDI();
        partialUpdatedEstatusCFDI.setId(estatusCFDI.getId());

        partialUpdatedEstatusCFDI.descEstCFDI(UPDATED_DESC_EST_CFDI).estatus(UPDATED_ESTATUS).usuario(UPDATED_USUARIO);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedEstatusCFDI.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedEstatusCFDI))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the EstatusCFDI in the database
        List<EstatusCFDI> estatusCFDIList = estatusCFDIRepository.findAll().collectList().block();
        assertThat(estatusCFDIList).hasSize(databaseSizeBeforeUpdate);
        EstatusCFDI testEstatusCFDI = estatusCFDIList.get(estatusCFDIList.size() - 1);
        assertThat(testEstatusCFDI.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testEstatusCFDI.getDescEstCFDI()).isEqualTo(UPDATED_DESC_EST_CFDI);
        assertThat(testEstatusCFDI.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testEstatusCFDI.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testEstatusCFDI.getFechaMod()).isEqualTo(DEFAULT_FECHA_MOD);
    }

    @Test
    void fullUpdateEstatusCFDIWithPatch() throws Exception {
        // Initialize the database
        estatusCFDIRepository.save(estatusCFDI).block();

        int databaseSizeBeforeUpdate = estatusCFDIRepository.findAll().collectList().block().size();

        // Update the estatusCFDI using partial update
        EstatusCFDI partialUpdatedEstatusCFDI = new EstatusCFDI();
        partialUpdatedEstatusCFDI.setId(estatusCFDI.getId());

        partialUpdatedEstatusCFDI
            .fecha(UPDATED_FECHA)
            .descEstCFDI(UPDATED_DESC_EST_CFDI)
            .estatus(UPDATED_ESTATUS)
            .usuario(UPDATED_USUARIO)
            .fechaMod(UPDATED_FECHA_MOD);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedEstatusCFDI.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedEstatusCFDI))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the EstatusCFDI in the database
        List<EstatusCFDI> estatusCFDIList = estatusCFDIRepository.findAll().collectList().block();
        assertThat(estatusCFDIList).hasSize(databaseSizeBeforeUpdate);
        EstatusCFDI testEstatusCFDI = estatusCFDIList.get(estatusCFDIList.size() - 1);
        assertThat(testEstatusCFDI.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testEstatusCFDI.getDescEstCFDI()).isEqualTo(UPDATED_DESC_EST_CFDI);
        assertThat(testEstatusCFDI.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testEstatusCFDI.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testEstatusCFDI.getFechaMod()).isEqualTo(UPDATED_FECHA_MOD);
    }

    @Test
    void patchNonExistingEstatusCFDI() throws Exception {
        int databaseSizeBeforeUpdate = estatusCFDIRepository.findAll().collectList().block().size();
        estatusCFDI.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, estatusCFDI.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(estatusCFDI))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the EstatusCFDI in the database
        List<EstatusCFDI> estatusCFDIList = estatusCFDIRepository.findAll().collectList().block();
        assertThat(estatusCFDIList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchEstatusCFDI() throws Exception {
        int databaseSizeBeforeUpdate = estatusCFDIRepository.findAll().collectList().block().size();
        estatusCFDI.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(estatusCFDI))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the EstatusCFDI in the database
        List<EstatusCFDI> estatusCFDIList = estatusCFDIRepository.findAll().collectList().block();
        assertThat(estatusCFDIList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamEstatusCFDI() throws Exception {
        int databaseSizeBeforeUpdate = estatusCFDIRepository.findAll().collectList().block().size();
        estatusCFDI.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(estatusCFDI))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the EstatusCFDI in the database
        List<EstatusCFDI> estatusCFDIList = estatusCFDIRepository.findAll().collectList().block();
        assertThat(estatusCFDIList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteEstatusCFDI() {
        // Initialize the database
        estatusCFDIRepository.save(estatusCFDI).block();

        int databaseSizeBeforeDelete = estatusCFDIRepository.findAll().collectList().block().size();

        // Delete the estatusCFDI
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, estatusCFDI.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<EstatusCFDI> estatusCFDIList = estatusCFDIRepository.findAll().collectList().block();
        assertThat(estatusCFDIList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
