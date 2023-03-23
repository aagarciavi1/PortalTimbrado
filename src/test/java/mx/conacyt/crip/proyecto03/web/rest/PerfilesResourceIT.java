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
import mx.conacyt.crip.proyecto03.domain.Perfiles;
import mx.conacyt.crip.proyecto03.repository.EntityManager;
import mx.conacyt.crip.proyecto03.repository.PerfilesRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link PerfilesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class PerfilesResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PERFIL = "AAAAAAAAAA";
    private static final String UPDATED_PERFIL = "BBBBBBBBBB";

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_MOD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_MOD = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/perfiles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PerfilesRepository perfilesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Perfiles perfiles;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Perfiles createEntity(EntityManager em) {
        Perfiles perfiles = new Perfiles().fecha(DEFAULT_FECHA).perfil(DEFAULT_PERFIL).usuario(DEFAULT_USUARIO).fechaMod(DEFAULT_FECHA_MOD);
        return perfiles;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Perfiles createUpdatedEntity(EntityManager em) {
        Perfiles perfiles = new Perfiles().fecha(UPDATED_FECHA).perfil(UPDATED_PERFIL).usuario(UPDATED_USUARIO).fechaMod(UPDATED_FECHA_MOD);
        return perfiles;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Perfiles.class).block();
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
        perfiles = createEntity(em);
    }

    @Test
    void createPerfiles() throws Exception {
        int databaseSizeBeforeCreate = perfilesRepository.findAll().collectList().block().size();
        // Create the Perfiles
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(perfiles))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Perfiles in the database
        List<Perfiles> perfilesList = perfilesRepository.findAll().collectList().block();
        assertThat(perfilesList).hasSize(databaseSizeBeforeCreate + 1);
        Perfiles testPerfiles = perfilesList.get(perfilesList.size() - 1);
        assertThat(testPerfiles.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testPerfiles.getPerfil()).isEqualTo(DEFAULT_PERFIL);
        assertThat(testPerfiles.getUsuario()).isEqualTo(DEFAULT_USUARIO);
        assertThat(testPerfiles.getFechaMod()).isEqualTo(DEFAULT_FECHA_MOD);
    }

    @Test
    void createPerfilesWithExistingId() throws Exception {
        // Create the Perfiles with an existing ID
        perfiles.setId(1L);

        int databaseSizeBeforeCreate = perfilesRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(perfiles))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Perfiles in the database
        List<Perfiles> perfilesList = perfilesRepository.findAll().collectList().block();
        assertThat(perfilesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = perfilesRepository.findAll().collectList().block().size();
        // set the field null
        perfiles.setFecha(null);

        // Create the Perfiles, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(perfiles))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Perfiles> perfilesList = perfilesRepository.findAll().collectList().block();
        assertThat(perfilesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkPerfilIsRequired() throws Exception {
        int databaseSizeBeforeTest = perfilesRepository.findAll().collectList().block().size();
        // set the field null
        perfiles.setPerfil(null);

        // Create the Perfiles, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(perfiles))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Perfiles> perfilesList = perfilesRepository.findAll().collectList().block();
        assertThat(perfilesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllPerfiles() {
        // Initialize the database
        perfilesRepository.save(perfiles).block();

        // Get all the perfilesList
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
            .value(hasItem(perfiles.getId().intValue()))
            .jsonPath("$.[*].fecha")
            .value(hasItem(DEFAULT_FECHA.toString()))
            .jsonPath("$.[*].perfil")
            .value(hasItem(DEFAULT_PERFIL))
            .jsonPath("$.[*].usuario")
            .value(hasItem(DEFAULT_USUARIO))
            .jsonPath("$.[*].fechaMod")
            .value(hasItem(DEFAULT_FECHA_MOD.toString()));
    }

    @Test
    void getPerfiles() {
        // Initialize the database
        perfilesRepository.save(perfiles).block();

        // Get the perfiles
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, perfiles.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(perfiles.getId().intValue()))
            .jsonPath("$.fecha")
            .value(is(DEFAULT_FECHA.toString()))
            .jsonPath("$.perfil")
            .value(is(DEFAULT_PERFIL))
            .jsonPath("$.usuario")
            .value(is(DEFAULT_USUARIO))
            .jsonPath("$.fechaMod")
            .value(is(DEFAULT_FECHA_MOD.toString()));
    }

    @Test
    void getNonExistingPerfiles() {
        // Get the perfiles
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingPerfiles() throws Exception {
        // Initialize the database
        perfilesRepository.save(perfiles).block();

        int databaseSizeBeforeUpdate = perfilesRepository.findAll().collectList().block().size();

        // Update the perfiles
        Perfiles updatedPerfiles = perfilesRepository.findById(perfiles.getId()).block();
        updatedPerfiles.fecha(UPDATED_FECHA).perfil(UPDATED_PERFIL).usuario(UPDATED_USUARIO).fechaMod(UPDATED_FECHA_MOD);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedPerfiles.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedPerfiles))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Perfiles in the database
        List<Perfiles> perfilesList = perfilesRepository.findAll().collectList().block();
        assertThat(perfilesList).hasSize(databaseSizeBeforeUpdate);
        Perfiles testPerfiles = perfilesList.get(perfilesList.size() - 1);
        assertThat(testPerfiles.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testPerfiles.getPerfil()).isEqualTo(UPDATED_PERFIL);
        assertThat(testPerfiles.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testPerfiles.getFechaMod()).isEqualTo(UPDATED_FECHA_MOD);
    }

    @Test
    void putNonExistingPerfiles() throws Exception {
        int databaseSizeBeforeUpdate = perfilesRepository.findAll().collectList().block().size();
        perfiles.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, perfiles.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(perfiles))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Perfiles in the database
        List<Perfiles> perfilesList = perfilesRepository.findAll().collectList().block();
        assertThat(perfilesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchPerfiles() throws Exception {
        int databaseSizeBeforeUpdate = perfilesRepository.findAll().collectList().block().size();
        perfiles.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(perfiles))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Perfiles in the database
        List<Perfiles> perfilesList = perfilesRepository.findAll().collectList().block();
        assertThat(perfilesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamPerfiles() throws Exception {
        int databaseSizeBeforeUpdate = perfilesRepository.findAll().collectList().block().size();
        perfiles.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(perfiles))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Perfiles in the database
        List<Perfiles> perfilesList = perfilesRepository.findAll().collectList().block();
        assertThat(perfilesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdatePerfilesWithPatch() throws Exception {
        // Initialize the database
        perfilesRepository.save(perfiles).block();

        int databaseSizeBeforeUpdate = perfilesRepository.findAll().collectList().block().size();

        // Update the perfiles using partial update
        Perfiles partialUpdatedPerfiles = new Perfiles();
        partialUpdatedPerfiles.setId(perfiles.getId());

        partialUpdatedPerfiles.fecha(UPDATED_FECHA).perfil(UPDATED_PERFIL).fechaMod(UPDATED_FECHA_MOD);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedPerfiles.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedPerfiles))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Perfiles in the database
        List<Perfiles> perfilesList = perfilesRepository.findAll().collectList().block();
        assertThat(perfilesList).hasSize(databaseSizeBeforeUpdate);
        Perfiles testPerfiles = perfilesList.get(perfilesList.size() - 1);
        assertThat(testPerfiles.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testPerfiles.getPerfil()).isEqualTo(UPDATED_PERFIL);
        assertThat(testPerfiles.getUsuario()).isEqualTo(DEFAULT_USUARIO);
        assertThat(testPerfiles.getFechaMod()).isEqualTo(UPDATED_FECHA_MOD);
    }

    @Test
    void fullUpdatePerfilesWithPatch() throws Exception {
        // Initialize the database
        perfilesRepository.save(perfiles).block();

        int databaseSizeBeforeUpdate = perfilesRepository.findAll().collectList().block().size();

        // Update the perfiles using partial update
        Perfiles partialUpdatedPerfiles = new Perfiles();
        partialUpdatedPerfiles.setId(perfiles.getId());

        partialUpdatedPerfiles.fecha(UPDATED_FECHA).perfil(UPDATED_PERFIL).usuario(UPDATED_USUARIO).fechaMod(UPDATED_FECHA_MOD);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedPerfiles.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedPerfiles))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Perfiles in the database
        List<Perfiles> perfilesList = perfilesRepository.findAll().collectList().block();
        assertThat(perfilesList).hasSize(databaseSizeBeforeUpdate);
        Perfiles testPerfiles = perfilesList.get(perfilesList.size() - 1);
        assertThat(testPerfiles.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testPerfiles.getPerfil()).isEqualTo(UPDATED_PERFIL);
        assertThat(testPerfiles.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testPerfiles.getFechaMod()).isEqualTo(UPDATED_FECHA_MOD);
    }

    @Test
    void patchNonExistingPerfiles() throws Exception {
        int databaseSizeBeforeUpdate = perfilesRepository.findAll().collectList().block().size();
        perfiles.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, perfiles.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(perfiles))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Perfiles in the database
        List<Perfiles> perfilesList = perfilesRepository.findAll().collectList().block();
        assertThat(perfilesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchPerfiles() throws Exception {
        int databaseSizeBeforeUpdate = perfilesRepository.findAll().collectList().block().size();
        perfiles.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(perfiles))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Perfiles in the database
        List<Perfiles> perfilesList = perfilesRepository.findAll().collectList().block();
        assertThat(perfilesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamPerfiles() throws Exception {
        int databaseSizeBeforeUpdate = perfilesRepository.findAll().collectList().block().size();
        perfiles.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(perfiles))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Perfiles in the database
        List<Perfiles> perfilesList = perfilesRepository.findAll().collectList().block();
        assertThat(perfilesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deletePerfiles() {
        // Initialize the database
        perfilesRepository.save(perfiles).block();

        int databaseSizeBeforeDelete = perfilesRepository.findAll().collectList().block().size();

        // Delete the perfiles
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, perfiles.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Perfiles> perfilesList = perfilesRepository.findAll().collectList().block();
        assertThat(perfilesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
