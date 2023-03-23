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
import mx.conacyt.crip.proyecto03.domain.Notificaciones;
import mx.conacyt.crip.proyecto03.domain.enumeration.TipoNot;
import mx.conacyt.crip.proyecto03.repository.EntityManager;
import mx.conacyt.crip.proyecto03.repository.NotificacionesRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link NotificacionesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class NotificacionesResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_ID_INSTIT = 1;
    private static final Integer UPDATED_ID_INSTIT = 2;

    private static final Integer DEFAULT_NIVEL = 1;
    private static final Integer UPDATED_NIVEL = 2;

    private static final TipoNot DEFAULT_TIPO_NOT = TipoNot.NOTEXP;
    private static final TipoNot UPDATED_TIPO_NOT = TipoNot.CFDI;

    private static final String DEFAULT_CLAVE = "AAAAAAAAAA";
    private static final String UPDATED_CLAVE = "BBBBBBBBBB";

    private static final String DEFAULT_ASUNTO = "AAAAAAAAAA";
    private static final String UPDATED_ASUNTO = "BBBBBBBBBB";

    private static final Long DEFAULT_TEXTO = 1L;
    private static final Long UPDATED_TEXTO = 2L;

    private static final String DEFAULT_PIE_PAGINA = "AAAAAAAAAA";
    private static final String UPDATED_PIE_PAGINA = "BBBBBBBBBB";

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_MOD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_MOD = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/notificaciones";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NotificacionesRepository notificacionesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Notificaciones notificaciones;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notificaciones createEntity(EntityManager em) {
        Notificaciones notificaciones = new Notificaciones()
            .fecha(DEFAULT_FECHA)
            .idInstit(DEFAULT_ID_INSTIT)
            .nivel(DEFAULT_NIVEL)
            .tipoNot(DEFAULT_TIPO_NOT)
            .clave(DEFAULT_CLAVE)
            .asunto(DEFAULT_ASUNTO)
            .texto(DEFAULT_TEXTO)
            .piePagina(DEFAULT_PIE_PAGINA)
            .usuario(DEFAULT_USUARIO)
            .fechaMod(DEFAULT_FECHA_MOD);
        return notificaciones;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notificaciones createUpdatedEntity(EntityManager em) {
        Notificaciones notificaciones = new Notificaciones()
            .fecha(UPDATED_FECHA)
            .idInstit(UPDATED_ID_INSTIT)
            .nivel(UPDATED_NIVEL)
            .tipoNot(UPDATED_TIPO_NOT)
            .clave(UPDATED_CLAVE)
            .asunto(UPDATED_ASUNTO)
            .texto(UPDATED_TEXTO)
            .piePagina(UPDATED_PIE_PAGINA)
            .usuario(UPDATED_USUARIO)
            .fechaMod(UPDATED_FECHA_MOD);
        return notificaciones;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Notificaciones.class).block();
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
        notificaciones = createEntity(em);
    }

    @Test
    void createNotificaciones() throws Exception {
        int databaseSizeBeforeCreate = notificacionesRepository.findAll().collectList().block().size();
        // Create the Notificaciones
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(notificaciones))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Notificaciones in the database
        List<Notificaciones> notificacionesList = notificacionesRepository.findAll().collectList().block();
        assertThat(notificacionesList).hasSize(databaseSizeBeforeCreate + 1);
        Notificaciones testNotificaciones = notificacionesList.get(notificacionesList.size() - 1);
        assertThat(testNotificaciones.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testNotificaciones.getIdInstit()).isEqualTo(DEFAULT_ID_INSTIT);
        assertThat(testNotificaciones.getNivel()).isEqualTo(DEFAULT_NIVEL);
        assertThat(testNotificaciones.getTipoNot()).isEqualTo(DEFAULT_TIPO_NOT);
        assertThat(testNotificaciones.getClave()).isEqualTo(DEFAULT_CLAVE);
        assertThat(testNotificaciones.getAsunto()).isEqualTo(DEFAULT_ASUNTO);
        assertThat(testNotificaciones.getTexto()).isEqualTo(DEFAULT_TEXTO);
        assertThat(testNotificaciones.getPiePagina()).isEqualTo(DEFAULT_PIE_PAGINA);
        assertThat(testNotificaciones.getUsuario()).isEqualTo(DEFAULT_USUARIO);
        assertThat(testNotificaciones.getFechaMod()).isEqualTo(DEFAULT_FECHA_MOD);
    }

    @Test
    void createNotificacionesWithExistingId() throws Exception {
        // Create the Notificaciones with an existing ID
        notificaciones.setId(1L);

        int databaseSizeBeforeCreate = notificacionesRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(notificaciones))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Notificaciones in the database
        List<Notificaciones> notificacionesList = notificacionesRepository.findAll().collectList().block();
        assertThat(notificacionesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificacionesRepository.findAll().collectList().block().size();
        // set the field null
        notificaciones.setFecha(null);

        // Create the Notificaciones, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(notificaciones))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Notificaciones> notificacionesList = notificacionesRepository.findAll().collectList().block();
        assertThat(notificacionesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkIdInstitIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificacionesRepository.findAll().collectList().block().size();
        // set the field null
        notificaciones.setIdInstit(null);

        // Create the Notificaciones, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(notificaciones))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Notificaciones> notificacionesList = notificacionesRepository.findAll().collectList().block();
        assertThat(notificacionesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkNivelIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificacionesRepository.findAll().collectList().block().size();
        // set the field null
        notificaciones.setNivel(null);

        // Create the Notificaciones, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(notificaciones))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Notificaciones> notificacionesList = notificacionesRepository.findAll().collectList().block();
        assertThat(notificacionesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllNotificaciones() {
        // Initialize the database
        notificacionesRepository.save(notificaciones).block();

        // Get all the notificacionesList
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
            .value(hasItem(notificaciones.getId().intValue()))
            .jsonPath("$.[*].fecha")
            .value(hasItem(DEFAULT_FECHA.toString()))
            .jsonPath("$.[*].idInstit")
            .value(hasItem(DEFAULT_ID_INSTIT))
            .jsonPath("$.[*].nivel")
            .value(hasItem(DEFAULT_NIVEL))
            .jsonPath("$.[*].tipoNot")
            .value(hasItem(DEFAULT_TIPO_NOT.toString()))
            .jsonPath("$.[*].clave")
            .value(hasItem(DEFAULT_CLAVE))
            .jsonPath("$.[*].asunto")
            .value(hasItem(DEFAULT_ASUNTO))
            .jsonPath("$.[*].texto")
            .value(hasItem(DEFAULT_TEXTO.intValue()))
            .jsonPath("$.[*].piePagina")
            .value(hasItem(DEFAULT_PIE_PAGINA))
            .jsonPath("$.[*].usuario")
            .value(hasItem(DEFAULT_USUARIO))
            .jsonPath("$.[*].fechaMod")
            .value(hasItem(DEFAULT_FECHA_MOD.toString()));
    }

    @Test
    void getNotificaciones() {
        // Initialize the database
        notificacionesRepository.save(notificaciones).block();

        // Get the notificaciones
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, notificaciones.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(notificaciones.getId().intValue()))
            .jsonPath("$.fecha")
            .value(is(DEFAULT_FECHA.toString()))
            .jsonPath("$.idInstit")
            .value(is(DEFAULT_ID_INSTIT))
            .jsonPath("$.nivel")
            .value(is(DEFAULT_NIVEL))
            .jsonPath("$.tipoNot")
            .value(is(DEFAULT_TIPO_NOT.toString()))
            .jsonPath("$.clave")
            .value(is(DEFAULT_CLAVE))
            .jsonPath("$.asunto")
            .value(is(DEFAULT_ASUNTO))
            .jsonPath("$.texto")
            .value(is(DEFAULT_TEXTO.intValue()))
            .jsonPath("$.piePagina")
            .value(is(DEFAULT_PIE_PAGINA))
            .jsonPath("$.usuario")
            .value(is(DEFAULT_USUARIO))
            .jsonPath("$.fechaMod")
            .value(is(DEFAULT_FECHA_MOD.toString()));
    }

    @Test
    void getNonExistingNotificaciones() {
        // Get the notificaciones
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingNotificaciones() throws Exception {
        // Initialize the database
        notificacionesRepository.save(notificaciones).block();

        int databaseSizeBeforeUpdate = notificacionesRepository.findAll().collectList().block().size();

        // Update the notificaciones
        Notificaciones updatedNotificaciones = notificacionesRepository.findById(notificaciones.getId()).block();
        updatedNotificaciones
            .fecha(UPDATED_FECHA)
            .idInstit(UPDATED_ID_INSTIT)
            .nivel(UPDATED_NIVEL)
            .tipoNot(UPDATED_TIPO_NOT)
            .clave(UPDATED_CLAVE)
            .asunto(UPDATED_ASUNTO)
            .texto(UPDATED_TEXTO)
            .piePagina(UPDATED_PIE_PAGINA)
            .usuario(UPDATED_USUARIO)
            .fechaMod(UPDATED_FECHA_MOD);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedNotificaciones.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedNotificaciones))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Notificaciones in the database
        List<Notificaciones> notificacionesList = notificacionesRepository.findAll().collectList().block();
        assertThat(notificacionesList).hasSize(databaseSizeBeforeUpdate);
        Notificaciones testNotificaciones = notificacionesList.get(notificacionesList.size() - 1);
        assertThat(testNotificaciones.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testNotificaciones.getIdInstit()).isEqualTo(UPDATED_ID_INSTIT);
        assertThat(testNotificaciones.getNivel()).isEqualTo(UPDATED_NIVEL);
        assertThat(testNotificaciones.getTipoNot()).isEqualTo(UPDATED_TIPO_NOT);
        assertThat(testNotificaciones.getClave()).isEqualTo(UPDATED_CLAVE);
        assertThat(testNotificaciones.getAsunto()).isEqualTo(UPDATED_ASUNTO);
        assertThat(testNotificaciones.getTexto()).isEqualTo(UPDATED_TEXTO);
        assertThat(testNotificaciones.getPiePagina()).isEqualTo(UPDATED_PIE_PAGINA);
        assertThat(testNotificaciones.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testNotificaciones.getFechaMod()).isEqualTo(UPDATED_FECHA_MOD);
    }

    @Test
    void putNonExistingNotificaciones() throws Exception {
        int databaseSizeBeforeUpdate = notificacionesRepository.findAll().collectList().block().size();
        notificaciones.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, notificaciones.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(notificaciones))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Notificaciones in the database
        List<Notificaciones> notificacionesList = notificacionesRepository.findAll().collectList().block();
        assertThat(notificacionesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchNotificaciones() throws Exception {
        int databaseSizeBeforeUpdate = notificacionesRepository.findAll().collectList().block().size();
        notificaciones.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(notificaciones))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Notificaciones in the database
        List<Notificaciones> notificacionesList = notificacionesRepository.findAll().collectList().block();
        assertThat(notificacionesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamNotificaciones() throws Exception {
        int databaseSizeBeforeUpdate = notificacionesRepository.findAll().collectList().block().size();
        notificaciones.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(notificaciones))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Notificaciones in the database
        List<Notificaciones> notificacionesList = notificacionesRepository.findAll().collectList().block();
        assertThat(notificacionesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateNotificacionesWithPatch() throws Exception {
        // Initialize the database
        notificacionesRepository.save(notificaciones).block();

        int databaseSizeBeforeUpdate = notificacionesRepository.findAll().collectList().block().size();

        // Update the notificaciones using partial update
        Notificaciones partialUpdatedNotificaciones = new Notificaciones();
        partialUpdatedNotificaciones.setId(notificaciones.getId());

        partialUpdatedNotificaciones
            .fecha(UPDATED_FECHA)
            .nivel(UPDATED_NIVEL)
            .tipoNot(UPDATED_TIPO_NOT)
            .piePagina(UPDATED_PIE_PAGINA)
            .usuario(UPDATED_USUARIO)
            .fechaMod(UPDATED_FECHA_MOD);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedNotificaciones.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedNotificaciones))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Notificaciones in the database
        List<Notificaciones> notificacionesList = notificacionesRepository.findAll().collectList().block();
        assertThat(notificacionesList).hasSize(databaseSizeBeforeUpdate);
        Notificaciones testNotificaciones = notificacionesList.get(notificacionesList.size() - 1);
        assertThat(testNotificaciones.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testNotificaciones.getIdInstit()).isEqualTo(DEFAULT_ID_INSTIT);
        assertThat(testNotificaciones.getNivel()).isEqualTo(UPDATED_NIVEL);
        assertThat(testNotificaciones.getTipoNot()).isEqualTo(UPDATED_TIPO_NOT);
        assertThat(testNotificaciones.getClave()).isEqualTo(DEFAULT_CLAVE);
        assertThat(testNotificaciones.getAsunto()).isEqualTo(DEFAULT_ASUNTO);
        assertThat(testNotificaciones.getTexto()).isEqualTo(DEFAULT_TEXTO);
        assertThat(testNotificaciones.getPiePagina()).isEqualTo(UPDATED_PIE_PAGINA);
        assertThat(testNotificaciones.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testNotificaciones.getFechaMod()).isEqualTo(UPDATED_FECHA_MOD);
    }

    @Test
    void fullUpdateNotificacionesWithPatch() throws Exception {
        // Initialize the database
        notificacionesRepository.save(notificaciones).block();

        int databaseSizeBeforeUpdate = notificacionesRepository.findAll().collectList().block().size();

        // Update the notificaciones using partial update
        Notificaciones partialUpdatedNotificaciones = new Notificaciones();
        partialUpdatedNotificaciones.setId(notificaciones.getId());

        partialUpdatedNotificaciones
            .fecha(UPDATED_FECHA)
            .idInstit(UPDATED_ID_INSTIT)
            .nivel(UPDATED_NIVEL)
            .tipoNot(UPDATED_TIPO_NOT)
            .clave(UPDATED_CLAVE)
            .asunto(UPDATED_ASUNTO)
            .texto(UPDATED_TEXTO)
            .piePagina(UPDATED_PIE_PAGINA)
            .usuario(UPDATED_USUARIO)
            .fechaMod(UPDATED_FECHA_MOD);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedNotificaciones.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedNotificaciones))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Notificaciones in the database
        List<Notificaciones> notificacionesList = notificacionesRepository.findAll().collectList().block();
        assertThat(notificacionesList).hasSize(databaseSizeBeforeUpdate);
        Notificaciones testNotificaciones = notificacionesList.get(notificacionesList.size() - 1);
        assertThat(testNotificaciones.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testNotificaciones.getIdInstit()).isEqualTo(UPDATED_ID_INSTIT);
        assertThat(testNotificaciones.getNivel()).isEqualTo(UPDATED_NIVEL);
        assertThat(testNotificaciones.getTipoNot()).isEqualTo(UPDATED_TIPO_NOT);
        assertThat(testNotificaciones.getClave()).isEqualTo(UPDATED_CLAVE);
        assertThat(testNotificaciones.getAsunto()).isEqualTo(UPDATED_ASUNTO);
        assertThat(testNotificaciones.getTexto()).isEqualTo(UPDATED_TEXTO);
        assertThat(testNotificaciones.getPiePagina()).isEqualTo(UPDATED_PIE_PAGINA);
        assertThat(testNotificaciones.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testNotificaciones.getFechaMod()).isEqualTo(UPDATED_FECHA_MOD);
    }

    @Test
    void patchNonExistingNotificaciones() throws Exception {
        int databaseSizeBeforeUpdate = notificacionesRepository.findAll().collectList().block().size();
        notificaciones.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, notificaciones.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(notificaciones))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Notificaciones in the database
        List<Notificaciones> notificacionesList = notificacionesRepository.findAll().collectList().block();
        assertThat(notificacionesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchNotificaciones() throws Exception {
        int databaseSizeBeforeUpdate = notificacionesRepository.findAll().collectList().block().size();
        notificaciones.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(notificaciones))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Notificaciones in the database
        List<Notificaciones> notificacionesList = notificacionesRepository.findAll().collectList().block();
        assertThat(notificacionesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamNotificaciones() throws Exception {
        int databaseSizeBeforeUpdate = notificacionesRepository.findAll().collectList().block().size();
        notificaciones.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(notificaciones))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Notificaciones in the database
        List<Notificaciones> notificacionesList = notificacionesRepository.findAll().collectList().block();
        assertThat(notificacionesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteNotificaciones() {
        // Initialize the database
        notificacionesRepository.save(notificaciones).block();

        int databaseSizeBeforeDelete = notificacionesRepository.findAll().collectList().block().size();

        // Delete the notificaciones
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, notificaciones.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Notificaciones> notificacionesList = notificacionesRepository.findAll().collectList().block();
        assertThat(notificacionesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
