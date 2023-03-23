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
import mx.conacyt.crip.proyecto03.domain.Funcionalidades;
import mx.conacyt.crip.proyecto03.domain.enumeration.Funcionalidad;
import mx.conacyt.crip.proyecto03.repository.EntityManager;
import mx.conacyt.crip.proyecto03.repository.FuncionalidadesRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link FuncionalidadesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class FuncionalidadesResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PERFIL = "AAAAAAAAAA";
    private static final String UPDATED_PERFIL = "BBBBBBBBBB";

    private static final Funcionalidad DEFAULT_FUNCIONALIDAD = Funcionalidad.Instituciones;
    private static final Funcionalidad UPDATED_FUNCIONALIDAD = Funcionalidad.Catalogos;

    private static final String DEFAULT_ALTA = "AAAAAAAAAA";
    private static final String UPDATED_ALTA = "BBBBBBBBBB";

    private static final String DEFAULT_EDITAR = "AAAAAAAAAA";
    private static final String UPDATED_EDITAR = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVAR_INACT = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVAR_INACT = "BBBBBBBBBB";

    private static final String DEFAULT_CONSULTA = "AAAAAAAAAA";
    private static final String UPDATED_CONSULTA = "BBBBBBBBBB";

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_MOD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_MOD = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/funcionalidades";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FuncionalidadesRepository funcionalidadesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Funcionalidades funcionalidades;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Funcionalidades createEntity(EntityManager em) {
        Funcionalidades funcionalidades = new Funcionalidades()
            .fecha(DEFAULT_FECHA)
            .perfil(DEFAULT_PERFIL)
            .funcionalidad(DEFAULT_FUNCIONALIDAD)
            .alta(DEFAULT_ALTA)
            .editar(DEFAULT_EDITAR)
            .activarInact(DEFAULT_ACTIVAR_INACT)
            .consulta(DEFAULT_CONSULTA)
            .usuario(DEFAULT_USUARIO)
            .fechaMod(DEFAULT_FECHA_MOD);
        return funcionalidades;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Funcionalidades createUpdatedEntity(EntityManager em) {
        Funcionalidades funcionalidades = new Funcionalidades()
            .fecha(UPDATED_FECHA)
            .perfil(UPDATED_PERFIL)
            .funcionalidad(UPDATED_FUNCIONALIDAD)
            .alta(UPDATED_ALTA)
            .editar(UPDATED_EDITAR)
            .activarInact(UPDATED_ACTIVAR_INACT)
            .consulta(UPDATED_CONSULTA)
            .usuario(UPDATED_USUARIO)
            .fechaMod(UPDATED_FECHA_MOD);
        return funcionalidades;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Funcionalidades.class).block();
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
        funcionalidades = createEntity(em);
    }

    @Test
    void createFuncionalidades() throws Exception {
        int databaseSizeBeforeCreate = funcionalidadesRepository.findAll().collectList().block().size();
        // Create the Funcionalidades
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(funcionalidades))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Funcionalidades in the database
        List<Funcionalidades> funcionalidadesList = funcionalidadesRepository.findAll().collectList().block();
        assertThat(funcionalidadesList).hasSize(databaseSizeBeforeCreate + 1);
        Funcionalidades testFuncionalidades = funcionalidadesList.get(funcionalidadesList.size() - 1);
        assertThat(testFuncionalidades.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testFuncionalidades.getPerfil()).isEqualTo(DEFAULT_PERFIL);
        assertThat(testFuncionalidades.getFuncionalidad()).isEqualTo(DEFAULT_FUNCIONALIDAD);
        assertThat(testFuncionalidades.getAlta()).isEqualTo(DEFAULT_ALTA);
        assertThat(testFuncionalidades.getEditar()).isEqualTo(DEFAULT_EDITAR);
        assertThat(testFuncionalidades.getActivarInact()).isEqualTo(DEFAULT_ACTIVAR_INACT);
        assertThat(testFuncionalidades.getConsulta()).isEqualTo(DEFAULT_CONSULTA);
        assertThat(testFuncionalidades.getUsuario()).isEqualTo(DEFAULT_USUARIO);
        assertThat(testFuncionalidades.getFechaMod()).isEqualTo(DEFAULT_FECHA_MOD);
    }

    @Test
    void createFuncionalidadesWithExistingId() throws Exception {
        // Create the Funcionalidades with an existing ID
        funcionalidades.setId(1L);

        int databaseSizeBeforeCreate = funcionalidadesRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(funcionalidades))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Funcionalidades in the database
        List<Funcionalidades> funcionalidadesList = funcionalidadesRepository.findAll().collectList().block();
        assertThat(funcionalidadesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = funcionalidadesRepository.findAll().collectList().block().size();
        // set the field null
        funcionalidades.setFecha(null);

        // Create the Funcionalidades, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(funcionalidades))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Funcionalidades> funcionalidadesList = funcionalidadesRepository.findAll().collectList().block();
        assertThat(funcionalidadesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkPerfilIsRequired() throws Exception {
        int databaseSizeBeforeTest = funcionalidadesRepository.findAll().collectList().block().size();
        // set the field null
        funcionalidades.setPerfil(null);

        // Create the Funcionalidades, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(funcionalidades))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Funcionalidades> funcionalidadesList = funcionalidadesRepository.findAll().collectList().block();
        assertThat(funcionalidadesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllFuncionalidades() {
        // Initialize the database
        funcionalidadesRepository.save(funcionalidades).block();

        // Get all the funcionalidadesList
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
            .value(hasItem(funcionalidades.getId().intValue()))
            .jsonPath("$.[*].fecha")
            .value(hasItem(DEFAULT_FECHA.toString()))
            .jsonPath("$.[*].perfil")
            .value(hasItem(DEFAULT_PERFIL))
            .jsonPath("$.[*].funcionalidad")
            .value(hasItem(DEFAULT_FUNCIONALIDAD.toString()))
            .jsonPath("$.[*].alta")
            .value(hasItem(DEFAULT_ALTA))
            .jsonPath("$.[*].editar")
            .value(hasItem(DEFAULT_EDITAR))
            .jsonPath("$.[*].activarInact")
            .value(hasItem(DEFAULT_ACTIVAR_INACT))
            .jsonPath("$.[*].consulta")
            .value(hasItem(DEFAULT_CONSULTA))
            .jsonPath("$.[*].usuario")
            .value(hasItem(DEFAULT_USUARIO))
            .jsonPath("$.[*].fechaMod")
            .value(hasItem(DEFAULT_FECHA_MOD.toString()));
    }

    @Test
    void getFuncionalidades() {
        // Initialize the database
        funcionalidadesRepository.save(funcionalidades).block();

        // Get the funcionalidades
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, funcionalidades.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(funcionalidades.getId().intValue()))
            .jsonPath("$.fecha")
            .value(is(DEFAULT_FECHA.toString()))
            .jsonPath("$.perfil")
            .value(is(DEFAULT_PERFIL))
            .jsonPath("$.funcionalidad")
            .value(is(DEFAULT_FUNCIONALIDAD.toString()))
            .jsonPath("$.alta")
            .value(is(DEFAULT_ALTA))
            .jsonPath("$.editar")
            .value(is(DEFAULT_EDITAR))
            .jsonPath("$.activarInact")
            .value(is(DEFAULT_ACTIVAR_INACT))
            .jsonPath("$.consulta")
            .value(is(DEFAULT_CONSULTA))
            .jsonPath("$.usuario")
            .value(is(DEFAULT_USUARIO))
            .jsonPath("$.fechaMod")
            .value(is(DEFAULT_FECHA_MOD.toString()));
    }

    @Test
    void getNonExistingFuncionalidades() {
        // Get the funcionalidades
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingFuncionalidades() throws Exception {
        // Initialize the database
        funcionalidadesRepository.save(funcionalidades).block();

        int databaseSizeBeforeUpdate = funcionalidadesRepository.findAll().collectList().block().size();

        // Update the funcionalidades
        Funcionalidades updatedFuncionalidades = funcionalidadesRepository.findById(funcionalidades.getId()).block();
        updatedFuncionalidades
            .fecha(UPDATED_FECHA)
            .perfil(UPDATED_PERFIL)
            .funcionalidad(UPDATED_FUNCIONALIDAD)
            .alta(UPDATED_ALTA)
            .editar(UPDATED_EDITAR)
            .activarInact(UPDATED_ACTIVAR_INACT)
            .consulta(UPDATED_CONSULTA)
            .usuario(UPDATED_USUARIO)
            .fechaMod(UPDATED_FECHA_MOD);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedFuncionalidades.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedFuncionalidades))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Funcionalidades in the database
        List<Funcionalidades> funcionalidadesList = funcionalidadesRepository.findAll().collectList().block();
        assertThat(funcionalidadesList).hasSize(databaseSizeBeforeUpdate);
        Funcionalidades testFuncionalidades = funcionalidadesList.get(funcionalidadesList.size() - 1);
        assertThat(testFuncionalidades.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testFuncionalidades.getPerfil()).isEqualTo(UPDATED_PERFIL);
        assertThat(testFuncionalidades.getFuncionalidad()).isEqualTo(UPDATED_FUNCIONALIDAD);
        assertThat(testFuncionalidades.getAlta()).isEqualTo(UPDATED_ALTA);
        assertThat(testFuncionalidades.getEditar()).isEqualTo(UPDATED_EDITAR);
        assertThat(testFuncionalidades.getActivarInact()).isEqualTo(UPDATED_ACTIVAR_INACT);
        assertThat(testFuncionalidades.getConsulta()).isEqualTo(UPDATED_CONSULTA);
        assertThat(testFuncionalidades.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testFuncionalidades.getFechaMod()).isEqualTo(UPDATED_FECHA_MOD);
    }

    @Test
    void putNonExistingFuncionalidades() throws Exception {
        int databaseSizeBeforeUpdate = funcionalidadesRepository.findAll().collectList().block().size();
        funcionalidades.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, funcionalidades.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(funcionalidades))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Funcionalidades in the database
        List<Funcionalidades> funcionalidadesList = funcionalidadesRepository.findAll().collectList().block();
        assertThat(funcionalidadesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchFuncionalidades() throws Exception {
        int databaseSizeBeforeUpdate = funcionalidadesRepository.findAll().collectList().block().size();
        funcionalidades.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(funcionalidades))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Funcionalidades in the database
        List<Funcionalidades> funcionalidadesList = funcionalidadesRepository.findAll().collectList().block();
        assertThat(funcionalidadesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamFuncionalidades() throws Exception {
        int databaseSizeBeforeUpdate = funcionalidadesRepository.findAll().collectList().block().size();
        funcionalidades.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(funcionalidades))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Funcionalidades in the database
        List<Funcionalidades> funcionalidadesList = funcionalidadesRepository.findAll().collectList().block();
        assertThat(funcionalidadesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateFuncionalidadesWithPatch() throws Exception {
        // Initialize the database
        funcionalidadesRepository.save(funcionalidades).block();

        int databaseSizeBeforeUpdate = funcionalidadesRepository.findAll().collectList().block().size();

        // Update the funcionalidades using partial update
        Funcionalidades partialUpdatedFuncionalidades = new Funcionalidades();
        partialUpdatedFuncionalidades.setId(funcionalidades.getId());

        partialUpdatedFuncionalidades.funcionalidad(UPDATED_FUNCIONALIDAD).consulta(UPDATED_CONSULTA);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedFuncionalidades.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedFuncionalidades))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Funcionalidades in the database
        List<Funcionalidades> funcionalidadesList = funcionalidadesRepository.findAll().collectList().block();
        assertThat(funcionalidadesList).hasSize(databaseSizeBeforeUpdate);
        Funcionalidades testFuncionalidades = funcionalidadesList.get(funcionalidadesList.size() - 1);
        assertThat(testFuncionalidades.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testFuncionalidades.getPerfil()).isEqualTo(DEFAULT_PERFIL);
        assertThat(testFuncionalidades.getFuncionalidad()).isEqualTo(UPDATED_FUNCIONALIDAD);
        assertThat(testFuncionalidades.getAlta()).isEqualTo(DEFAULT_ALTA);
        assertThat(testFuncionalidades.getEditar()).isEqualTo(DEFAULT_EDITAR);
        assertThat(testFuncionalidades.getActivarInact()).isEqualTo(DEFAULT_ACTIVAR_INACT);
        assertThat(testFuncionalidades.getConsulta()).isEqualTo(UPDATED_CONSULTA);
        assertThat(testFuncionalidades.getUsuario()).isEqualTo(DEFAULT_USUARIO);
        assertThat(testFuncionalidades.getFechaMod()).isEqualTo(DEFAULT_FECHA_MOD);
    }

    @Test
    void fullUpdateFuncionalidadesWithPatch() throws Exception {
        // Initialize the database
        funcionalidadesRepository.save(funcionalidades).block();

        int databaseSizeBeforeUpdate = funcionalidadesRepository.findAll().collectList().block().size();

        // Update the funcionalidades using partial update
        Funcionalidades partialUpdatedFuncionalidades = new Funcionalidades();
        partialUpdatedFuncionalidades.setId(funcionalidades.getId());

        partialUpdatedFuncionalidades
            .fecha(UPDATED_FECHA)
            .perfil(UPDATED_PERFIL)
            .funcionalidad(UPDATED_FUNCIONALIDAD)
            .alta(UPDATED_ALTA)
            .editar(UPDATED_EDITAR)
            .activarInact(UPDATED_ACTIVAR_INACT)
            .consulta(UPDATED_CONSULTA)
            .usuario(UPDATED_USUARIO)
            .fechaMod(UPDATED_FECHA_MOD);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedFuncionalidades.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedFuncionalidades))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Funcionalidades in the database
        List<Funcionalidades> funcionalidadesList = funcionalidadesRepository.findAll().collectList().block();
        assertThat(funcionalidadesList).hasSize(databaseSizeBeforeUpdate);
        Funcionalidades testFuncionalidades = funcionalidadesList.get(funcionalidadesList.size() - 1);
        assertThat(testFuncionalidades.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testFuncionalidades.getPerfil()).isEqualTo(UPDATED_PERFIL);
        assertThat(testFuncionalidades.getFuncionalidad()).isEqualTo(UPDATED_FUNCIONALIDAD);
        assertThat(testFuncionalidades.getAlta()).isEqualTo(UPDATED_ALTA);
        assertThat(testFuncionalidades.getEditar()).isEqualTo(UPDATED_EDITAR);
        assertThat(testFuncionalidades.getActivarInact()).isEqualTo(UPDATED_ACTIVAR_INACT);
        assertThat(testFuncionalidades.getConsulta()).isEqualTo(UPDATED_CONSULTA);
        assertThat(testFuncionalidades.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testFuncionalidades.getFechaMod()).isEqualTo(UPDATED_FECHA_MOD);
    }

    @Test
    void patchNonExistingFuncionalidades() throws Exception {
        int databaseSizeBeforeUpdate = funcionalidadesRepository.findAll().collectList().block().size();
        funcionalidades.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, funcionalidades.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(funcionalidades))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Funcionalidades in the database
        List<Funcionalidades> funcionalidadesList = funcionalidadesRepository.findAll().collectList().block();
        assertThat(funcionalidadesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchFuncionalidades() throws Exception {
        int databaseSizeBeforeUpdate = funcionalidadesRepository.findAll().collectList().block().size();
        funcionalidades.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(funcionalidades))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Funcionalidades in the database
        List<Funcionalidades> funcionalidadesList = funcionalidadesRepository.findAll().collectList().block();
        assertThat(funcionalidadesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamFuncionalidades() throws Exception {
        int databaseSizeBeforeUpdate = funcionalidadesRepository.findAll().collectList().block().size();
        funcionalidades.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(funcionalidades))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Funcionalidades in the database
        List<Funcionalidades> funcionalidadesList = funcionalidadesRepository.findAll().collectList().block();
        assertThat(funcionalidadesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteFuncionalidades() {
        // Initialize the database
        funcionalidadesRepository.save(funcionalidades).block();

        int databaseSizeBeforeDelete = funcionalidadesRepository.findAll().collectList().block().size();

        // Delete the funcionalidades
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, funcionalidades.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Funcionalidades> funcionalidadesList = funcionalidadesRepository.findAll().collectList().block();
        assertThat(funcionalidadesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
