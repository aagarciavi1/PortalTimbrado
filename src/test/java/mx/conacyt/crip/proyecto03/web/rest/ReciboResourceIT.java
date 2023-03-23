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
import mx.conacyt.crip.proyecto03.domain.Recibo;
import mx.conacyt.crip.proyecto03.repository.EntityManager;
import mx.conacyt.crip.proyecto03.repository.ReciboRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link ReciboResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class ReciboResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_ID_INSTIT = 1;
    private static final Integer UPDATED_ID_INSTIT = 2;

    private static final Integer DEFAULT_NIVEL = 1;
    private static final Integer UPDATED_NIVEL = 2;

    private static final String DEFAULT_TIPO_CFDI = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_CFDI = "BBBBBBBBBB";

    private static final String DEFAULT_CLAVE = "AAAAAAAAAA";
    private static final String UPDATED_CLAVE = "BBBBBBBBBB";

    private static final String DEFAULT_ENVIO_CON_CFDI = "AAAAAAAAAA";
    private static final String UPDATED_ENVIO_CON_CFDI = "BBBBBBBBBB";

    private static final String DEFAULT_ENVIO_SIN_CFDI = "AAAAAAAAAA";
    private static final String UPDATED_ENVIO_SIN_CFDI = "BBBBBBBBBB";

    private static final String DEFAULT_APLICA_LEYENDA = "AAAAAAAAAA";
    private static final String UPDATED_APLICA_LEYENDA = "BBBBBBBBBB";

    private static final Long DEFAULT_LEYENDA = 1L;
    private static final Long UPDATED_LEYENDA = 2L;

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_MOD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_MOD = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/recibos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ReciboRepository reciboRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Recibo recibo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recibo createEntity(EntityManager em) {
        Recibo recibo = new Recibo()
            .fecha(DEFAULT_FECHA)
            .idInstit(DEFAULT_ID_INSTIT)
            .nivel(DEFAULT_NIVEL)
            .tipoCFDI(DEFAULT_TIPO_CFDI)
            .clave(DEFAULT_CLAVE)
            .envioConCFDI(DEFAULT_ENVIO_CON_CFDI)
            .envioSinCFDI(DEFAULT_ENVIO_SIN_CFDI)
            .aplicaLeyenda(DEFAULT_APLICA_LEYENDA)
            .leyenda(DEFAULT_LEYENDA)
            .usuario(DEFAULT_USUARIO)
            .fechaMod(DEFAULT_FECHA_MOD);
        return recibo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recibo createUpdatedEntity(EntityManager em) {
        Recibo recibo = new Recibo()
            .fecha(UPDATED_FECHA)
            .idInstit(UPDATED_ID_INSTIT)
            .nivel(UPDATED_NIVEL)
            .tipoCFDI(UPDATED_TIPO_CFDI)
            .clave(UPDATED_CLAVE)
            .envioConCFDI(UPDATED_ENVIO_CON_CFDI)
            .envioSinCFDI(UPDATED_ENVIO_SIN_CFDI)
            .aplicaLeyenda(UPDATED_APLICA_LEYENDA)
            .leyenda(UPDATED_LEYENDA)
            .usuario(UPDATED_USUARIO)
            .fechaMod(UPDATED_FECHA_MOD);
        return recibo;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Recibo.class).block();
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
        recibo = createEntity(em);
    }

    @Test
    void createRecibo() throws Exception {
        int databaseSizeBeforeCreate = reciboRepository.findAll().collectList().block().size();
        // Create the Recibo
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(recibo))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Recibo in the database
        List<Recibo> reciboList = reciboRepository.findAll().collectList().block();
        assertThat(reciboList).hasSize(databaseSizeBeforeCreate + 1);
        Recibo testRecibo = reciboList.get(reciboList.size() - 1);
        assertThat(testRecibo.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testRecibo.getIdInstit()).isEqualTo(DEFAULT_ID_INSTIT);
        assertThat(testRecibo.getNivel()).isEqualTo(DEFAULT_NIVEL);
        assertThat(testRecibo.getTipoCFDI()).isEqualTo(DEFAULT_TIPO_CFDI);
        assertThat(testRecibo.getClave()).isEqualTo(DEFAULT_CLAVE);
        assertThat(testRecibo.getEnvioConCFDI()).isEqualTo(DEFAULT_ENVIO_CON_CFDI);
        assertThat(testRecibo.getEnvioSinCFDI()).isEqualTo(DEFAULT_ENVIO_SIN_CFDI);
        assertThat(testRecibo.getAplicaLeyenda()).isEqualTo(DEFAULT_APLICA_LEYENDA);
        assertThat(testRecibo.getLeyenda()).isEqualTo(DEFAULT_LEYENDA);
        assertThat(testRecibo.getUsuario()).isEqualTo(DEFAULT_USUARIO);
        assertThat(testRecibo.getFechaMod()).isEqualTo(DEFAULT_FECHA_MOD);
    }

    @Test
    void createReciboWithExistingId() throws Exception {
        // Create the Recibo with an existing ID
        recibo.setId(1L);

        int databaseSizeBeforeCreate = reciboRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(recibo))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Recibo in the database
        List<Recibo> reciboList = reciboRepository.findAll().collectList().block();
        assertThat(reciboList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = reciboRepository.findAll().collectList().block().size();
        // set the field null
        recibo.setFecha(null);

        // Create the Recibo, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(recibo))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Recibo> reciboList = reciboRepository.findAll().collectList().block();
        assertThat(reciboList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkIdInstitIsRequired() throws Exception {
        int databaseSizeBeforeTest = reciboRepository.findAll().collectList().block().size();
        // set the field null
        recibo.setIdInstit(null);

        // Create the Recibo, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(recibo))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Recibo> reciboList = reciboRepository.findAll().collectList().block();
        assertThat(reciboList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkNivelIsRequired() throws Exception {
        int databaseSizeBeforeTest = reciboRepository.findAll().collectList().block().size();
        // set the field null
        recibo.setNivel(null);

        // Create the Recibo, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(recibo))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Recibo> reciboList = reciboRepository.findAll().collectList().block();
        assertThat(reciboList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkTipoCFDIIsRequired() throws Exception {
        int databaseSizeBeforeTest = reciboRepository.findAll().collectList().block().size();
        // set the field null
        recibo.setTipoCFDI(null);

        // Create the Recibo, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(recibo))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Recibo> reciboList = reciboRepository.findAll().collectList().block();
        assertThat(reciboList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllRecibos() {
        // Initialize the database
        reciboRepository.save(recibo).block();

        // Get all the reciboList
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
            .value(hasItem(recibo.getId().intValue()))
            .jsonPath("$.[*].fecha")
            .value(hasItem(DEFAULT_FECHA.toString()))
            .jsonPath("$.[*].idInstit")
            .value(hasItem(DEFAULT_ID_INSTIT))
            .jsonPath("$.[*].nivel")
            .value(hasItem(DEFAULT_NIVEL))
            .jsonPath("$.[*].tipoCFDI")
            .value(hasItem(DEFAULT_TIPO_CFDI))
            .jsonPath("$.[*].clave")
            .value(hasItem(DEFAULT_CLAVE))
            .jsonPath("$.[*].envioConCFDI")
            .value(hasItem(DEFAULT_ENVIO_CON_CFDI))
            .jsonPath("$.[*].envioSinCFDI")
            .value(hasItem(DEFAULT_ENVIO_SIN_CFDI))
            .jsonPath("$.[*].aplicaLeyenda")
            .value(hasItem(DEFAULT_APLICA_LEYENDA))
            .jsonPath("$.[*].leyenda")
            .value(hasItem(DEFAULT_LEYENDA.intValue()))
            .jsonPath("$.[*].usuario")
            .value(hasItem(DEFAULT_USUARIO))
            .jsonPath("$.[*].fechaMod")
            .value(hasItem(DEFAULT_FECHA_MOD.toString()));
    }

    @Test
    void getRecibo() {
        // Initialize the database
        reciboRepository.save(recibo).block();

        // Get the recibo
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, recibo.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(recibo.getId().intValue()))
            .jsonPath("$.fecha")
            .value(is(DEFAULT_FECHA.toString()))
            .jsonPath("$.idInstit")
            .value(is(DEFAULT_ID_INSTIT))
            .jsonPath("$.nivel")
            .value(is(DEFAULT_NIVEL))
            .jsonPath("$.tipoCFDI")
            .value(is(DEFAULT_TIPO_CFDI))
            .jsonPath("$.clave")
            .value(is(DEFAULT_CLAVE))
            .jsonPath("$.envioConCFDI")
            .value(is(DEFAULT_ENVIO_CON_CFDI))
            .jsonPath("$.envioSinCFDI")
            .value(is(DEFAULT_ENVIO_SIN_CFDI))
            .jsonPath("$.aplicaLeyenda")
            .value(is(DEFAULT_APLICA_LEYENDA))
            .jsonPath("$.leyenda")
            .value(is(DEFAULT_LEYENDA.intValue()))
            .jsonPath("$.usuario")
            .value(is(DEFAULT_USUARIO))
            .jsonPath("$.fechaMod")
            .value(is(DEFAULT_FECHA_MOD.toString()));
    }

    @Test
    void getNonExistingRecibo() {
        // Get the recibo
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingRecibo() throws Exception {
        // Initialize the database
        reciboRepository.save(recibo).block();

        int databaseSizeBeforeUpdate = reciboRepository.findAll().collectList().block().size();

        // Update the recibo
        Recibo updatedRecibo = reciboRepository.findById(recibo.getId()).block();
        updatedRecibo
            .fecha(UPDATED_FECHA)
            .idInstit(UPDATED_ID_INSTIT)
            .nivel(UPDATED_NIVEL)
            .tipoCFDI(UPDATED_TIPO_CFDI)
            .clave(UPDATED_CLAVE)
            .envioConCFDI(UPDATED_ENVIO_CON_CFDI)
            .envioSinCFDI(UPDATED_ENVIO_SIN_CFDI)
            .aplicaLeyenda(UPDATED_APLICA_LEYENDA)
            .leyenda(UPDATED_LEYENDA)
            .usuario(UPDATED_USUARIO)
            .fechaMod(UPDATED_FECHA_MOD);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedRecibo.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedRecibo))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Recibo in the database
        List<Recibo> reciboList = reciboRepository.findAll().collectList().block();
        assertThat(reciboList).hasSize(databaseSizeBeforeUpdate);
        Recibo testRecibo = reciboList.get(reciboList.size() - 1);
        assertThat(testRecibo.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testRecibo.getIdInstit()).isEqualTo(UPDATED_ID_INSTIT);
        assertThat(testRecibo.getNivel()).isEqualTo(UPDATED_NIVEL);
        assertThat(testRecibo.getTipoCFDI()).isEqualTo(UPDATED_TIPO_CFDI);
        assertThat(testRecibo.getClave()).isEqualTo(UPDATED_CLAVE);
        assertThat(testRecibo.getEnvioConCFDI()).isEqualTo(UPDATED_ENVIO_CON_CFDI);
        assertThat(testRecibo.getEnvioSinCFDI()).isEqualTo(UPDATED_ENVIO_SIN_CFDI);
        assertThat(testRecibo.getAplicaLeyenda()).isEqualTo(UPDATED_APLICA_LEYENDA);
        assertThat(testRecibo.getLeyenda()).isEqualTo(UPDATED_LEYENDA);
        assertThat(testRecibo.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testRecibo.getFechaMod()).isEqualTo(UPDATED_FECHA_MOD);
    }

    @Test
    void putNonExistingRecibo() throws Exception {
        int databaseSizeBeforeUpdate = reciboRepository.findAll().collectList().block().size();
        recibo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, recibo.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(recibo))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Recibo in the database
        List<Recibo> reciboList = reciboRepository.findAll().collectList().block();
        assertThat(reciboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchRecibo() throws Exception {
        int databaseSizeBeforeUpdate = reciboRepository.findAll().collectList().block().size();
        recibo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(recibo))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Recibo in the database
        List<Recibo> reciboList = reciboRepository.findAll().collectList().block();
        assertThat(reciboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamRecibo() throws Exception {
        int databaseSizeBeforeUpdate = reciboRepository.findAll().collectList().block().size();
        recibo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(recibo))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Recibo in the database
        List<Recibo> reciboList = reciboRepository.findAll().collectList().block();
        assertThat(reciboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateReciboWithPatch() throws Exception {
        // Initialize the database
        reciboRepository.save(recibo).block();

        int databaseSizeBeforeUpdate = reciboRepository.findAll().collectList().block().size();

        // Update the recibo using partial update
        Recibo partialUpdatedRecibo = new Recibo();
        partialUpdatedRecibo.setId(recibo.getId());

        partialUpdatedRecibo
            .fecha(UPDATED_FECHA)
            .idInstit(UPDATED_ID_INSTIT)
            .nivel(UPDATED_NIVEL)
            .leyenda(UPDATED_LEYENDA)
            .usuario(UPDATED_USUARIO);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedRecibo.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedRecibo))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Recibo in the database
        List<Recibo> reciboList = reciboRepository.findAll().collectList().block();
        assertThat(reciboList).hasSize(databaseSizeBeforeUpdate);
        Recibo testRecibo = reciboList.get(reciboList.size() - 1);
        assertThat(testRecibo.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testRecibo.getIdInstit()).isEqualTo(UPDATED_ID_INSTIT);
        assertThat(testRecibo.getNivel()).isEqualTo(UPDATED_NIVEL);
        assertThat(testRecibo.getTipoCFDI()).isEqualTo(DEFAULT_TIPO_CFDI);
        assertThat(testRecibo.getClave()).isEqualTo(DEFAULT_CLAVE);
        assertThat(testRecibo.getEnvioConCFDI()).isEqualTo(DEFAULT_ENVIO_CON_CFDI);
        assertThat(testRecibo.getEnvioSinCFDI()).isEqualTo(DEFAULT_ENVIO_SIN_CFDI);
        assertThat(testRecibo.getAplicaLeyenda()).isEqualTo(DEFAULT_APLICA_LEYENDA);
        assertThat(testRecibo.getLeyenda()).isEqualTo(UPDATED_LEYENDA);
        assertThat(testRecibo.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testRecibo.getFechaMod()).isEqualTo(DEFAULT_FECHA_MOD);
    }

    @Test
    void fullUpdateReciboWithPatch() throws Exception {
        // Initialize the database
        reciboRepository.save(recibo).block();

        int databaseSizeBeforeUpdate = reciboRepository.findAll().collectList().block().size();

        // Update the recibo using partial update
        Recibo partialUpdatedRecibo = new Recibo();
        partialUpdatedRecibo.setId(recibo.getId());

        partialUpdatedRecibo
            .fecha(UPDATED_FECHA)
            .idInstit(UPDATED_ID_INSTIT)
            .nivel(UPDATED_NIVEL)
            .tipoCFDI(UPDATED_TIPO_CFDI)
            .clave(UPDATED_CLAVE)
            .envioConCFDI(UPDATED_ENVIO_CON_CFDI)
            .envioSinCFDI(UPDATED_ENVIO_SIN_CFDI)
            .aplicaLeyenda(UPDATED_APLICA_LEYENDA)
            .leyenda(UPDATED_LEYENDA)
            .usuario(UPDATED_USUARIO)
            .fechaMod(UPDATED_FECHA_MOD);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedRecibo.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedRecibo))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Recibo in the database
        List<Recibo> reciboList = reciboRepository.findAll().collectList().block();
        assertThat(reciboList).hasSize(databaseSizeBeforeUpdate);
        Recibo testRecibo = reciboList.get(reciboList.size() - 1);
        assertThat(testRecibo.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testRecibo.getIdInstit()).isEqualTo(UPDATED_ID_INSTIT);
        assertThat(testRecibo.getNivel()).isEqualTo(UPDATED_NIVEL);
        assertThat(testRecibo.getTipoCFDI()).isEqualTo(UPDATED_TIPO_CFDI);
        assertThat(testRecibo.getClave()).isEqualTo(UPDATED_CLAVE);
        assertThat(testRecibo.getEnvioConCFDI()).isEqualTo(UPDATED_ENVIO_CON_CFDI);
        assertThat(testRecibo.getEnvioSinCFDI()).isEqualTo(UPDATED_ENVIO_SIN_CFDI);
        assertThat(testRecibo.getAplicaLeyenda()).isEqualTo(UPDATED_APLICA_LEYENDA);
        assertThat(testRecibo.getLeyenda()).isEqualTo(UPDATED_LEYENDA);
        assertThat(testRecibo.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testRecibo.getFechaMod()).isEqualTo(UPDATED_FECHA_MOD);
    }

    @Test
    void patchNonExistingRecibo() throws Exception {
        int databaseSizeBeforeUpdate = reciboRepository.findAll().collectList().block().size();
        recibo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, recibo.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(recibo))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Recibo in the database
        List<Recibo> reciboList = reciboRepository.findAll().collectList().block();
        assertThat(reciboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchRecibo() throws Exception {
        int databaseSizeBeforeUpdate = reciboRepository.findAll().collectList().block().size();
        recibo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(recibo))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Recibo in the database
        List<Recibo> reciboList = reciboRepository.findAll().collectList().block();
        assertThat(reciboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamRecibo() throws Exception {
        int databaseSizeBeforeUpdate = reciboRepository.findAll().collectList().block().size();
        recibo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(recibo))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Recibo in the database
        List<Recibo> reciboList = reciboRepository.findAll().collectList().block();
        assertThat(reciboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteRecibo() {
        // Initialize the database
        reciboRepository.save(recibo).block();

        int databaseSizeBeforeDelete = reciboRepository.findAll().collectList().block().size();

        // Delete the recibo
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, recibo.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Recibo> reciboList = reciboRepository.findAll().collectList().block();
        assertThat(reciboList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
