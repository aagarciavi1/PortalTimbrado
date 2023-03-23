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
import mx.conacyt.crip.proyecto03.domain.TipoCFDI;
import mx.conacyt.crip.proyecto03.domain.enumeration.Estatus;
import mx.conacyt.crip.proyecto03.repository.EntityManager;
import mx.conacyt.crip.proyecto03.repository.TipoCFDIRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link TipoCFDIResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class TipoCFDIResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TIPO_CFDI = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_CFDI = "BBBBBBBBBB";

    private static final Estatus DEFAULT_ESTATUS = Estatus.Activo;
    private static final Estatus UPDATED_ESTATUS = Estatus.Inactivo;

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_MOD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_MOD = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/tipo-cfdis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TipoCFDIRepository tipoCFDIRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private TipoCFDI tipoCFDI;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoCFDI createEntity(EntityManager em) {
        TipoCFDI tipoCFDI = new TipoCFDI()
            .fecha(DEFAULT_FECHA)
            .tipoCFDI(DEFAULT_TIPO_CFDI)
            .estatus(DEFAULT_ESTATUS)
            .usuario(DEFAULT_USUARIO)
            .fechaMod(DEFAULT_FECHA_MOD);
        return tipoCFDI;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoCFDI createUpdatedEntity(EntityManager em) {
        TipoCFDI tipoCFDI = new TipoCFDI()
            .fecha(UPDATED_FECHA)
            .tipoCFDI(UPDATED_TIPO_CFDI)
            .estatus(UPDATED_ESTATUS)
            .usuario(UPDATED_USUARIO)
            .fechaMod(UPDATED_FECHA_MOD);
        return tipoCFDI;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(TipoCFDI.class).block();
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
        tipoCFDI = createEntity(em);
    }

    @Test
    void createTipoCFDI() throws Exception {
        int databaseSizeBeforeCreate = tipoCFDIRepository.findAll().collectList().block().size();
        // Create the TipoCFDI
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(tipoCFDI))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the TipoCFDI in the database
        List<TipoCFDI> tipoCFDIList = tipoCFDIRepository.findAll().collectList().block();
        assertThat(tipoCFDIList).hasSize(databaseSizeBeforeCreate + 1);
        TipoCFDI testTipoCFDI = tipoCFDIList.get(tipoCFDIList.size() - 1);
        assertThat(testTipoCFDI.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testTipoCFDI.getTipoCFDI()).isEqualTo(DEFAULT_TIPO_CFDI);
        assertThat(testTipoCFDI.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
        assertThat(testTipoCFDI.getUsuario()).isEqualTo(DEFAULT_USUARIO);
        assertThat(testTipoCFDI.getFechaMod()).isEqualTo(DEFAULT_FECHA_MOD);
    }

    @Test
    void createTipoCFDIWithExistingId() throws Exception {
        // Create the TipoCFDI with an existing ID
        tipoCFDI.setId(1L);

        int databaseSizeBeforeCreate = tipoCFDIRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(tipoCFDI))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the TipoCFDI in the database
        List<TipoCFDI> tipoCFDIList = tipoCFDIRepository.findAll().collectList().block();
        assertThat(tipoCFDIList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllTipoCFDIS() {
        // Initialize the database
        tipoCFDIRepository.save(tipoCFDI).block();

        // Get all the tipoCFDIList
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
            .value(hasItem(tipoCFDI.getId().intValue()))
            .jsonPath("$.[*].fecha")
            .value(hasItem(DEFAULT_FECHA.toString()))
            .jsonPath("$.[*].tipoCFDI")
            .value(hasItem(DEFAULT_TIPO_CFDI))
            .jsonPath("$.[*].estatus")
            .value(hasItem(DEFAULT_ESTATUS.toString()))
            .jsonPath("$.[*].usuario")
            .value(hasItem(DEFAULT_USUARIO))
            .jsonPath("$.[*].fechaMod")
            .value(hasItem(DEFAULT_FECHA_MOD.toString()));
    }

    @Test
    void getTipoCFDI() {
        // Initialize the database
        tipoCFDIRepository.save(tipoCFDI).block();

        // Get the tipoCFDI
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, tipoCFDI.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(tipoCFDI.getId().intValue()))
            .jsonPath("$.fecha")
            .value(is(DEFAULT_FECHA.toString()))
            .jsonPath("$.tipoCFDI")
            .value(is(DEFAULT_TIPO_CFDI))
            .jsonPath("$.estatus")
            .value(is(DEFAULT_ESTATUS.toString()))
            .jsonPath("$.usuario")
            .value(is(DEFAULT_USUARIO))
            .jsonPath("$.fechaMod")
            .value(is(DEFAULT_FECHA_MOD.toString()));
    }

    @Test
    void getNonExistingTipoCFDI() {
        // Get the tipoCFDI
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingTipoCFDI() throws Exception {
        // Initialize the database
        tipoCFDIRepository.save(tipoCFDI).block();

        int databaseSizeBeforeUpdate = tipoCFDIRepository.findAll().collectList().block().size();

        // Update the tipoCFDI
        TipoCFDI updatedTipoCFDI = tipoCFDIRepository.findById(tipoCFDI.getId()).block();
        updatedTipoCFDI
            .fecha(UPDATED_FECHA)
            .tipoCFDI(UPDATED_TIPO_CFDI)
            .estatus(UPDATED_ESTATUS)
            .usuario(UPDATED_USUARIO)
            .fechaMod(UPDATED_FECHA_MOD);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedTipoCFDI.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedTipoCFDI))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the TipoCFDI in the database
        List<TipoCFDI> tipoCFDIList = tipoCFDIRepository.findAll().collectList().block();
        assertThat(tipoCFDIList).hasSize(databaseSizeBeforeUpdate);
        TipoCFDI testTipoCFDI = tipoCFDIList.get(tipoCFDIList.size() - 1);
        assertThat(testTipoCFDI.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testTipoCFDI.getTipoCFDI()).isEqualTo(UPDATED_TIPO_CFDI);
        assertThat(testTipoCFDI.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testTipoCFDI.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testTipoCFDI.getFechaMod()).isEqualTo(UPDATED_FECHA_MOD);
    }

    @Test
    void putNonExistingTipoCFDI() throws Exception {
        int databaseSizeBeforeUpdate = tipoCFDIRepository.findAll().collectList().block().size();
        tipoCFDI.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, tipoCFDI.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(tipoCFDI))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the TipoCFDI in the database
        List<TipoCFDI> tipoCFDIList = tipoCFDIRepository.findAll().collectList().block();
        assertThat(tipoCFDIList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchTipoCFDI() throws Exception {
        int databaseSizeBeforeUpdate = tipoCFDIRepository.findAll().collectList().block().size();
        tipoCFDI.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(tipoCFDI))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the TipoCFDI in the database
        List<TipoCFDI> tipoCFDIList = tipoCFDIRepository.findAll().collectList().block();
        assertThat(tipoCFDIList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamTipoCFDI() throws Exception {
        int databaseSizeBeforeUpdate = tipoCFDIRepository.findAll().collectList().block().size();
        tipoCFDI.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(tipoCFDI))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the TipoCFDI in the database
        List<TipoCFDI> tipoCFDIList = tipoCFDIRepository.findAll().collectList().block();
        assertThat(tipoCFDIList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateTipoCFDIWithPatch() throws Exception {
        // Initialize the database
        tipoCFDIRepository.save(tipoCFDI).block();

        int databaseSizeBeforeUpdate = tipoCFDIRepository.findAll().collectList().block().size();

        // Update the tipoCFDI using partial update
        TipoCFDI partialUpdatedTipoCFDI = new TipoCFDI();
        partialUpdatedTipoCFDI.setId(tipoCFDI.getId());

        partialUpdatedTipoCFDI.fecha(UPDATED_FECHA).usuario(UPDATED_USUARIO);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedTipoCFDI.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoCFDI))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the TipoCFDI in the database
        List<TipoCFDI> tipoCFDIList = tipoCFDIRepository.findAll().collectList().block();
        assertThat(tipoCFDIList).hasSize(databaseSizeBeforeUpdate);
        TipoCFDI testTipoCFDI = tipoCFDIList.get(tipoCFDIList.size() - 1);
        assertThat(testTipoCFDI.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testTipoCFDI.getTipoCFDI()).isEqualTo(DEFAULT_TIPO_CFDI);
        assertThat(testTipoCFDI.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
        assertThat(testTipoCFDI.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testTipoCFDI.getFechaMod()).isEqualTo(DEFAULT_FECHA_MOD);
    }

    @Test
    void fullUpdateTipoCFDIWithPatch() throws Exception {
        // Initialize the database
        tipoCFDIRepository.save(tipoCFDI).block();

        int databaseSizeBeforeUpdate = tipoCFDIRepository.findAll().collectList().block().size();

        // Update the tipoCFDI using partial update
        TipoCFDI partialUpdatedTipoCFDI = new TipoCFDI();
        partialUpdatedTipoCFDI.setId(tipoCFDI.getId());

        partialUpdatedTipoCFDI
            .fecha(UPDATED_FECHA)
            .tipoCFDI(UPDATED_TIPO_CFDI)
            .estatus(UPDATED_ESTATUS)
            .usuario(UPDATED_USUARIO)
            .fechaMod(UPDATED_FECHA_MOD);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedTipoCFDI.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoCFDI))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the TipoCFDI in the database
        List<TipoCFDI> tipoCFDIList = tipoCFDIRepository.findAll().collectList().block();
        assertThat(tipoCFDIList).hasSize(databaseSizeBeforeUpdate);
        TipoCFDI testTipoCFDI = tipoCFDIList.get(tipoCFDIList.size() - 1);
        assertThat(testTipoCFDI.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testTipoCFDI.getTipoCFDI()).isEqualTo(UPDATED_TIPO_CFDI);
        assertThat(testTipoCFDI.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testTipoCFDI.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testTipoCFDI.getFechaMod()).isEqualTo(UPDATED_FECHA_MOD);
    }

    @Test
    void patchNonExistingTipoCFDI() throws Exception {
        int databaseSizeBeforeUpdate = tipoCFDIRepository.findAll().collectList().block().size();
        tipoCFDI.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, tipoCFDI.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(tipoCFDI))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the TipoCFDI in the database
        List<TipoCFDI> tipoCFDIList = tipoCFDIRepository.findAll().collectList().block();
        assertThat(tipoCFDIList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchTipoCFDI() throws Exception {
        int databaseSizeBeforeUpdate = tipoCFDIRepository.findAll().collectList().block().size();
        tipoCFDI.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(tipoCFDI))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the TipoCFDI in the database
        List<TipoCFDI> tipoCFDIList = tipoCFDIRepository.findAll().collectList().block();
        assertThat(tipoCFDIList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamTipoCFDI() throws Exception {
        int databaseSizeBeforeUpdate = tipoCFDIRepository.findAll().collectList().block().size();
        tipoCFDI.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(tipoCFDI))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the TipoCFDI in the database
        List<TipoCFDI> tipoCFDIList = tipoCFDIRepository.findAll().collectList().block();
        assertThat(tipoCFDIList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteTipoCFDI() {
        // Initialize the database
        tipoCFDIRepository.save(tipoCFDI).block();

        int databaseSizeBeforeDelete = tipoCFDIRepository.findAll().collectList().block().size();

        // Delete the tipoCFDI
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, tipoCFDI.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<TipoCFDI> tipoCFDIList = tipoCFDIRepository.findAll().collectList().block();
        assertThat(tipoCFDIList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
