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
import mx.conacyt.crip.proyecto03.domain.MotivosCancel;
import mx.conacyt.crip.proyecto03.domain.enumeration.Estatus;
import mx.conacyt.crip.proyecto03.repository.EntityManager;
import mx.conacyt.crip.proyecto03.repository.MotivosCancelRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link MotivosCancelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class MotivosCancelResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_MOTIVO_CANCEL = "AAAAAAAAAA";
    private static final String UPDATED_MOTIVO_CANCEL = "BBBBBBBBBB";

    private static final Estatus DEFAULT_ESTATUS = Estatus.Activo;
    private static final Estatus UPDATED_ESTATUS = Estatus.Inactivo;

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_MOD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_MOD = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/motivos-cancels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MotivosCancelRepository motivosCancelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private MotivosCancel motivosCancel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MotivosCancel createEntity(EntityManager em) {
        MotivosCancel motivosCancel = new MotivosCancel()
            .fecha(DEFAULT_FECHA)
            .motivoCancel(DEFAULT_MOTIVO_CANCEL)
            .estatus(DEFAULT_ESTATUS)
            .usuario(DEFAULT_USUARIO)
            .fechaMod(DEFAULT_FECHA_MOD);
        return motivosCancel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MotivosCancel createUpdatedEntity(EntityManager em) {
        MotivosCancel motivosCancel = new MotivosCancel()
            .fecha(UPDATED_FECHA)
            .motivoCancel(UPDATED_MOTIVO_CANCEL)
            .estatus(UPDATED_ESTATUS)
            .usuario(UPDATED_USUARIO)
            .fechaMod(UPDATED_FECHA_MOD);
        return motivosCancel;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(MotivosCancel.class).block();
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
        motivosCancel = createEntity(em);
    }

    @Test
    void createMotivosCancel() throws Exception {
        int databaseSizeBeforeCreate = motivosCancelRepository.findAll().collectList().block().size();
        // Create the MotivosCancel
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(motivosCancel))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the MotivosCancel in the database
        List<MotivosCancel> motivosCancelList = motivosCancelRepository.findAll().collectList().block();
        assertThat(motivosCancelList).hasSize(databaseSizeBeforeCreate + 1);
        MotivosCancel testMotivosCancel = motivosCancelList.get(motivosCancelList.size() - 1);
        assertThat(testMotivosCancel.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testMotivosCancel.getMotivoCancel()).isEqualTo(DEFAULT_MOTIVO_CANCEL);
        assertThat(testMotivosCancel.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
        assertThat(testMotivosCancel.getUsuario()).isEqualTo(DEFAULT_USUARIO);
        assertThat(testMotivosCancel.getFechaMod()).isEqualTo(DEFAULT_FECHA_MOD);
    }

    @Test
    void createMotivosCancelWithExistingId() throws Exception {
        // Create the MotivosCancel with an existing ID
        motivosCancel.setId(1L);

        int databaseSizeBeforeCreate = motivosCancelRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(motivosCancel))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the MotivosCancel in the database
        List<MotivosCancel> motivosCancelList = motivosCancelRepository.findAll().collectList().block();
        assertThat(motivosCancelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllMotivosCancels() {
        // Initialize the database
        motivosCancelRepository.save(motivosCancel).block();

        // Get all the motivosCancelList
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
            .value(hasItem(motivosCancel.getId().intValue()))
            .jsonPath("$.[*].fecha")
            .value(hasItem(DEFAULT_FECHA.toString()))
            .jsonPath("$.[*].motivoCancel")
            .value(hasItem(DEFAULT_MOTIVO_CANCEL))
            .jsonPath("$.[*].estatus")
            .value(hasItem(DEFAULT_ESTATUS.toString()))
            .jsonPath("$.[*].usuario")
            .value(hasItem(DEFAULT_USUARIO))
            .jsonPath("$.[*].fechaMod")
            .value(hasItem(DEFAULT_FECHA_MOD.toString()));
    }

    @Test
    void getMotivosCancel() {
        // Initialize the database
        motivosCancelRepository.save(motivosCancel).block();

        // Get the motivosCancel
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, motivosCancel.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(motivosCancel.getId().intValue()))
            .jsonPath("$.fecha")
            .value(is(DEFAULT_FECHA.toString()))
            .jsonPath("$.motivoCancel")
            .value(is(DEFAULT_MOTIVO_CANCEL))
            .jsonPath("$.estatus")
            .value(is(DEFAULT_ESTATUS.toString()))
            .jsonPath("$.usuario")
            .value(is(DEFAULT_USUARIO))
            .jsonPath("$.fechaMod")
            .value(is(DEFAULT_FECHA_MOD.toString()));
    }

    @Test
    void getNonExistingMotivosCancel() {
        // Get the motivosCancel
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingMotivosCancel() throws Exception {
        // Initialize the database
        motivosCancelRepository.save(motivosCancel).block();

        int databaseSizeBeforeUpdate = motivosCancelRepository.findAll().collectList().block().size();

        // Update the motivosCancel
        MotivosCancel updatedMotivosCancel = motivosCancelRepository.findById(motivosCancel.getId()).block();
        updatedMotivosCancel
            .fecha(UPDATED_FECHA)
            .motivoCancel(UPDATED_MOTIVO_CANCEL)
            .estatus(UPDATED_ESTATUS)
            .usuario(UPDATED_USUARIO)
            .fechaMod(UPDATED_FECHA_MOD);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedMotivosCancel.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedMotivosCancel))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the MotivosCancel in the database
        List<MotivosCancel> motivosCancelList = motivosCancelRepository.findAll().collectList().block();
        assertThat(motivosCancelList).hasSize(databaseSizeBeforeUpdate);
        MotivosCancel testMotivosCancel = motivosCancelList.get(motivosCancelList.size() - 1);
        assertThat(testMotivosCancel.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testMotivosCancel.getMotivoCancel()).isEqualTo(UPDATED_MOTIVO_CANCEL);
        assertThat(testMotivosCancel.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testMotivosCancel.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testMotivosCancel.getFechaMod()).isEqualTo(UPDATED_FECHA_MOD);
    }

    @Test
    void putNonExistingMotivosCancel() throws Exception {
        int databaseSizeBeforeUpdate = motivosCancelRepository.findAll().collectList().block().size();
        motivosCancel.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, motivosCancel.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(motivosCancel))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the MotivosCancel in the database
        List<MotivosCancel> motivosCancelList = motivosCancelRepository.findAll().collectList().block();
        assertThat(motivosCancelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchMotivosCancel() throws Exception {
        int databaseSizeBeforeUpdate = motivosCancelRepository.findAll().collectList().block().size();
        motivosCancel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(motivosCancel))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the MotivosCancel in the database
        List<MotivosCancel> motivosCancelList = motivosCancelRepository.findAll().collectList().block();
        assertThat(motivosCancelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamMotivosCancel() throws Exception {
        int databaseSizeBeforeUpdate = motivosCancelRepository.findAll().collectList().block().size();
        motivosCancel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(motivosCancel))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the MotivosCancel in the database
        List<MotivosCancel> motivosCancelList = motivosCancelRepository.findAll().collectList().block();
        assertThat(motivosCancelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateMotivosCancelWithPatch() throws Exception {
        // Initialize the database
        motivosCancelRepository.save(motivosCancel).block();

        int databaseSizeBeforeUpdate = motivosCancelRepository.findAll().collectList().block().size();

        // Update the motivosCancel using partial update
        MotivosCancel partialUpdatedMotivosCancel = new MotivosCancel();
        partialUpdatedMotivosCancel.setId(motivosCancel.getId());

        partialUpdatedMotivosCancel.usuario(UPDATED_USUARIO).fechaMod(UPDATED_FECHA_MOD);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedMotivosCancel.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedMotivosCancel))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the MotivosCancel in the database
        List<MotivosCancel> motivosCancelList = motivosCancelRepository.findAll().collectList().block();
        assertThat(motivosCancelList).hasSize(databaseSizeBeforeUpdate);
        MotivosCancel testMotivosCancel = motivosCancelList.get(motivosCancelList.size() - 1);
        assertThat(testMotivosCancel.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testMotivosCancel.getMotivoCancel()).isEqualTo(DEFAULT_MOTIVO_CANCEL);
        assertThat(testMotivosCancel.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
        assertThat(testMotivosCancel.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testMotivosCancel.getFechaMod()).isEqualTo(UPDATED_FECHA_MOD);
    }

    @Test
    void fullUpdateMotivosCancelWithPatch() throws Exception {
        // Initialize the database
        motivosCancelRepository.save(motivosCancel).block();

        int databaseSizeBeforeUpdate = motivosCancelRepository.findAll().collectList().block().size();

        // Update the motivosCancel using partial update
        MotivosCancel partialUpdatedMotivosCancel = new MotivosCancel();
        partialUpdatedMotivosCancel.setId(motivosCancel.getId());

        partialUpdatedMotivosCancel
            .fecha(UPDATED_FECHA)
            .motivoCancel(UPDATED_MOTIVO_CANCEL)
            .estatus(UPDATED_ESTATUS)
            .usuario(UPDATED_USUARIO)
            .fechaMod(UPDATED_FECHA_MOD);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedMotivosCancel.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedMotivosCancel))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the MotivosCancel in the database
        List<MotivosCancel> motivosCancelList = motivosCancelRepository.findAll().collectList().block();
        assertThat(motivosCancelList).hasSize(databaseSizeBeforeUpdate);
        MotivosCancel testMotivosCancel = motivosCancelList.get(motivosCancelList.size() - 1);
        assertThat(testMotivosCancel.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testMotivosCancel.getMotivoCancel()).isEqualTo(UPDATED_MOTIVO_CANCEL);
        assertThat(testMotivosCancel.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testMotivosCancel.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testMotivosCancel.getFechaMod()).isEqualTo(UPDATED_FECHA_MOD);
    }

    @Test
    void patchNonExistingMotivosCancel() throws Exception {
        int databaseSizeBeforeUpdate = motivosCancelRepository.findAll().collectList().block().size();
        motivosCancel.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, motivosCancel.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(motivosCancel))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the MotivosCancel in the database
        List<MotivosCancel> motivosCancelList = motivosCancelRepository.findAll().collectList().block();
        assertThat(motivosCancelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchMotivosCancel() throws Exception {
        int databaseSizeBeforeUpdate = motivosCancelRepository.findAll().collectList().block().size();
        motivosCancel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(motivosCancel))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the MotivosCancel in the database
        List<MotivosCancel> motivosCancelList = motivosCancelRepository.findAll().collectList().block();
        assertThat(motivosCancelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamMotivosCancel() throws Exception {
        int databaseSizeBeforeUpdate = motivosCancelRepository.findAll().collectList().block().size();
        motivosCancel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(motivosCancel))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the MotivosCancel in the database
        List<MotivosCancel> motivosCancelList = motivosCancelRepository.findAll().collectList().block();
        assertThat(motivosCancelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteMotivosCancel() {
        // Initialize the database
        motivosCancelRepository.save(motivosCancel).block();

        int databaseSizeBeforeDelete = motivosCancelRepository.findAll().collectList().block().size();

        // Delete the motivosCancel
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, motivosCancel.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<MotivosCancel> motivosCancelList = motivosCancelRepository.findAll().collectList().block();
        assertThat(motivosCancelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
