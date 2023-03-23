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
import mx.conacyt.crip.proyecto03.domain.FTP;
import mx.conacyt.crip.proyecto03.repository.EntityManager;
import mx.conacyt.crip.proyecto03.repository.FTPRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link FTPResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class FTPResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_ID_INSTIT = 1;
    private static final Integer UPDATED_ID_INSTIT = 2;

    private static final Integer DEFAULT_NIVEL = 1;
    private static final Integer UPDATED_NIVEL = 2;

    private static final String DEFAULT_CLAVE = "AAAAAAAAAA";
    private static final String UPDATED_CLAVE = "BBBBBBBBBB";

    private static final String DEFAULT_CARPETA_FTP = "AAAAAAAAAA";
    private static final String UPDATED_CARPETA_FTP = "BBBBBBBBBB";

    private static final String DEFAULT_SUBCARPETA_FTP = "AAAAAAAAAA";
    private static final String UPDATED_SUBCARPETA_FTP = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION_FTP = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION_FTP = "BBBBBBBBBB";

    private static final String DEFAULT_IP_FTP = "AAAAAAAAAA";
    private static final String UPDATED_IP_FTP = "BBBBBBBBBB";

    private static final Integer DEFAULT_PUERTO = 1;
    private static final Integer UPDATED_PUERTO = 2;

    private static final String DEFAULT_USUARIO_FTP = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_FTP = "BBBBBBBBBB";

    private static final String DEFAULT_CONTRASENA = "AAAAAAAAAA";
    private static final String UPDATED_CONTRASENA = "BBBBBBBBBB";

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_MOD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_MOD = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/ftps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FTPRepository fTPRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private FTP fTP;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FTP createEntity(EntityManager em) {
        FTP fTP = new FTP()
            .fecha(DEFAULT_FECHA)
            .idInstit(DEFAULT_ID_INSTIT)
            .nivel(DEFAULT_NIVEL)
            .clave(DEFAULT_CLAVE)
            .carpetaFTP(DEFAULT_CARPETA_FTP)
            .subcarpetaFTP(DEFAULT_SUBCARPETA_FTP)
            .descripcionFTP(DEFAULT_DESCRIPCION_FTP)
            .ipFTP(DEFAULT_IP_FTP)
            .puerto(DEFAULT_PUERTO)
            .usuarioFTP(DEFAULT_USUARIO_FTP)
            .contrasena(DEFAULT_CONTRASENA)
            .usuario(DEFAULT_USUARIO)
            .fechaMod(DEFAULT_FECHA_MOD);
        return fTP;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FTP createUpdatedEntity(EntityManager em) {
        FTP fTP = new FTP()
            .fecha(UPDATED_FECHA)
            .idInstit(UPDATED_ID_INSTIT)
            .nivel(UPDATED_NIVEL)
            .clave(UPDATED_CLAVE)
            .carpetaFTP(UPDATED_CARPETA_FTP)
            .subcarpetaFTP(UPDATED_SUBCARPETA_FTP)
            .descripcionFTP(UPDATED_DESCRIPCION_FTP)
            .ipFTP(UPDATED_IP_FTP)
            .puerto(UPDATED_PUERTO)
            .usuarioFTP(UPDATED_USUARIO_FTP)
            .contrasena(UPDATED_CONTRASENA)
            .usuario(UPDATED_USUARIO)
            .fechaMod(UPDATED_FECHA_MOD);
        return fTP;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(FTP.class).block();
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
        fTP = createEntity(em);
    }

    @Test
    void createFTP() throws Exception {
        int databaseSizeBeforeCreate = fTPRepository.findAll().collectList().block().size();
        // Create the FTP
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(fTP))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the FTP in the database
        List<FTP> fTPList = fTPRepository.findAll().collectList().block();
        assertThat(fTPList).hasSize(databaseSizeBeforeCreate + 1);
        FTP testFTP = fTPList.get(fTPList.size() - 1);
        assertThat(testFTP.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testFTP.getIdInstit()).isEqualTo(DEFAULT_ID_INSTIT);
        assertThat(testFTP.getNivel()).isEqualTo(DEFAULT_NIVEL);
        assertThat(testFTP.getClave()).isEqualTo(DEFAULT_CLAVE);
        assertThat(testFTP.getCarpetaFTP()).isEqualTo(DEFAULT_CARPETA_FTP);
        assertThat(testFTP.getSubcarpetaFTP()).isEqualTo(DEFAULT_SUBCARPETA_FTP);
        assertThat(testFTP.getDescripcionFTP()).isEqualTo(DEFAULT_DESCRIPCION_FTP);
        assertThat(testFTP.getIpFTP()).isEqualTo(DEFAULT_IP_FTP);
        assertThat(testFTP.getPuerto()).isEqualTo(DEFAULT_PUERTO);
        assertThat(testFTP.getUsuarioFTP()).isEqualTo(DEFAULT_USUARIO_FTP);
        assertThat(testFTP.getContrasena()).isEqualTo(DEFAULT_CONTRASENA);
        assertThat(testFTP.getUsuario()).isEqualTo(DEFAULT_USUARIO);
        assertThat(testFTP.getFechaMod()).isEqualTo(DEFAULT_FECHA_MOD);
    }

    @Test
    void createFTPWithExistingId() throws Exception {
        // Create the FTP with an existing ID
        fTP.setId(1L);

        int databaseSizeBeforeCreate = fTPRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(fTP))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the FTP in the database
        List<FTP> fTPList = fTPRepository.findAll().collectList().block();
        assertThat(fTPList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = fTPRepository.findAll().collectList().block().size();
        // set the field null
        fTP.setFecha(null);

        // Create the FTP, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(fTP))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<FTP> fTPList = fTPRepository.findAll().collectList().block();
        assertThat(fTPList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkIdInstitIsRequired() throws Exception {
        int databaseSizeBeforeTest = fTPRepository.findAll().collectList().block().size();
        // set the field null
        fTP.setIdInstit(null);

        // Create the FTP, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(fTP))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<FTP> fTPList = fTPRepository.findAll().collectList().block();
        assertThat(fTPList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkNivelIsRequired() throws Exception {
        int databaseSizeBeforeTest = fTPRepository.findAll().collectList().block().size();
        // set the field null
        fTP.setNivel(null);

        // Create the FTP, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(fTP))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<FTP> fTPList = fTPRepository.findAll().collectList().block();
        assertThat(fTPList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllFTPS() {
        // Initialize the database
        fTPRepository.save(fTP).block();

        // Get all the fTPList
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
            .value(hasItem(fTP.getId().intValue()))
            .jsonPath("$.[*].fecha")
            .value(hasItem(DEFAULT_FECHA.toString()))
            .jsonPath("$.[*].idInstit")
            .value(hasItem(DEFAULT_ID_INSTIT))
            .jsonPath("$.[*].nivel")
            .value(hasItem(DEFAULT_NIVEL))
            .jsonPath("$.[*].clave")
            .value(hasItem(DEFAULT_CLAVE))
            .jsonPath("$.[*].carpetaFTP")
            .value(hasItem(DEFAULT_CARPETA_FTP))
            .jsonPath("$.[*].subcarpetaFTP")
            .value(hasItem(DEFAULT_SUBCARPETA_FTP))
            .jsonPath("$.[*].descripcionFTP")
            .value(hasItem(DEFAULT_DESCRIPCION_FTP))
            .jsonPath("$.[*].ipFTP")
            .value(hasItem(DEFAULT_IP_FTP))
            .jsonPath("$.[*].puerto")
            .value(hasItem(DEFAULT_PUERTO))
            .jsonPath("$.[*].usuarioFTP")
            .value(hasItem(DEFAULT_USUARIO_FTP))
            .jsonPath("$.[*].contrasena")
            .value(hasItem(DEFAULT_CONTRASENA))
            .jsonPath("$.[*].usuario")
            .value(hasItem(DEFAULT_USUARIO))
            .jsonPath("$.[*].fechaMod")
            .value(hasItem(DEFAULT_FECHA_MOD.toString()));
    }

    @Test
    void getFTP() {
        // Initialize the database
        fTPRepository.save(fTP).block();

        // Get the fTP
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, fTP.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(fTP.getId().intValue()))
            .jsonPath("$.fecha")
            .value(is(DEFAULT_FECHA.toString()))
            .jsonPath("$.idInstit")
            .value(is(DEFAULT_ID_INSTIT))
            .jsonPath("$.nivel")
            .value(is(DEFAULT_NIVEL))
            .jsonPath("$.clave")
            .value(is(DEFAULT_CLAVE))
            .jsonPath("$.carpetaFTP")
            .value(is(DEFAULT_CARPETA_FTP))
            .jsonPath("$.subcarpetaFTP")
            .value(is(DEFAULT_SUBCARPETA_FTP))
            .jsonPath("$.descripcionFTP")
            .value(is(DEFAULT_DESCRIPCION_FTP))
            .jsonPath("$.ipFTP")
            .value(is(DEFAULT_IP_FTP))
            .jsonPath("$.puerto")
            .value(is(DEFAULT_PUERTO))
            .jsonPath("$.usuarioFTP")
            .value(is(DEFAULT_USUARIO_FTP))
            .jsonPath("$.contrasena")
            .value(is(DEFAULT_CONTRASENA))
            .jsonPath("$.usuario")
            .value(is(DEFAULT_USUARIO))
            .jsonPath("$.fechaMod")
            .value(is(DEFAULT_FECHA_MOD.toString()));
    }

    @Test
    void getNonExistingFTP() {
        // Get the fTP
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingFTP() throws Exception {
        // Initialize the database
        fTPRepository.save(fTP).block();

        int databaseSizeBeforeUpdate = fTPRepository.findAll().collectList().block().size();

        // Update the fTP
        FTP updatedFTP = fTPRepository.findById(fTP.getId()).block();
        updatedFTP
            .fecha(UPDATED_FECHA)
            .idInstit(UPDATED_ID_INSTIT)
            .nivel(UPDATED_NIVEL)
            .clave(UPDATED_CLAVE)
            .carpetaFTP(UPDATED_CARPETA_FTP)
            .subcarpetaFTP(UPDATED_SUBCARPETA_FTP)
            .descripcionFTP(UPDATED_DESCRIPCION_FTP)
            .ipFTP(UPDATED_IP_FTP)
            .puerto(UPDATED_PUERTO)
            .usuarioFTP(UPDATED_USUARIO_FTP)
            .contrasena(UPDATED_CONTRASENA)
            .usuario(UPDATED_USUARIO)
            .fechaMod(UPDATED_FECHA_MOD);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedFTP.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedFTP))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the FTP in the database
        List<FTP> fTPList = fTPRepository.findAll().collectList().block();
        assertThat(fTPList).hasSize(databaseSizeBeforeUpdate);
        FTP testFTP = fTPList.get(fTPList.size() - 1);
        assertThat(testFTP.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testFTP.getIdInstit()).isEqualTo(UPDATED_ID_INSTIT);
        assertThat(testFTP.getNivel()).isEqualTo(UPDATED_NIVEL);
        assertThat(testFTP.getClave()).isEqualTo(UPDATED_CLAVE);
        assertThat(testFTP.getCarpetaFTP()).isEqualTo(UPDATED_CARPETA_FTP);
        assertThat(testFTP.getSubcarpetaFTP()).isEqualTo(UPDATED_SUBCARPETA_FTP);
        assertThat(testFTP.getDescripcionFTP()).isEqualTo(UPDATED_DESCRIPCION_FTP);
        assertThat(testFTP.getIpFTP()).isEqualTo(UPDATED_IP_FTP);
        assertThat(testFTP.getPuerto()).isEqualTo(UPDATED_PUERTO);
        assertThat(testFTP.getUsuarioFTP()).isEqualTo(UPDATED_USUARIO_FTP);
        assertThat(testFTP.getContrasena()).isEqualTo(UPDATED_CONTRASENA);
        assertThat(testFTP.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testFTP.getFechaMod()).isEqualTo(UPDATED_FECHA_MOD);
    }

    @Test
    void putNonExistingFTP() throws Exception {
        int databaseSizeBeforeUpdate = fTPRepository.findAll().collectList().block().size();
        fTP.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, fTP.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(fTP))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the FTP in the database
        List<FTP> fTPList = fTPRepository.findAll().collectList().block();
        assertThat(fTPList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchFTP() throws Exception {
        int databaseSizeBeforeUpdate = fTPRepository.findAll().collectList().block().size();
        fTP.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(fTP))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the FTP in the database
        List<FTP> fTPList = fTPRepository.findAll().collectList().block();
        assertThat(fTPList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamFTP() throws Exception {
        int databaseSizeBeforeUpdate = fTPRepository.findAll().collectList().block().size();
        fTP.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(fTP))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the FTP in the database
        List<FTP> fTPList = fTPRepository.findAll().collectList().block();
        assertThat(fTPList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateFTPWithPatch() throws Exception {
        // Initialize the database
        fTPRepository.save(fTP).block();

        int databaseSizeBeforeUpdate = fTPRepository.findAll().collectList().block().size();

        // Update the fTP using partial update
        FTP partialUpdatedFTP = new FTP();
        partialUpdatedFTP.setId(fTP.getId());

        partialUpdatedFTP
            .fecha(UPDATED_FECHA)
            .nivel(UPDATED_NIVEL)
            .clave(UPDATED_CLAVE)
            .subcarpetaFTP(UPDATED_SUBCARPETA_FTP)
            .ipFTP(UPDATED_IP_FTP)
            .usuarioFTP(UPDATED_USUARIO_FTP)
            .contrasena(UPDATED_CONTRASENA);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedFTP.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedFTP))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the FTP in the database
        List<FTP> fTPList = fTPRepository.findAll().collectList().block();
        assertThat(fTPList).hasSize(databaseSizeBeforeUpdate);
        FTP testFTP = fTPList.get(fTPList.size() - 1);
        assertThat(testFTP.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testFTP.getIdInstit()).isEqualTo(DEFAULT_ID_INSTIT);
        assertThat(testFTP.getNivel()).isEqualTo(UPDATED_NIVEL);
        assertThat(testFTP.getClave()).isEqualTo(UPDATED_CLAVE);
        assertThat(testFTP.getCarpetaFTP()).isEqualTo(DEFAULT_CARPETA_FTP);
        assertThat(testFTP.getSubcarpetaFTP()).isEqualTo(UPDATED_SUBCARPETA_FTP);
        assertThat(testFTP.getDescripcionFTP()).isEqualTo(DEFAULT_DESCRIPCION_FTP);
        assertThat(testFTP.getIpFTP()).isEqualTo(UPDATED_IP_FTP);
        assertThat(testFTP.getPuerto()).isEqualTo(DEFAULT_PUERTO);
        assertThat(testFTP.getUsuarioFTP()).isEqualTo(UPDATED_USUARIO_FTP);
        assertThat(testFTP.getContrasena()).isEqualTo(UPDATED_CONTRASENA);
        assertThat(testFTP.getUsuario()).isEqualTo(DEFAULT_USUARIO);
        assertThat(testFTP.getFechaMod()).isEqualTo(DEFAULT_FECHA_MOD);
    }

    @Test
    void fullUpdateFTPWithPatch() throws Exception {
        // Initialize the database
        fTPRepository.save(fTP).block();

        int databaseSizeBeforeUpdate = fTPRepository.findAll().collectList().block().size();

        // Update the fTP using partial update
        FTP partialUpdatedFTP = new FTP();
        partialUpdatedFTP.setId(fTP.getId());

        partialUpdatedFTP
            .fecha(UPDATED_FECHA)
            .idInstit(UPDATED_ID_INSTIT)
            .nivel(UPDATED_NIVEL)
            .clave(UPDATED_CLAVE)
            .carpetaFTP(UPDATED_CARPETA_FTP)
            .subcarpetaFTP(UPDATED_SUBCARPETA_FTP)
            .descripcionFTP(UPDATED_DESCRIPCION_FTP)
            .ipFTP(UPDATED_IP_FTP)
            .puerto(UPDATED_PUERTO)
            .usuarioFTP(UPDATED_USUARIO_FTP)
            .contrasena(UPDATED_CONTRASENA)
            .usuario(UPDATED_USUARIO)
            .fechaMod(UPDATED_FECHA_MOD);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedFTP.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedFTP))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the FTP in the database
        List<FTP> fTPList = fTPRepository.findAll().collectList().block();
        assertThat(fTPList).hasSize(databaseSizeBeforeUpdate);
        FTP testFTP = fTPList.get(fTPList.size() - 1);
        assertThat(testFTP.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testFTP.getIdInstit()).isEqualTo(UPDATED_ID_INSTIT);
        assertThat(testFTP.getNivel()).isEqualTo(UPDATED_NIVEL);
        assertThat(testFTP.getClave()).isEqualTo(UPDATED_CLAVE);
        assertThat(testFTP.getCarpetaFTP()).isEqualTo(UPDATED_CARPETA_FTP);
        assertThat(testFTP.getSubcarpetaFTP()).isEqualTo(UPDATED_SUBCARPETA_FTP);
        assertThat(testFTP.getDescripcionFTP()).isEqualTo(UPDATED_DESCRIPCION_FTP);
        assertThat(testFTP.getIpFTP()).isEqualTo(UPDATED_IP_FTP);
        assertThat(testFTP.getPuerto()).isEqualTo(UPDATED_PUERTO);
        assertThat(testFTP.getUsuarioFTP()).isEqualTo(UPDATED_USUARIO_FTP);
        assertThat(testFTP.getContrasena()).isEqualTo(UPDATED_CONTRASENA);
        assertThat(testFTP.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testFTP.getFechaMod()).isEqualTo(UPDATED_FECHA_MOD);
    }

    @Test
    void patchNonExistingFTP() throws Exception {
        int databaseSizeBeforeUpdate = fTPRepository.findAll().collectList().block().size();
        fTP.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, fTP.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(fTP))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the FTP in the database
        List<FTP> fTPList = fTPRepository.findAll().collectList().block();
        assertThat(fTPList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchFTP() throws Exception {
        int databaseSizeBeforeUpdate = fTPRepository.findAll().collectList().block().size();
        fTP.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(fTP))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the FTP in the database
        List<FTP> fTPList = fTPRepository.findAll().collectList().block();
        assertThat(fTPList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamFTP() throws Exception {
        int databaseSizeBeforeUpdate = fTPRepository.findAll().collectList().block().size();
        fTP.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(fTP))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the FTP in the database
        List<FTP> fTPList = fTPRepository.findAll().collectList().block();
        assertThat(fTPList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteFTP() {
        // Initialize the database
        fTPRepository.save(fTP).block();

        int databaseSizeBeforeDelete = fTPRepository.findAll().collectList().block().size();

        // Delete the fTP
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, fTP.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<FTP> fTPList = fTPRepository.findAll().collectList().block();
        assertThat(fTPList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
