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
import mx.conacyt.crip.proyecto03.domain.RepGrafica;
import mx.conacyt.crip.proyecto03.domain.enumeration.Estatus;
import mx.conacyt.crip.proyecto03.repository.EntityManager;
import mx.conacyt.crip.proyecto03.repository.RepGraficaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link RepGraficaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class RepGraficaResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_REP_GRAFICA = "AAAAAAAAAA";
    private static final String UPDATED_REP_GRAFICA = "BBBBBBBBBB";

    private static final Estatus DEFAULT_ESTATUS = Estatus.Activo;
    private static final Estatus UPDATED_ESTATUS = Estatus.Inactivo;

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_MOD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_MOD = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/rep-graficas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RepGraficaRepository repGraficaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private RepGrafica repGrafica;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RepGrafica createEntity(EntityManager em) {
        RepGrafica repGrafica = new RepGrafica()
            .fecha(DEFAULT_FECHA)
            .repGrafica(DEFAULT_REP_GRAFICA)
            .estatus(DEFAULT_ESTATUS)
            .usuario(DEFAULT_USUARIO)
            .fechaMod(DEFAULT_FECHA_MOD);
        return repGrafica;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RepGrafica createUpdatedEntity(EntityManager em) {
        RepGrafica repGrafica = new RepGrafica()
            .fecha(UPDATED_FECHA)
            .repGrafica(UPDATED_REP_GRAFICA)
            .estatus(UPDATED_ESTATUS)
            .usuario(UPDATED_USUARIO)
            .fechaMod(UPDATED_FECHA_MOD);
        return repGrafica;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(RepGrafica.class).block();
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
        repGrafica = createEntity(em);
    }

    @Test
    void createRepGrafica() throws Exception {
        int databaseSizeBeforeCreate = repGraficaRepository.findAll().collectList().block().size();
        // Create the RepGrafica
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(repGrafica))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the RepGrafica in the database
        List<RepGrafica> repGraficaList = repGraficaRepository.findAll().collectList().block();
        assertThat(repGraficaList).hasSize(databaseSizeBeforeCreate + 1);
        RepGrafica testRepGrafica = repGraficaList.get(repGraficaList.size() - 1);
        assertThat(testRepGrafica.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testRepGrafica.getRepGrafica()).isEqualTo(DEFAULT_REP_GRAFICA);
        assertThat(testRepGrafica.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
        assertThat(testRepGrafica.getUsuario()).isEqualTo(DEFAULT_USUARIO);
        assertThat(testRepGrafica.getFechaMod()).isEqualTo(DEFAULT_FECHA_MOD);
    }

    @Test
    void createRepGraficaWithExistingId() throws Exception {
        // Create the RepGrafica with an existing ID
        repGrafica.setId(1L);

        int databaseSizeBeforeCreate = repGraficaRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(repGrafica))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the RepGrafica in the database
        List<RepGrafica> repGraficaList = repGraficaRepository.findAll().collectList().block();
        assertThat(repGraficaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllRepGraficas() {
        // Initialize the database
        repGraficaRepository.save(repGrafica).block();

        // Get all the repGraficaList
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
            .value(hasItem(repGrafica.getId().intValue()))
            .jsonPath("$.[*].fecha")
            .value(hasItem(DEFAULT_FECHA.toString()))
            .jsonPath("$.[*].repGrafica")
            .value(hasItem(DEFAULT_REP_GRAFICA))
            .jsonPath("$.[*].estatus")
            .value(hasItem(DEFAULT_ESTATUS.toString()))
            .jsonPath("$.[*].usuario")
            .value(hasItem(DEFAULT_USUARIO))
            .jsonPath("$.[*].fechaMod")
            .value(hasItem(DEFAULT_FECHA_MOD.toString()));
    }

    @Test
    void getRepGrafica() {
        // Initialize the database
        repGraficaRepository.save(repGrafica).block();

        // Get the repGrafica
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, repGrafica.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(repGrafica.getId().intValue()))
            .jsonPath("$.fecha")
            .value(is(DEFAULT_FECHA.toString()))
            .jsonPath("$.repGrafica")
            .value(is(DEFAULT_REP_GRAFICA))
            .jsonPath("$.estatus")
            .value(is(DEFAULT_ESTATUS.toString()))
            .jsonPath("$.usuario")
            .value(is(DEFAULT_USUARIO))
            .jsonPath("$.fechaMod")
            .value(is(DEFAULT_FECHA_MOD.toString()));
    }

    @Test
    void getNonExistingRepGrafica() {
        // Get the repGrafica
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingRepGrafica() throws Exception {
        // Initialize the database
        repGraficaRepository.save(repGrafica).block();

        int databaseSizeBeforeUpdate = repGraficaRepository.findAll().collectList().block().size();

        // Update the repGrafica
        RepGrafica updatedRepGrafica = repGraficaRepository.findById(repGrafica.getId()).block();
        updatedRepGrafica
            .fecha(UPDATED_FECHA)
            .repGrafica(UPDATED_REP_GRAFICA)
            .estatus(UPDATED_ESTATUS)
            .usuario(UPDATED_USUARIO)
            .fechaMod(UPDATED_FECHA_MOD);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedRepGrafica.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedRepGrafica))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the RepGrafica in the database
        List<RepGrafica> repGraficaList = repGraficaRepository.findAll().collectList().block();
        assertThat(repGraficaList).hasSize(databaseSizeBeforeUpdate);
        RepGrafica testRepGrafica = repGraficaList.get(repGraficaList.size() - 1);
        assertThat(testRepGrafica.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testRepGrafica.getRepGrafica()).isEqualTo(UPDATED_REP_GRAFICA);
        assertThat(testRepGrafica.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testRepGrafica.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testRepGrafica.getFechaMod()).isEqualTo(UPDATED_FECHA_MOD);
    }

    @Test
    void putNonExistingRepGrafica() throws Exception {
        int databaseSizeBeforeUpdate = repGraficaRepository.findAll().collectList().block().size();
        repGrafica.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, repGrafica.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(repGrafica))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the RepGrafica in the database
        List<RepGrafica> repGraficaList = repGraficaRepository.findAll().collectList().block();
        assertThat(repGraficaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchRepGrafica() throws Exception {
        int databaseSizeBeforeUpdate = repGraficaRepository.findAll().collectList().block().size();
        repGrafica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(repGrafica))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the RepGrafica in the database
        List<RepGrafica> repGraficaList = repGraficaRepository.findAll().collectList().block();
        assertThat(repGraficaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamRepGrafica() throws Exception {
        int databaseSizeBeforeUpdate = repGraficaRepository.findAll().collectList().block().size();
        repGrafica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(repGrafica))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the RepGrafica in the database
        List<RepGrafica> repGraficaList = repGraficaRepository.findAll().collectList().block();
        assertThat(repGraficaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateRepGraficaWithPatch() throws Exception {
        // Initialize the database
        repGraficaRepository.save(repGrafica).block();

        int databaseSizeBeforeUpdate = repGraficaRepository.findAll().collectList().block().size();

        // Update the repGrafica using partial update
        RepGrafica partialUpdatedRepGrafica = new RepGrafica();
        partialUpdatedRepGrafica.setId(repGrafica.getId());

        partialUpdatedRepGrafica.repGrafica(UPDATED_REP_GRAFICA).estatus(UPDATED_ESTATUS).usuario(UPDATED_USUARIO);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedRepGrafica.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedRepGrafica))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the RepGrafica in the database
        List<RepGrafica> repGraficaList = repGraficaRepository.findAll().collectList().block();
        assertThat(repGraficaList).hasSize(databaseSizeBeforeUpdate);
        RepGrafica testRepGrafica = repGraficaList.get(repGraficaList.size() - 1);
        assertThat(testRepGrafica.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testRepGrafica.getRepGrafica()).isEqualTo(UPDATED_REP_GRAFICA);
        assertThat(testRepGrafica.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testRepGrafica.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testRepGrafica.getFechaMod()).isEqualTo(DEFAULT_FECHA_MOD);
    }

    @Test
    void fullUpdateRepGraficaWithPatch() throws Exception {
        // Initialize the database
        repGraficaRepository.save(repGrafica).block();

        int databaseSizeBeforeUpdate = repGraficaRepository.findAll().collectList().block().size();

        // Update the repGrafica using partial update
        RepGrafica partialUpdatedRepGrafica = new RepGrafica();
        partialUpdatedRepGrafica.setId(repGrafica.getId());

        partialUpdatedRepGrafica
            .fecha(UPDATED_FECHA)
            .repGrafica(UPDATED_REP_GRAFICA)
            .estatus(UPDATED_ESTATUS)
            .usuario(UPDATED_USUARIO)
            .fechaMod(UPDATED_FECHA_MOD);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedRepGrafica.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedRepGrafica))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the RepGrafica in the database
        List<RepGrafica> repGraficaList = repGraficaRepository.findAll().collectList().block();
        assertThat(repGraficaList).hasSize(databaseSizeBeforeUpdate);
        RepGrafica testRepGrafica = repGraficaList.get(repGraficaList.size() - 1);
        assertThat(testRepGrafica.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testRepGrafica.getRepGrafica()).isEqualTo(UPDATED_REP_GRAFICA);
        assertThat(testRepGrafica.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testRepGrafica.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testRepGrafica.getFechaMod()).isEqualTo(UPDATED_FECHA_MOD);
    }

    @Test
    void patchNonExistingRepGrafica() throws Exception {
        int databaseSizeBeforeUpdate = repGraficaRepository.findAll().collectList().block().size();
        repGrafica.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, repGrafica.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(repGrafica))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the RepGrafica in the database
        List<RepGrafica> repGraficaList = repGraficaRepository.findAll().collectList().block();
        assertThat(repGraficaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchRepGrafica() throws Exception {
        int databaseSizeBeforeUpdate = repGraficaRepository.findAll().collectList().block().size();
        repGrafica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(repGrafica))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the RepGrafica in the database
        List<RepGrafica> repGraficaList = repGraficaRepository.findAll().collectList().block();
        assertThat(repGraficaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamRepGrafica() throws Exception {
        int databaseSizeBeforeUpdate = repGraficaRepository.findAll().collectList().block().size();
        repGrafica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(repGrafica))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the RepGrafica in the database
        List<RepGrafica> repGraficaList = repGraficaRepository.findAll().collectList().block();
        assertThat(repGraficaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteRepGrafica() {
        // Initialize the database
        repGraficaRepository.save(repGrafica).block();

        int databaseSizeBeforeDelete = repGraficaRepository.findAll().collectList().block().size();

        // Delete the repGrafica
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, repGrafica.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<RepGrafica> repGraficaList = repGraficaRepository.findAll().collectList().block();
        assertThat(repGraficaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
