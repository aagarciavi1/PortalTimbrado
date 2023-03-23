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
import mx.conacyt.crip.proyecto03.domain.TipoRecibo;
import mx.conacyt.crip.proyecto03.domain.enumeration.Estatus;
import mx.conacyt.crip.proyecto03.repository.EntityManager;
import mx.conacyt.crip.proyecto03.repository.TipoReciboRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link TipoReciboResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class TipoReciboResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TIPO_RECIBO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_RECIBO = "BBBBBBBBBB";

    private static final String DEFAULT_CLAVE = "AAAAAAAAAA";
    private static final String UPDATED_CLAVE = "BBBBBBBBBB";

    private static final Estatus DEFAULT_ESTATUS = Estatus.Activo;
    private static final Estatus UPDATED_ESTATUS = Estatus.Inactivo;

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_MOD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_MOD = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/tipo-recibos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TipoReciboRepository tipoReciboRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private TipoRecibo tipoRecibo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoRecibo createEntity(EntityManager em) {
        TipoRecibo tipoRecibo = new TipoRecibo()
            .fecha(DEFAULT_FECHA)
            .tipoRecibo(DEFAULT_TIPO_RECIBO)
            .clave(DEFAULT_CLAVE)
            .estatus(DEFAULT_ESTATUS)
            .usuario(DEFAULT_USUARIO)
            .fechaMod(DEFAULT_FECHA_MOD);
        return tipoRecibo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoRecibo createUpdatedEntity(EntityManager em) {
        TipoRecibo tipoRecibo = new TipoRecibo()
            .fecha(UPDATED_FECHA)
            .tipoRecibo(UPDATED_TIPO_RECIBO)
            .clave(UPDATED_CLAVE)
            .estatus(UPDATED_ESTATUS)
            .usuario(UPDATED_USUARIO)
            .fechaMod(UPDATED_FECHA_MOD);
        return tipoRecibo;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(TipoRecibo.class).block();
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
        tipoRecibo = createEntity(em);
    }

    @Test
    void createTipoRecibo() throws Exception {
        int databaseSizeBeforeCreate = tipoReciboRepository.findAll().collectList().block().size();
        // Create the TipoRecibo
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(tipoRecibo))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the TipoRecibo in the database
        List<TipoRecibo> tipoReciboList = tipoReciboRepository.findAll().collectList().block();
        assertThat(tipoReciboList).hasSize(databaseSizeBeforeCreate + 1);
        TipoRecibo testTipoRecibo = tipoReciboList.get(tipoReciboList.size() - 1);
        assertThat(testTipoRecibo.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testTipoRecibo.getTipoRecibo()).isEqualTo(DEFAULT_TIPO_RECIBO);
        assertThat(testTipoRecibo.getClave()).isEqualTo(DEFAULT_CLAVE);
        assertThat(testTipoRecibo.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
        assertThat(testTipoRecibo.getUsuario()).isEqualTo(DEFAULT_USUARIO);
        assertThat(testTipoRecibo.getFechaMod()).isEqualTo(DEFAULT_FECHA_MOD);
    }

    @Test
    void createTipoReciboWithExistingId() throws Exception {
        // Create the TipoRecibo with an existing ID
        tipoRecibo.setId(1L);

        int databaseSizeBeforeCreate = tipoReciboRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(tipoRecibo))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the TipoRecibo in the database
        List<TipoRecibo> tipoReciboList = tipoReciboRepository.findAll().collectList().block();
        assertThat(tipoReciboList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllTipoRecibos() {
        // Initialize the database
        tipoReciboRepository.save(tipoRecibo).block();

        // Get all the tipoReciboList
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
            .value(hasItem(tipoRecibo.getId().intValue()))
            .jsonPath("$.[*].fecha")
            .value(hasItem(DEFAULT_FECHA.toString()))
            .jsonPath("$.[*].tipoRecibo")
            .value(hasItem(DEFAULT_TIPO_RECIBO))
            .jsonPath("$.[*].clave")
            .value(hasItem(DEFAULT_CLAVE))
            .jsonPath("$.[*].estatus")
            .value(hasItem(DEFAULT_ESTATUS.toString()))
            .jsonPath("$.[*].usuario")
            .value(hasItem(DEFAULT_USUARIO))
            .jsonPath("$.[*].fechaMod")
            .value(hasItem(DEFAULT_FECHA_MOD.toString()));
    }

    @Test
    void getTipoRecibo() {
        // Initialize the database
        tipoReciboRepository.save(tipoRecibo).block();

        // Get the tipoRecibo
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, tipoRecibo.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(tipoRecibo.getId().intValue()))
            .jsonPath("$.fecha")
            .value(is(DEFAULT_FECHA.toString()))
            .jsonPath("$.tipoRecibo")
            .value(is(DEFAULT_TIPO_RECIBO))
            .jsonPath("$.clave")
            .value(is(DEFAULT_CLAVE))
            .jsonPath("$.estatus")
            .value(is(DEFAULT_ESTATUS.toString()))
            .jsonPath("$.usuario")
            .value(is(DEFAULT_USUARIO))
            .jsonPath("$.fechaMod")
            .value(is(DEFAULT_FECHA_MOD.toString()));
    }

    @Test
    void getNonExistingTipoRecibo() {
        // Get the tipoRecibo
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingTipoRecibo() throws Exception {
        // Initialize the database
        tipoReciboRepository.save(tipoRecibo).block();

        int databaseSizeBeforeUpdate = tipoReciboRepository.findAll().collectList().block().size();

        // Update the tipoRecibo
        TipoRecibo updatedTipoRecibo = tipoReciboRepository.findById(tipoRecibo.getId()).block();
        updatedTipoRecibo
            .fecha(UPDATED_FECHA)
            .tipoRecibo(UPDATED_TIPO_RECIBO)
            .clave(UPDATED_CLAVE)
            .estatus(UPDATED_ESTATUS)
            .usuario(UPDATED_USUARIO)
            .fechaMod(UPDATED_FECHA_MOD);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedTipoRecibo.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedTipoRecibo))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the TipoRecibo in the database
        List<TipoRecibo> tipoReciboList = tipoReciboRepository.findAll().collectList().block();
        assertThat(tipoReciboList).hasSize(databaseSizeBeforeUpdate);
        TipoRecibo testTipoRecibo = tipoReciboList.get(tipoReciboList.size() - 1);
        assertThat(testTipoRecibo.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testTipoRecibo.getTipoRecibo()).isEqualTo(UPDATED_TIPO_RECIBO);
        assertThat(testTipoRecibo.getClave()).isEqualTo(UPDATED_CLAVE);
        assertThat(testTipoRecibo.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testTipoRecibo.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testTipoRecibo.getFechaMod()).isEqualTo(UPDATED_FECHA_MOD);
    }

    @Test
    void putNonExistingTipoRecibo() throws Exception {
        int databaseSizeBeforeUpdate = tipoReciboRepository.findAll().collectList().block().size();
        tipoRecibo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, tipoRecibo.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(tipoRecibo))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the TipoRecibo in the database
        List<TipoRecibo> tipoReciboList = tipoReciboRepository.findAll().collectList().block();
        assertThat(tipoReciboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchTipoRecibo() throws Exception {
        int databaseSizeBeforeUpdate = tipoReciboRepository.findAll().collectList().block().size();
        tipoRecibo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(tipoRecibo))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the TipoRecibo in the database
        List<TipoRecibo> tipoReciboList = tipoReciboRepository.findAll().collectList().block();
        assertThat(tipoReciboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamTipoRecibo() throws Exception {
        int databaseSizeBeforeUpdate = tipoReciboRepository.findAll().collectList().block().size();
        tipoRecibo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(tipoRecibo))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the TipoRecibo in the database
        List<TipoRecibo> tipoReciboList = tipoReciboRepository.findAll().collectList().block();
        assertThat(tipoReciboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateTipoReciboWithPatch() throws Exception {
        // Initialize the database
        tipoReciboRepository.save(tipoRecibo).block();

        int databaseSizeBeforeUpdate = tipoReciboRepository.findAll().collectList().block().size();

        // Update the tipoRecibo using partial update
        TipoRecibo partialUpdatedTipoRecibo = new TipoRecibo();
        partialUpdatedTipoRecibo.setId(tipoRecibo.getId());

        partialUpdatedTipoRecibo.tipoRecibo(UPDATED_TIPO_RECIBO);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedTipoRecibo.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoRecibo))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the TipoRecibo in the database
        List<TipoRecibo> tipoReciboList = tipoReciboRepository.findAll().collectList().block();
        assertThat(tipoReciboList).hasSize(databaseSizeBeforeUpdate);
        TipoRecibo testTipoRecibo = tipoReciboList.get(tipoReciboList.size() - 1);
        assertThat(testTipoRecibo.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testTipoRecibo.getTipoRecibo()).isEqualTo(UPDATED_TIPO_RECIBO);
        assertThat(testTipoRecibo.getClave()).isEqualTo(DEFAULT_CLAVE);
        assertThat(testTipoRecibo.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
        assertThat(testTipoRecibo.getUsuario()).isEqualTo(DEFAULT_USUARIO);
        assertThat(testTipoRecibo.getFechaMod()).isEqualTo(DEFAULT_FECHA_MOD);
    }

    @Test
    void fullUpdateTipoReciboWithPatch() throws Exception {
        // Initialize the database
        tipoReciboRepository.save(tipoRecibo).block();

        int databaseSizeBeforeUpdate = tipoReciboRepository.findAll().collectList().block().size();

        // Update the tipoRecibo using partial update
        TipoRecibo partialUpdatedTipoRecibo = new TipoRecibo();
        partialUpdatedTipoRecibo.setId(tipoRecibo.getId());

        partialUpdatedTipoRecibo
            .fecha(UPDATED_FECHA)
            .tipoRecibo(UPDATED_TIPO_RECIBO)
            .clave(UPDATED_CLAVE)
            .estatus(UPDATED_ESTATUS)
            .usuario(UPDATED_USUARIO)
            .fechaMod(UPDATED_FECHA_MOD);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedTipoRecibo.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoRecibo))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the TipoRecibo in the database
        List<TipoRecibo> tipoReciboList = tipoReciboRepository.findAll().collectList().block();
        assertThat(tipoReciboList).hasSize(databaseSizeBeforeUpdate);
        TipoRecibo testTipoRecibo = tipoReciboList.get(tipoReciboList.size() - 1);
        assertThat(testTipoRecibo.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testTipoRecibo.getTipoRecibo()).isEqualTo(UPDATED_TIPO_RECIBO);
        assertThat(testTipoRecibo.getClave()).isEqualTo(UPDATED_CLAVE);
        assertThat(testTipoRecibo.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testTipoRecibo.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testTipoRecibo.getFechaMod()).isEqualTo(UPDATED_FECHA_MOD);
    }

    @Test
    void patchNonExistingTipoRecibo() throws Exception {
        int databaseSizeBeforeUpdate = tipoReciboRepository.findAll().collectList().block().size();
        tipoRecibo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, tipoRecibo.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(tipoRecibo))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the TipoRecibo in the database
        List<TipoRecibo> tipoReciboList = tipoReciboRepository.findAll().collectList().block();
        assertThat(tipoReciboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchTipoRecibo() throws Exception {
        int databaseSizeBeforeUpdate = tipoReciboRepository.findAll().collectList().block().size();
        tipoRecibo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(tipoRecibo))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the TipoRecibo in the database
        List<TipoRecibo> tipoReciboList = tipoReciboRepository.findAll().collectList().block();
        assertThat(tipoReciboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamTipoRecibo() throws Exception {
        int databaseSizeBeforeUpdate = tipoReciboRepository.findAll().collectList().block().size();
        tipoRecibo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(tipoRecibo))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the TipoRecibo in the database
        List<TipoRecibo> tipoReciboList = tipoReciboRepository.findAll().collectList().block();
        assertThat(tipoReciboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteTipoRecibo() {
        // Initialize the database
        tipoReciboRepository.save(tipoRecibo).block();

        int databaseSizeBeforeDelete = tipoReciboRepository.findAll().collectList().block().size();

        // Delete the tipoRecibo
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, tipoRecibo.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<TipoRecibo> tipoReciboList = tipoReciboRepository.findAll().collectList().block();
        assertThat(tipoReciboList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
