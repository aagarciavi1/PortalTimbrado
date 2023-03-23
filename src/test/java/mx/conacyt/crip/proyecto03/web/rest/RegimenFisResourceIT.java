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
import mx.conacyt.crip.proyecto03.domain.RegimenFis;
import mx.conacyt.crip.proyecto03.domain.enumeration.Estatus;
import mx.conacyt.crip.proyecto03.repository.EntityManager;
import mx.conacyt.crip.proyecto03.repository.RegimenFisRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link RegimenFisResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class RegimenFisResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_REGIMEN_F = "AAAAAAAAAA";
    private static final String UPDATED_REGIMEN_F = "BBBBBBBBBB";

    private static final Estatus DEFAULT_ESTATUS = Estatus.Activo;
    private static final Estatus UPDATED_ESTATUS = Estatus.Inactivo;

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    private static final String DEFAULT_FECHA_MOD = "AAAAAAAAAA";
    private static final String UPDATED_FECHA_MOD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/regimen-fis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RegimenFisRepository regimenFisRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private RegimenFis regimenFis;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegimenFis createEntity(EntityManager em) {
        RegimenFis regimenFis = new RegimenFis()
            .fecha(DEFAULT_FECHA)
            .regimenF(DEFAULT_REGIMEN_F)
            .estatus(DEFAULT_ESTATUS)
            .usuario(DEFAULT_USUARIO)
            .fechaMod(DEFAULT_FECHA_MOD);
        return regimenFis;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegimenFis createUpdatedEntity(EntityManager em) {
        RegimenFis regimenFis = new RegimenFis()
            .fecha(UPDATED_FECHA)
            .regimenF(UPDATED_REGIMEN_F)
            .estatus(UPDATED_ESTATUS)
            .usuario(UPDATED_USUARIO)
            .fechaMod(UPDATED_FECHA_MOD);
        return regimenFis;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(RegimenFis.class).block();
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
        regimenFis = createEntity(em);
    }

    @Test
    void createRegimenFis() throws Exception {
        int databaseSizeBeforeCreate = regimenFisRepository.findAll().collectList().block().size();
        // Create the RegimenFis
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(regimenFis))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the RegimenFis in the database
        List<RegimenFis> regimenFisList = regimenFisRepository.findAll().collectList().block();
        assertThat(regimenFisList).hasSize(databaseSizeBeforeCreate + 1);
        RegimenFis testRegimenFis = regimenFisList.get(regimenFisList.size() - 1);
        assertThat(testRegimenFis.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testRegimenFis.getRegimenF()).isEqualTo(DEFAULT_REGIMEN_F);
        assertThat(testRegimenFis.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
        assertThat(testRegimenFis.getUsuario()).isEqualTo(DEFAULT_USUARIO);
        assertThat(testRegimenFis.getFechaMod()).isEqualTo(DEFAULT_FECHA_MOD);
    }

    @Test
    void createRegimenFisWithExistingId() throws Exception {
        // Create the RegimenFis with an existing ID
        regimenFis.setId(1L);

        int databaseSizeBeforeCreate = regimenFisRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(regimenFis))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the RegimenFis in the database
        List<RegimenFis> regimenFisList = regimenFisRepository.findAll().collectList().block();
        assertThat(regimenFisList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllRegimenFis() {
        // Initialize the database
        regimenFisRepository.save(regimenFis).block();

        // Get all the regimenFisList
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
            .value(hasItem(regimenFis.getId().intValue()))
            .jsonPath("$.[*].fecha")
            .value(hasItem(DEFAULT_FECHA.toString()))
            .jsonPath("$.[*].regimenF")
            .value(hasItem(DEFAULT_REGIMEN_F))
            .jsonPath("$.[*].estatus")
            .value(hasItem(DEFAULT_ESTATUS.toString()))
            .jsonPath("$.[*].usuario")
            .value(hasItem(DEFAULT_USUARIO))
            .jsonPath("$.[*].fechaMod")
            .value(hasItem(DEFAULT_FECHA_MOD));
    }

    @Test
    void getRegimenFis() {
        // Initialize the database
        regimenFisRepository.save(regimenFis).block();

        // Get the regimenFis
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, regimenFis.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(regimenFis.getId().intValue()))
            .jsonPath("$.fecha")
            .value(is(DEFAULT_FECHA.toString()))
            .jsonPath("$.regimenF")
            .value(is(DEFAULT_REGIMEN_F))
            .jsonPath("$.estatus")
            .value(is(DEFAULT_ESTATUS.toString()))
            .jsonPath("$.usuario")
            .value(is(DEFAULT_USUARIO))
            .jsonPath("$.fechaMod")
            .value(is(DEFAULT_FECHA_MOD));
    }

    @Test
    void getNonExistingRegimenFis() {
        // Get the regimenFis
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingRegimenFis() throws Exception {
        // Initialize the database
        regimenFisRepository.save(regimenFis).block();

        int databaseSizeBeforeUpdate = regimenFisRepository.findAll().collectList().block().size();

        // Update the regimenFis
        RegimenFis updatedRegimenFis = regimenFisRepository.findById(regimenFis.getId()).block();
        updatedRegimenFis
            .fecha(UPDATED_FECHA)
            .regimenF(UPDATED_REGIMEN_F)
            .estatus(UPDATED_ESTATUS)
            .usuario(UPDATED_USUARIO)
            .fechaMod(UPDATED_FECHA_MOD);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedRegimenFis.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedRegimenFis))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the RegimenFis in the database
        List<RegimenFis> regimenFisList = regimenFisRepository.findAll().collectList().block();
        assertThat(regimenFisList).hasSize(databaseSizeBeforeUpdate);
        RegimenFis testRegimenFis = regimenFisList.get(regimenFisList.size() - 1);
        assertThat(testRegimenFis.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testRegimenFis.getRegimenF()).isEqualTo(UPDATED_REGIMEN_F);
        assertThat(testRegimenFis.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testRegimenFis.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testRegimenFis.getFechaMod()).isEqualTo(UPDATED_FECHA_MOD);
    }

    @Test
    void putNonExistingRegimenFis() throws Exception {
        int databaseSizeBeforeUpdate = regimenFisRepository.findAll().collectList().block().size();
        regimenFis.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, regimenFis.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(regimenFis))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the RegimenFis in the database
        List<RegimenFis> regimenFisList = regimenFisRepository.findAll().collectList().block();
        assertThat(regimenFisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchRegimenFis() throws Exception {
        int databaseSizeBeforeUpdate = regimenFisRepository.findAll().collectList().block().size();
        regimenFis.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(regimenFis))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the RegimenFis in the database
        List<RegimenFis> regimenFisList = regimenFisRepository.findAll().collectList().block();
        assertThat(regimenFisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamRegimenFis() throws Exception {
        int databaseSizeBeforeUpdate = regimenFisRepository.findAll().collectList().block().size();
        regimenFis.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(regimenFis))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the RegimenFis in the database
        List<RegimenFis> regimenFisList = regimenFisRepository.findAll().collectList().block();
        assertThat(regimenFisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateRegimenFisWithPatch() throws Exception {
        // Initialize the database
        regimenFisRepository.save(regimenFis).block();

        int databaseSizeBeforeUpdate = regimenFisRepository.findAll().collectList().block().size();

        // Update the regimenFis using partial update
        RegimenFis partialUpdatedRegimenFis = new RegimenFis();
        partialUpdatedRegimenFis.setId(regimenFis.getId());

        partialUpdatedRegimenFis.regimenF(UPDATED_REGIMEN_F).estatus(UPDATED_ESTATUS).usuario(UPDATED_USUARIO).fechaMod(UPDATED_FECHA_MOD);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedRegimenFis.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedRegimenFis))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the RegimenFis in the database
        List<RegimenFis> regimenFisList = regimenFisRepository.findAll().collectList().block();
        assertThat(regimenFisList).hasSize(databaseSizeBeforeUpdate);
        RegimenFis testRegimenFis = regimenFisList.get(regimenFisList.size() - 1);
        assertThat(testRegimenFis.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testRegimenFis.getRegimenF()).isEqualTo(UPDATED_REGIMEN_F);
        assertThat(testRegimenFis.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testRegimenFis.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testRegimenFis.getFechaMod()).isEqualTo(UPDATED_FECHA_MOD);
    }

    @Test
    void fullUpdateRegimenFisWithPatch() throws Exception {
        // Initialize the database
        regimenFisRepository.save(regimenFis).block();

        int databaseSizeBeforeUpdate = regimenFisRepository.findAll().collectList().block().size();

        // Update the regimenFis using partial update
        RegimenFis partialUpdatedRegimenFis = new RegimenFis();
        partialUpdatedRegimenFis.setId(regimenFis.getId());

        partialUpdatedRegimenFis
            .fecha(UPDATED_FECHA)
            .regimenF(UPDATED_REGIMEN_F)
            .estatus(UPDATED_ESTATUS)
            .usuario(UPDATED_USUARIO)
            .fechaMod(UPDATED_FECHA_MOD);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedRegimenFis.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedRegimenFis))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the RegimenFis in the database
        List<RegimenFis> regimenFisList = regimenFisRepository.findAll().collectList().block();
        assertThat(regimenFisList).hasSize(databaseSizeBeforeUpdate);
        RegimenFis testRegimenFis = regimenFisList.get(regimenFisList.size() - 1);
        assertThat(testRegimenFis.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testRegimenFis.getRegimenF()).isEqualTo(UPDATED_REGIMEN_F);
        assertThat(testRegimenFis.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testRegimenFis.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testRegimenFis.getFechaMod()).isEqualTo(UPDATED_FECHA_MOD);
    }

    @Test
    void patchNonExistingRegimenFis() throws Exception {
        int databaseSizeBeforeUpdate = regimenFisRepository.findAll().collectList().block().size();
        regimenFis.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, regimenFis.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(regimenFis))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the RegimenFis in the database
        List<RegimenFis> regimenFisList = regimenFisRepository.findAll().collectList().block();
        assertThat(regimenFisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchRegimenFis() throws Exception {
        int databaseSizeBeforeUpdate = regimenFisRepository.findAll().collectList().block().size();
        regimenFis.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(regimenFis))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the RegimenFis in the database
        List<RegimenFis> regimenFisList = regimenFisRepository.findAll().collectList().block();
        assertThat(regimenFisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamRegimenFis() throws Exception {
        int databaseSizeBeforeUpdate = regimenFisRepository.findAll().collectList().block().size();
        regimenFis.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(regimenFis))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the RegimenFis in the database
        List<RegimenFis> regimenFisList = regimenFisRepository.findAll().collectList().block();
        assertThat(regimenFisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteRegimenFis() {
        // Initialize the database
        regimenFisRepository.save(regimenFis).block();

        int databaseSizeBeforeDelete = regimenFisRepository.findAll().collectList().block().size();

        // Delete the regimenFis
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, regimenFis.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<RegimenFis> regimenFisList = regimenFisRepository.findAll().collectList().block();
        assertThat(regimenFisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
