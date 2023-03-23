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
import mx.conacyt.crip.proyecto03.domain.Parametros;
import mx.conacyt.crip.proyecto03.repository.EntityManager;
import mx.conacyt.crip.proyecto03.repository.ParametrosRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link ParametrosResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class ParametrosResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_ID_INSTIT = 1;
    private static final Integer UPDATED_ID_INSTIT = 2;

    private static final Integer DEFAULT_NIVEL = 1;
    private static final Integer UPDATED_NIVEL = 2;

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_MOD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_MOD = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/parametros";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ParametrosRepository parametrosRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Parametros parametros;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parametros createEntity(EntityManager em) {
        Parametros parametros = new Parametros()
            .fecha(DEFAULT_FECHA)
            .idInstit(DEFAULT_ID_INSTIT)
            .nivel(DEFAULT_NIVEL)
            .usuario(DEFAULT_USUARIO)
            .fechaMod(DEFAULT_FECHA_MOD);
        return parametros;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parametros createUpdatedEntity(EntityManager em) {
        Parametros parametros = new Parametros()
            .fecha(UPDATED_FECHA)
            .idInstit(UPDATED_ID_INSTIT)
            .nivel(UPDATED_NIVEL)
            .usuario(UPDATED_USUARIO)
            .fechaMod(UPDATED_FECHA_MOD);
        return parametros;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Parametros.class).block();
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
        parametros = createEntity(em);
    }

    @Test
    void createParametros() throws Exception {
        int databaseSizeBeforeCreate = parametrosRepository.findAll().collectList().block().size();
        // Create the Parametros
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(parametros))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Parametros in the database
        List<Parametros> parametrosList = parametrosRepository.findAll().collectList().block();
        assertThat(parametrosList).hasSize(databaseSizeBeforeCreate + 1);
        Parametros testParametros = parametrosList.get(parametrosList.size() - 1);
        assertThat(testParametros.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testParametros.getIdInstit()).isEqualTo(DEFAULT_ID_INSTIT);
        assertThat(testParametros.getNivel()).isEqualTo(DEFAULT_NIVEL);
        assertThat(testParametros.getUsuario()).isEqualTo(DEFAULT_USUARIO);
        assertThat(testParametros.getFechaMod()).isEqualTo(DEFAULT_FECHA_MOD);
    }

    @Test
    void createParametrosWithExistingId() throws Exception {
        // Create the Parametros with an existing ID
        parametros.setId(1L);

        int databaseSizeBeforeCreate = parametrosRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(parametros))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Parametros in the database
        List<Parametros> parametrosList = parametrosRepository.findAll().collectList().block();
        assertThat(parametrosList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = parametrosRepository.findAll().collectList().block().size();
        // set the field null
        parametros.setFecha(null);

        // Create the Parametros, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(parametros))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Parametros> parametrosList = parametrosRepository.findAll().collectList().block();
        assertThat(parametrosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkIdInstitIsRequired() throws Exception {
        int databaseSizeBeforeTest = parametrosRepository.findAll().collectList().block().size();
        // set the field null
        parametros.setIdInstit(null);

        // Create the Parametros, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(parametros))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Parametros> parametrosList = parametrosRepository.findAll().collectList().block();
        assertThat(parametrosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkNivelIsRequired() throws Exception {
        int databaseSizeBeforeTest = parametrosRepository.findAll().collectList().block().size();
        // set the field null
        parametros.setNivel(null);

        // Create the Parametros, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(parametros))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Parametros> parametrosList = parametrosRepository.findAll().collectList().block();
        assertThat(parametrosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllParametros() {
        // Initialize the database
        parametrosRepository.save(parametros).block();

        // Get all the parametrosList
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
            .value(hasItem(parametros.getId().intValue()))
            .jsonPath("$.[*].fecha")
            .value(hasItem(DEFAULT_FECHA.toString()))
            .jsonPath("$.[*].idInstit")
            .value(hasItem(DEFAULT_ID_INSTIT))
            .jsonPath("$.[*].nivel")
            .value(hasItem(DEFAULT_NIVEL))
            .jsonPath("$.[*].usuario")
            .value(hasItem(DEFAULT_USUARIO))
            .jsonPath("$.[*].fechaMod")
            .value(hasItem(DEFAULT_FECHA_MOD.toString()));
    }

    @Test
    void getParametros() {
        // Initialize the database
        parametrosRepository.save(parametros).block();

        // Get the parametros
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, parametros.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(parametros.getId().intValue()))
            .jsonPath("$.fecha")
            .value(is(DEFAULT_FECHA.toString()))
            .jsonPath("$.idInstit")
            .value(is(DEFAULT_ID_INSTIT))
            .jsonPath("$.nivel")
            .value(is(DEFAULT_NIVEL))
            .jsonPath("$.usuario")
            .value(is(DEFAULT_USUARIO))
            .jsonPath("$.fechaMod")
            .value(is(DEFAULT_FECHA_MOD.toString()));
    }

    @Test
    void getNonExistingParametros() {
        // Get the parametros
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingParametros() throws Exception {
        // Initialize the database
        parametrosRepository.save(parametros).block();

        int databaseSizeBeforeUpdate = parametrosRepository.findAll().collectList().block().size();

        // Update the parametros
        Parametros updatedParametros = parametrosRepository.findById(parametros.getId()).block();
        updatedParametros
            .fecha(UPDATED_FECHA)
            .idInstit(UPDATED_ID_INSTIT)
            .nivel(UPDATED_NIVEL)
            .usuario(UPDATED_USUARIO)
            .fechaMod(UPDATED_FECHA_MOD);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedParametros.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedParametros))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Parametros in the database
        List<Parametros> parametrosList = parametrosRepository.findAll().collectList().block();
        assertThat(parametrosList).hasSize(databaseSizeBeforeUpdate);
        Parametros testParametros = parametrosList.get(parametrosList.size() - 1);
        assertThat(testParametros.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testParametros.getIdInstit()).isEqualTo(UPDATED_ID_INSTIT);
        assertThat(testParametros.getNivel()).isEqualTo(UPDATED_NIVEL);
        assertThat(testParametros.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testParametros.getFechaMod()).isEqualTo(UPDATED_FECHA_MOD);
    }

    @Test
    void putNonExistingParametros() throws Exception {
        int databaseSizeBeforeUpdate = parametrosRepository.findAll().collectList().block().size();
        parametros.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, parametros.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(parametros))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Parametros in the database
        List<Parametros> parametrosList = parametrosRepository.findAll().collectList().block();
        assertThat(parametrosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchParametros() throws Exception {
        int databaseSizeBeforeUpdate = parametrosRepository.findAll().collectList().block().size();
        parametros.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(parametros))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Parametros in the database
        List<Parametros> parametrosList = parametrosRepository.findAll().collectList().block();
        assertThat(parametrosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamParametros() throws Exception {
        int databaseSizeBeforeUpdate = parametrosRepository.findAll().collectList().block().size();
        parametros.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(parametros))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Parametros in the database
        List<Parametros> parametrosList = parametrosRepository.findAll().collectList().block();
        assertThat(parametrosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateParametrosWithPatch() throws Exception {
        // Initialize the database
        parametrosRepository.save(parametros).block();

        int databaseSizeBeforeUpdate = parametrosRepository.findAll().collectList().block().size();

        // Update the parametros using partial update
        Parametros partialUpdatedParametros = new Parametros();
        partialUpdatedParametros.setId(parametros.getId());

        partialUpdatedParametros.fecha(UPDATED_FECHA).idInstit(UPDATED_ID_INSTIT).nivel(UPDATED_NIVEL).usuario(UPDATED_USUARIO);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedParametros.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedParametros))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Parametros in the database
        List<Parametros> parametrosList = parametrosRepository.findAll().collectList().block();
        assertThat(parametrosList).hasSize(databaseSizeBeforeUpdate);
        Parametros testParametros = parametrosList.get(parametrosList.size() - 1);
        assertThat(testParametros.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testParametros.getIdInstit()).isEqualTo(UPDATED_ID_INSTIT);
        assertThat(testParametros.getNivel()).isEqualTo(UPDATED_NIVEL);
        assertThat(testParametros.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testParametros.getFechaMod()).isEqualTo(DEFAULT_FECHA_MOD);
    }

    @Test
    void fullUpdateParametrosWithPatch() throws Exception {
        // Initialize the database
        parametrosRepository.save(parametros).block();

        int databaseSizeBeforeUpdate = parametrosRepository.findAll().collectList().block().size();

        // Update the parametros using partial update
        Parametros partialUpdatedParametros = new Parametros();
        partialUpdatedParametros.setId(parametros.getId());

        partialUpdatedParametros
            .fecha(UPDATED_FECHA)
            .idInstit(UPDATED_ID_INSTIT)
            .nivel(UPDATED_NIVEL)
            .usuario(UPDATED_USUARIO)
            .fechaMod(UPDATED_FECHA_MOD);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedParametros.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedParametros))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Parametros in the database
        List<Parametros> parametrosList = parametrosRepository.findAll().collectList().block();
        assertThat(parametrosList).hasSize(databaseSizeBeforeUpdate);
        Parametros testParametros = parametrosList.get(parametrosList.size() - 1);
        assertThat(testParametros.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testParametros.getIdInstit()).isEqualTo(UPDATED_ID_INSTIT);
        assertThat(testParametros.getNivel()).isEqualTo(UPDATED_NIVEL);
        assertThat(testParametros.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testParametros.getFechaMod()).isEqualTo(UPDATED_FECHA_MOD);
    }

    @Test
    void patchNonExistingParametros() throws Exception {
        int databaseSizeBeforeUpdate = parametrosRepository.findAll().collectList().block().size();
        parametros.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, parametros.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(parametros))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Parametros in the database
        List<Parametros> parametrosList = parametrosRepository.findAll().collectList().block();
        assertThat(parametrosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchParametros() throws Exception {
        int databaseSizeBeforeUpdate = parametrosRepository.findAll().collectList().block().size();
        parametros.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(parametros))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Parametros in the database
        List<Parametros> parametrosList = parametrosRepository.findAll().collectList().block();
        assertThat(parametrosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamParametros() throws Exception {
        int databaseSizeBeforeUpdate = parametrosRepository.findAll().collectList().block().size();
        parametros.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(parametros))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Parametros in the database
        List<Parametros> parametrosList = parametrosRepository.findAll().collectList().block();
        assertThat(parametrosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteParametros() {
        // Initialize the database
        parametrosRepository.save(parametros).block();

        int databaseSizeBeforeDelete = parametrosRepository.findAll().collectList().block().size();

        // Delete the parametros
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, parametros.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Parametros> parametrosList = parametrosRepository.findAll().collectList().block();
        assertThat(parametrosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
