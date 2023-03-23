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
import mx.conacyt.crip.proyecto03.domain.Estado;
import mx.conacyt.crip.proyecto03.domain.enumeration.Estatus;
import mx.conacyt.crip.proyecto03.repository.EntityManager;
import mx.conacyt.crip.proyecto03.repository.EstadoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link EstadoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class EstadoResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PAIS = "AAAAAAAAAA";
    private static final String UPDATED_PAIS = "BBBBBBBBBB";

    private static final String DEFAULT_ESTATDO = "AAAAAAAAAA";
    private static final String UPDATED_ESTATDO = "BBBBBBBBBB";

    private static final String DEFAULT_DESC_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_DESC_ESTADO = "BBBBBBBBBB";

    private static final Estatus DEFAULT_ESTATUS = Estatus.Activo;
    private static final Estatus UPDATED_ESTATUS = Estatus.Inactivo;

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_MOD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_MOD = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/estados";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Estado estado;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Estado createEntity(EntityManager em) {
        Estado estado = new Estado()
            .fecha(DEFAULT_FECHA)
            .pais(DEFAULT_PAIS)
            .estatdo(DEFAULT_ESTATDO)
            .descEstado(DEFAULT_DESC_ESTADO)
            .estatus(DEFAULT_ESTATUS)
            .usuario(DEFAULT_USUARIO)
            .fechaMod(DEFAULT_FECHA_MOD);
        return estado;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Estado createUpdatedEntity(EntityManager em) {
        Estado estado = new Estado()
            .fecha(UPDATED_FECHA)
            .pais(UPDATED_PAIS)
            .estatdo(UPDATED_ESTATDO)
            .descEstado(UPDATED_DESC_ESTADO)
            .estatus(UPDATED_ESTATUS)
            .usuario(UPDATED_USUARIO)
            .fechaMod(UPDATED_FECHA_MOD);
        return estado;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Estado.class).block();
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
        estado = createEntity(em);
    }

    @Test
    void createEstado() throws Exception {
        int databaseSizeBeforeCreate = estadoRepository.findAll().collectList().block().size();
        // Create the Estado
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(estado))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Estado in the database
        List<Estado> estadoList = estadoRepository.findAll().collectList().block();
        assertThat(estadoList).hasSize(databaseSizeBeforeCreate + 1);
        Estado testEstado = estadoList.get(estadoList.size() - 1);
        assertThat(testEstado.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testEstado.getPais()).isEqualTo(DEFAULT_PAIS);
        assertThat(testEstado.getEstatdo()).isEqualTo(DEFAULT_ESTATDO);
        assertThat(testEstado.getDescEstado()).isEqualTo(DEFAULT_DESC_ESTADO);
        assertThat(testEstado.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
        assertThat(testEstado.getUsuario()).isEqualTo(DEFAULT_USUARIO);
        assertThat(testEstado.getFechaMod()).isEqualTo(DEFAULT_FECHA_MOD);
    }

    @Test
    void createEstadoWithExistingId() throws Exception {
        // Create the Estado with an existing ID
        estado.setId(1L);

        int databaseSizeBeforeCreate = estadoRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(estado))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Estado in the database
        List<Estado> estadoList = estadoRepository.findAll().collectList().block();
        assertThat(estadoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllEstados() {
        // Initialize the database
        estadoRepository.save(estado).block();

        // Get all the estadoList
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
            .value(hasItem(estado.getId().intValue()))
            .jsonPath("$.[*].fecha")
            .value(hasItem(DEFAULT_FECHA.toString()))
            .jsonPath("$.[*].pais")
            .value(hasItem(DEFAULT_PAIS))
            .jsonPath("$.[*].estatdo")
            .value(hasItem(DEFAULT_ESTATDO))
            .jsonPath("$.[*].descEstado")
            .value(hasItem(DEFAULT_DESC_ESTADO))
            .jsonPath("$.[*].estatus")
            .value(hasItem(DEFAULT_ESTATUS.toString()))
            .jsonPath("$.[*].usuario")
            .value(hasItem(DEFAULT_USUARIO))
            .jsonPath("$.[*].fechaMod")
            .value(hasItem(DEFAULT_FECHA_MOD.toString()));
    }

    @Test
    void getEstado() {
        // Initialize the database
        estadoRepository.save(estado).block();

        // Get the estado
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, estado.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(estado.getId().intValue()))
            .jsonPath("$.fecha")
            .value(is(DEFAULT_FECHA.toString()))
            .jsonPath("$.pais")
            .value(is(DEFAULT_PAIS))
            .jsonPath("$.estatdo")
            .value(is(DEFAULT_ESTATDO))
            .jsonPath("$.descEstado")
            .value(is(DEFAULT_DESC_ESTADO))
            .jsonPath("$.estatus")
            .value(is(DEFAULT_ESTATUS.toString()))
            .jsonPath("$.usuario")
            .value(is(DEFAULT_USUARIO))
            .jsonPath("$.fechaMod")
            .value(is(DEFAULT_FECHA_MOD.toString()));
    }

    @Test
    void getNonExistingEstado() {
        // Get the estado
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingEstado() throws Exception {
        // Initialize the database
        estadoRepository.save(estado).block();

        int databaseSizeBeforeUpdate = estadoRepository.findAll().collectList().block().size();

        // Update the estado
        Estado updatedEstado = estadoRepository.findById(estado.getId()).block();
        updatedEstado
            .fecha(UPDATED_FECHA)
            .pais(UPDATED_PAIS)
            .estatdo(UPDATED_ESTATDO)
            .descEstado(UPDATED_DESC_ESTADO)
            .estatus(UPDATED_ESTATUS)
            .usuario(UPDATED_USUARIO)
            .fechaMod(UPDATED_FECHA_MOD);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedEstado.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedEstado))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Estado in the database
        List<Estado> estadoList = estadoRepository.findAll().collectList().block();
        assertThat(estadoList).hasSize(databaseSizeBeforeUpdate);
        Estado testEstado = estadoList.get(estadoList.size() - 1);
        assertThat(testEstado.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testEstado.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testEstado.getEstatdo()).isEqualTo(UPDATED_ESTATDO);
        assertThat(testEstado.getDescEstado()).isEqualTo(UPDATED_DESC_ESTADO);
        assertThat(testEstado.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testEstado.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testEstado.getFechaMod()).isEqualTo(UPDATED_FECHA_MOD);
    }

    @Test
    void putNonExistingEstado() throws Exception {
        int databaseSizeBeforeUpdate = estadoRepository.findAll().collectList().block().size();
        estado.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, estado.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(estado))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Estado in the database
        List<Estado> estadoList = estadoRepository.findAll().collectList().block();
        assertThat(estadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchEstado() throws Exception {
        int databaseSizeBeforeUpdate = estadoRepository.findAll().collectList().block().size();
        estado.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(estado))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Estado in the database
        List<Estado> estadoList = estadoRepository.findAll().collectList().block();
        assertThat(estadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamEstado() throws Exception {
        int databaseSizeBeforeUpdate = estadoRepository.findAll().collectList().block().size();
        estado.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(estado))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Estado in the database
        List<Estado> estadoList = estadoRepository.findAll().collectList().block();
        assertThat(estadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateEstadoWithPatch() throws Exception {
        // Initialize the database
        estadoRepository.save(estado).block();

        int databaseSizeBeforeUpdate = estadoRepository.findAll().collectList().block().size();

        // Update the estado using partial update
        Estado partialUpdatedEstado = new Estado();
        partialUpdatedEstado.setId(estado.getId());

        partialUpdatedEstado.estatdo(UPDATED_ESTATDO).fechaMod(UPDATED_FECHA_MOD);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedEstado.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedEstado))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Estado in the database
        List<Estado> estadoList = estadoRepository.findAll().collectList().block();
        assertThat(estadoList).hasSize(databaseSizeBeforeUpdate);
        Estado testEstado = estadoList.get(estadoList.size() - 1);
        assertThat(testEstado.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testEstado.getPais()).isEqualTo(DEFAULT_PAIS);
        assertThat(testEstado.getEstatdo()).isEqualTo(UPDATED_ESTATDO);
        assertThat(testEstado.getDescEstado()).isEqualTo(DEFAULT_DESC_ESTADO);
        assertThat(testEstado.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
        assertThat(testEstado.getUsuario()).isEqualTo(DEFAULT_USUARIO);
        assertThat(testEstado.getFechaMod()).isEqualTo(UPDATED_FECHA_MOD);
    }

    @Test
    void fullUpdateEstadoWithPatch() throws Exception {
        // Initialize the database
        estadoRepository.save(estado).block();

        int databaseSizeBeforeUpdate = estadoRepository.findAll().collectList().block().size();

        // Update the estado using partial update
        Estado partialUpdatedEstado = new Estado();
        partialUpdatedEstado.setId(estado.getId());

        partialUpdatedEstado
            .fecha(UPDATED_FECHA)
            .pais(UPDATED_PAIS)
            .estatdo(UPDATED_ESTATDO)
            .descEstado(UPDATED_DESC_ESTADO)
            .estatus(UPDATED_ESTATUS)
            .usuario(UPDATED_USUARIO)
            .fechaMod(UPDATED_FECHA_MOD);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedEstado.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedEstado))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Estado in the database
        List<Estado> estadoList = estadoRepository.findAll().collectList().block();
        assertThat(estadoList).hasSize(databaseSizeBeforeUpdate);
        Estado testEstado = estadoList.get(estadoList.size() - 1);
        assertThat(testEstado.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testEstado.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testEstado.getEstatdo()).isEqualTo(UPDATED_ESTATDO);
        assertThat(testEstado.getDescEstado()).isEqualTo(UPDATED_DESC_ESTADO);
        assertThat(testEstado.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testEstado.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testEstado.getFechaMod()).isEqualTo(UPDATED_FECHA_MOD);
    }

    @Test
    void patchNonExistingEstado() throws Exception {
        int databaseSizeBeforeUpdate = estadoRepository.findAll().collectList().block().size();
        estado.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, estado.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(estado))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Estado in the database
        List<Estado> estadoList = estadoRepository.findAll().collectList().block();
        assertThat(estadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchEstado() throws Exception {
        int databaseSizeBeforeUpdate = estadoRepository.findAll().collectList().block().size();
        estado.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(estado))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Estado in the database
        List<Estado> estadoList = estadoRepository.findAll().collectList().block();
        assertThat(estadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamEstado() throws Exception {
        int databaseSizeBeforeUpdate = estadoRepository.findAll().collectList().block().size();
        estado.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(estado))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Estado in the database
        List<Estado> estadoList = estadoRepository.findAll().collectList().block();
        assertThat(estadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteEstado() {
        // Initialize the database
        estadoRepository.save(estado).block();

        int databaseSizeBeforeDelete = estadoRepository.findAll().collectList().block().size();

        // Delete the estado
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, estado.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Estado> estadoList = estadoRepository.findAll().collectList().block();
        assertThat(estadoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
