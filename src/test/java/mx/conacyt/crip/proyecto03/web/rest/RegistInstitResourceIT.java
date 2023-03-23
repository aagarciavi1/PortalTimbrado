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
import mx.conacyt.crip.proyecto03.domain.RegistInstit;
import mx.conacyt.crip.proyecto03.domain.enumeration.Estatus;
import mx.conacyt.crip.proyecto03.repository.EntityManager;
import mx.conacyt.crip.proyecto03.repository.RegistInstitRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link RegistInstitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class RegistInstitResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_ID_INSTIT = 1;
    private static final Integer UPDATED_ID_INSTIT = 2;

    private static final Estatus DEFAULT_ESTATUS = Estatus.Activo;
    private static final Estatus UPDATED_ESTATUS = Estatus.Inactivo;

    private static final Integer DEFAULT_NIVEL = 1;
    private static final Integer UPDATED_NIVEL = 2;

    private static final String DEFAULT_RAZON_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_RAZON_SOCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_R_FC = "AAAAAAAAAA";
    private static final String UPDATED_R_FC = "BBBBBBBBBB";

    private static final String DEFAULT_PAIS = "AAAAAAAAAA";
    private static final String UPDATED_PAIS = "BBBBBBBBBB";

    private static final String DEFAULT_MUNICIPIO = "AAAAAAAAAA";
    private static final String UPDATED_MUNICIPIO = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALIDAD = "AAAAAAAAAA";
    private static final String UPDATED_LOCALIDAD = "BBBBBBBBBB";

    private static final Integer DEFAULT_CODIGO_POSTAL = 1;
    private static final Integer UPDATED_CODIGO_POSTAL = 2;

    private static final String DEFAULT_COLONIA = "AAAAAAAAAA";
    private static final String UPDATED_COLONIA = "BBBBBBBBBB";

    private static final String DEFAULT_CALLE = "AAAAAAAAAA";
    private static final String UPDATED_CALLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUM_EXT = 1;
    private static final Integer UPDATED_NUM_EXT = 2;

    private static final Integer DEFAULT_NUM_INT = 1;
    private static final Integer UPDATED_NUM_INT = 2;

    private static final byte[] DEFAULT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_CERTIFICADO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CERTIFICADO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CERTIFICADO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CERTIFICADO_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_LLAVE_PRIV = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LLAVE_PRIV = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LLAVE_PRIV_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LLAVE_PRIV_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_CONTRASENA = "AAAAAAAAAA";
    private static final String UPDATED_CONTRASENA = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_EXP = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_EXP = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_DIAS = 1;
    private static final Integer UPDATED_DIAS = 2;

    private static final String DEFAULT_CORREO = "AAAAAAAAAA";
    private static final String UPDATED_CORREO = "BBBBBBBBBB";

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_MOD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_MOD = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/regist-instits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RegistInstitRepository registInstitRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private RegistInstit registInstit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegistInstit createEntity(EntityManager em) {
        RegistInstit registInstit = new RegistInstit()
            .fecha(DEFAULT_FECHA)
            .idInstit(DEFAULT_ID_INSTIT)
            .estatus(DEFAULT_ESTATUS)
            .nivel(DEFAULT_NIVEL)
            .razonSocial(DEFAULT_RAZON_SOCIAL)
            .rFC(DEFAULT_R_FC)
            .pais(DEFAULT_PAIS)
            .municipio(DEFAULT_MUNICIPIO)
            .localidad(DEFAULT_LOCALIDAD)
            .codigoPostal(DEFAULT_CODIGO_POSTAL)
            .colonia(DEFAULT_COLONIA)
            .calle(DEFAULT_CALLE)
            .numExt(DEFAULT_NUM_EXT)
            .numInt(DEFAULT_NUM_INT)
            .logo(DEFAULT_LOGO)
            .logoContentType(DEFAULT_LOGO_CONTENT_TYPE)
            .certificado(DEFAULT_CERTIFICADO)
            .certificadoContentType(DEFAULT_CERTIFICADO_CONTENT_TYPE)
            .llavePriv(DEFAULT_LLAVE_PRIV)
            .llavePrivContentType(DEFAULT_LLAVE_PRIV_CONTENT_TYPE)
            .contrasena(DEFAULT_CONTRASENA)
            .fechaExp(DEFAULT_FECHA_EXP)
            .dias(DEFAULT_DIAS)
            .correo(DEFAULT_CORREO)
            .usuario(DEFAULT_USUARIO)
            .fechaMod(DEFAULT_FECHA_MOD);
        return registInstit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegistInstit createUpdatedEntity(EntityManager em) {
        RegistInstit registInstit = new RegistInstit()
            .fecha(UPDATED_FECHA)
            .idInstit(UPDATED_ID_INSTIT)
            .estatus(UPDATED_ESTATUS)
            .nivel(UPDATED_NIVEL)
            .razonSocial(UPDATED_RAZON_SOCIAL)
            .rFC(UPDATED_R_FC)
            .pais(UPDATED_PAIS)
            .municipio(UPDATED_MUNICIPIO)
            .localidad(UPDATED_LOCALIDAD)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .colonia(UPDATED_COLONIA)
            .calle(UPDATED_CALLE)
            .numExt(UPDATED_NUM_EXT)
            .numInt(UPDATED_NUM_INT)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .certificado(UPDATED_CERTIFICADO)
            .certificadoContentType(UPDATED_CERTIFICADO_CONTENT_TYPE)
            .llavePriv(UPDATED_LLAVE_PRIV)
            .llavePrivContentType(UPDATED_LLAVE_PRIV_CONTENT_TYPE)
            .contrasena(UPDATED_CONTRASENA)
            .fechaExp(UPDATED_FECHA_EXP)
            .dias(UPDATED_DIAS)
            .correo(UPDATED_CORREO)
            .usuario(UPDATED_USUARIO)
            .fechaMod(UPDATED_FECHA_MOD);
        return registInstit;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(RegistInstit.class).block();
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
        registInstit = createEntity(em);
    }

    @Test
    void createRegistInstit() throws Exception {
        int databaseSizeBeforeCreate = registInstitRepository.findAll().collectList().block().size();
        // Create the RegistInstit
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(registInstit))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the RegistInstit in the database
        List<RegistInstit> registInstitList = registInstitRepository.findAll().collectList().block();
        assertThat(registInstitList).hasSize(databaseSizeBeforeCreate + 1);
        RegistInstit testRegistInstit = registInstitList.get(registInstitList.size() - 1);
        assertThat(testRegistInstit.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testRegistInstit.getIdInstit()).isEqualTo(DEFAULT_ID_INSTIT);
        assertThat(testRegistInstit.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
        assertThat(testRegistInstit.getNivel()).isEqualTo(DEFAULT_NIVEL);
        assertThat(testRegistInstit.getRazonSocial()).isEqualTo(DEFAULT_RAZON_SOCIAL);
        assertThat(testRegistInstit.getrFC()).isEqualTo(DEFAULT_R_FC);
        assertThat(testRegistInstit.getPais()).isEqualTo(DEFAULT_PAIS);
        assertThat(testRegistInstit.getMunicipio()).isEqualTo(DEFAULT_MUNICIPIO);
        assertThat(testRegistInstit.getLocalidad()).isEqualTo(DEFAULT_LOCALIDAD);
        assertThat(testRegistInstit.getCodigoPostal()).isEqualTo(DEFAULT_CODIGO_POSTAL);
        assertThat(testRegistInstit.getColonia()).isEqualTo(DEFAULT_COLONIA);
        assertThat(testRegistInstit.getCalle()).isEqualTo(DEFAULT_CALLE);
        assertThat(testRegistInstit.getNumExt()).isEqualTo(DEFAULT_NUM_EXT);
        assertThat(testRegistInstit.getNumInt()).isEqualTo(DEFAULT_NUM_INT);
        assertThat(testRegistInstit.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testRegistInstit.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
        assertThat(testRegistInstit.getCertificado()).isEqualTo(DEFAULT_CERTIFICADO);
        assertThat(testRegistInstit.getCertificadoContentType()).isEqualTo(DEFAULT_CERTIFICADO_CONTENT_TYPE);
        assertThat(testRegistInstit.getLlavePriv()).isEqualTo(DEFAULT_LLAVE_PRIV);
        assertThat(testRegistInstit.getLlavePrivContentType()).isEqualTo(DEFAULT_LLAVE_PRIV_CONTENT_TYPE);
        assertThat(testRegistInstit.getContrasena()).isEqualTo(DEFAULT_CONTRASENA);
        assertThat(testRegistInstit.getFechaExp()).isEqualTo(DEFAULT_FECHA_EXP);
        assertThat(testRegistInstit.getDias()).isEqualTo(DEFAULT_DIAS);
        assertThat(testRegistInstit.getCorreo()).isEqualTo(DEFAULT_CORREO);
        assertThat(testRegistInstit.getUsuario()).isEqualTo(DEFAULT_USUARIO);
        assertThat(testRegistInstit.getFechaMod()).isEqualTo(DEFAULT_FECHA_MOD);
    }

    @Test
    void createRegistInstitWithExistingId() throws Exception {
        // Create the RegistInstit with an existing ID
        registInstit.setId(1L);

        int databaseSizeBeforeCreate = registInstitRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(registInstit))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the RegistInstit in the database
        List<RegistInstit> registInstitList = registInstitRepository.findAll().collectList().block();
        assertThat(registInstitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = registInstitRepository.findAll().collectList().block().size();
        // set the field null
        registInstit.setFecha(null);

        // Create the RegistInstit, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(registInstit))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<RegistInstit> registInstitList = registInstitRepository.findAll().collectList().block();
        assertThat(registInstitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkIdInstitIsRequired() throws Exception {
        int databaseSizeBeforeTest = registInstitRepository.findAll().collectList().block().size();
        // set the field null
        registInstit.setIdInstit(null);

        // Create the RegistInstit, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(registInstit))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<RegistInstit> registInstitList = registInstitRepository.findAll().collectList().block();
        assertThat(registInstitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkNivelIsRequired() throws Exception {
        int databaseSizeBeforeTest = registInstitRepository.findAll().collectList().block().size();
        // set the field null
        registInstit.setNivel(null);

        // Create the RegistInstit, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(registInstit))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<RegistInstit> registInstitList = registInstitRepository.findAll().collectList().block();
        assertThat(registInstitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkRazonSocialIsRequired() throws Exception {
        int databaseSizeBeforeTest = registInstitRepository.findAll().collectList().block().size();
        // set the field null
        registInstit.setRazonSocial(null);

        // Create the RegistInstit, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(registInstit))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<RegistInstit> registInstitList = registInstitRepository.findAll().collectList().block();
        assertThat(registInstitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkrFCIsRequired() throws Exception {
        int databaseSizeBeforeTest = registInstitRepository.findAll().collectList().block().size();
        // set the field null
        registInstit.setrFC(null);

        // Create the RegistInstit, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(registInstit))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<RegistInstit> registInstitList = registInstitRepository.findAll().collectList().block();
        assertThat(registInstitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllRegistInstits() {
        // Initialize the database
        registInstitRepository.save(registInstit).block();

        // Get all the registInstitList
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
            .value(hasItem(registInstit.getId().intValue()))
            .jsonPath("$.[*].fecha")
            .value(hasItem(DEFAULT_FECHA.toString()))
            .jsonPath("$.[*].idInstit")
            .value(hasItem(DEFAULT_ID_INSTIT))
            .jsonPath("$.[*].estatus")
            .value(hasItem(DEFAULT_ESTATUS.toString()))
            .jsonPath("$.[*].nivel")
            .value(hasItem(DEFAULT_NIVEL))
            .jsonPath("$.[*].razonSocial")
            .value(hasItem(DEFAULT_RAZON_SOCIAL))
            .jsonPath("$.[*].rFC")
            .value(hasItem(DEFAULT_R_FC))
            .jsonPath("$.[*].pais")
            .value(hasItem(DEFAULT_PAIS))
            .jsonPath("$.[*].municipio")
            .value(hasItem(DEFAULT_MUNICIPIO))
            .jsonPath("$.[*].localidad")
            .value(hasItem(DEFAULT_LOCALIDAD))
            .jsonPath("$.[*].codigoPostal")
            .value(hasItem(DEFAULT_CODIGO_POSTAL))
            .jsonPath("$.[*].colonia")
            .value(hasItem(DEFAULT_COLONIA))
            .jsonPath("$.[*].calle")
            .value(hasItem(DEFAULT_CALLE))
            .jsonPath("$.[*].numExt")
            .value(hasItem(DEFAULT_NUM_EXT))
            .jsonPath("$.[*].numInt")
            .value(hasItem(DEFAULT_NUM_INT))
            .jsonPath("$.[*].logoContentType")
            .value(hasItem(DEFAULT_LOGO_CONTENT_TYPE))
            .jsonPath("$.[*].logo")
            .value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO)))
            .jsonPath("$.[*].certificadoContentType")
            .value(hasItem(DEFAULT_CERTIFICADO_CONTENT_TYPE))
            .jsonPath("$.[*].certificado")
            .value(hasItem(Base64Utils.encodeToString(DEFAULT_CERTIFICADO)))
            .jsonPath("$.[*].llavePrivContentType")
            .value(hasItem(DEFAULT_LLAVE_PRIV_CONTENT_TYPE))
            .jsonPath("$.[*].llavePriv")
            .value(hasItem(Base64Utils.encodeToString(DEFAULT_LLAVE_PRIV)))
            .jsonPath("$.[*].contrasena")
            .value(hasItem(DEFAULT_CONTRASENA))
            .jsonPath("$.[*].fechaExp")
            .value(hasItem(DEFAULT_FECHA_EXP.toString()))
            .jsonPath("$.[*].dias")
            .value(hasItem(DEFAULT_DIAS))
            .jsonPath("$.[*].correo")
            .value(hasItem(DEFAULT_CORREO))
            .jsonPath("$.[*].usuario")
            .value(hasItem(DEFAULT_USUARIO))
            .jsonPath("$.[*].fechaMod")
            .value(hasItem(DEFAULT_FECHA_MOD.toString()));
    }

    @Test
    void getRegistInstit() {
        // Initialize the database
        registInstitRepository.save(registInstit).block();

        // Get the registInstit
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, registInstit.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(registInstit.getId().intValue()))
            .jsonPath("$.fecha")
            .value(is(DEFAULT_FECHA.toString()))
            .jsonPath("$.idInstit")
            .value(is(DEFAULT_ID_INSTIT))
            .jsonPath("$.estatus")
            .value(is(DEFAULT_ESTATUS.toString()))
            .jsonPath("$.nivel")
            .value(is(DEFAULT_NIVEL))
            .jsonPath("$.razonSocial")
            .value(is(DEFAULT_RAZON_SOCIAL))
            .jsonPath("$.rFC")
            .value(is(DEFAULT_R_FC))
            .jsonPath("$.pais")
            .value(is(DEFAULT_PAIS))
            .jsonPath("$.municipio")
            .value(is(DEFAULT_MUNICIPIO))
            .jsonPath("$.localidad")
            .value(is(DEFAULT_LOCALIDAD))
            .jsonPath("$.codigoPostal")
            .value(is(DEFAULT_CODIGO_POSTAL))
            .jsonPath("$.colonia")
            .value(is(DEFAULT_COLONIA))
            .jsonPath("$.calle")
            .value(is(DEFAULT_CALLE))
            .jsonPath("$.numExt")
            .value(is(DEFAULT_NUM_EXT))
            .jsonPath("$.numInt")
            .value(is(DEFAULT_NUM_INT))
            .jsonPath("$.logoContentType")
            .value(is(DEFAULT_LOGO_CONTENT_TYPE))
            .jsonPath("$.logo")
            .value(is(Base64Utils.encodeToString(DEFAULT_LOGO)))
            .jsonPath("$.certificadoContentType")
            .value(is(DEFAULT_CERTIFICADO_CONTENT_TYPE))
            .jsonPath("$.certificado")
            .value(is(Base64Utils.encodeToString(DEFAULT_CERTIFICADO)))
            .jsonPath("$.llavePrivContentType")
            .value(is(DEFAULT_LLAVE_PRIV_CONTENT_TYPE))
            .jsonPath("$.llavePriv")
            .value(is(Base64Utils.encodeToString(DEFAULT_LLAVE_PRIV)))
            .jsonPath("$.contrasena")
            .value(is(DEFAULT_CONTRASENA))
            .jsonPath("$.fechaExp")
            .value(is(DEFAULT_FECHA_EXP.toString()))
            .jsonPath("$.dias")
            .value(is(DEFAULT_DIAS))
            .jsonPath("$.correo")
            .value(is(DEFAULT_CORREO))
            .jsonPath("$.usuario")
            .value(is(DEFAULT_USUARIO))
            .jsonPath("$.fechaMod")
            .value(is(DEFAULT_FECHA_MOD.toString()));
    }

    @Test
    void getNonExistingRegistInstit() {
        // Get the registInstit
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingRegistInstit() throws Exception {
        // Initialize the database
        registInstitRepository.save(registInstit).block();

        int databaseSizeBeforeUpdate = registInstitRepository.findAll().collectList().block().size();

        // Update the registInstit
        RegistInstit updatedRegistInstit = registInstitRepository.findById(registInstit.getId()).block();
        updatedRegistInstit
            .fecha(UPDATED_FECHA)
            .idInstit(UPDATED_ID_INSTIT)
            .estatus(UPDATED_ESTATUS)
            .nivel(UPDATED_NIVEL)
            .razonSocial(UPDATED_RAZON_SOCIAL)
            .rFC(UPDATED_R_FC)
            .pais(UPDATED_PAIS)
            .municipio(UPDATED_MUNICIPIO)
            .localidad(UPDATED_LOCALIDAD)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .colonia(UPDATED_COLONIA)
            .calle(UPDATED_CALLE)
            .numExt(UPDATED_NUM_EXT)
            .numInt(UPDATED_NUM_INT)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .certificado(UPDATED_CERTIFICADO)
            .certificadoContentType(UPDATED_CERTIFICADO_CONTENT_TYPE)
            .llavePriv(UPDATED_LLAVE_PRIV)
            .llavePrivContentType(UPDATED_LLAVE_PRIV_CONTENT_TYPE)
            .contrasena(UPDATED_CONTRASENA)
            .fechaExp(UPDATED_FECHA_EXP)
            .dias(UPDATED_DIAS)
            .correo(UPDATED_CORREO)
            .usuario(UPDATED_USUARIO)
            .fechaMod(UPDATED_FECHA_MOD);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedRegistInstit.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedRegistInstit))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the RegistInstit in the database
        List<RegistInstit> registInstitList = registInstitRepository.findAll().collectList().block();
        assertThat(registInstitList).hasSize(databaseSizeBeforeUpdate);
        RegistInstit testRegistInstit = registInstitList.get(registInstitList.size() - 1);
        assertThat(testRegistInstit.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testRegistInstit.getIdInstit()).isEqualTo(UPDATED_ID_INSTIT);
        assertThat(testRegistInstit.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testRegistInstit.getNivel()).isEqualTo(UPDATED_NIVEL);
        assertThat(testRegistInstit.getRazonSocial()).isEqualTo(UPDATED_RAZON_SOCIAL);
        assertThat(testRegistInstit.getrFC()).isEqualTo(UPDATED_R_FC);
        assertThat(testRegistInstit.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testRegistInstit.getMunicipio()).isEqualTo(UPDATED_MUNICIPIO);
        assertThat(testRegistInstit.getLocalidad()).isEqualTo(UPDATED_LOCALIDAD);
        assertThat(testRegistInstit.getCodigoPostal()).isEqualTo(UPDATED_CODIGO_POSTAL);
        assertThat(testRegistInstit.getColonia()).isEqualTo(UPDATED_COLONIA);
        assertThat(testRegistInstit.getCalle()).isEqualTo(UPDATED_CALLE);
        assertThat(testRegistInstit.getNumExt()).isEqualTo(UPDATED_NUM_EXT);
        assertThat(testRegistInstit.getNumInt()).isEqualTo(UPDATED_NUM_INT);
        assertThat(testRegistInstit.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testRegistInstit.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testRegistInstit.getCertificado()).isEqualTo(UPDATED_CERTIFICADO);
        assertThat(testRegistInstit.getCertificadoContentType()).isEqualTo(UPDATED_CERTIFICADO_CONTENT_TYPE);
        assertThat(testRegistInstit.getLlavePriv()).isEqualTo(UPDATED_LLAVE_PRIV);
        assertThat(testRegistInstit.getLlavePrivContentType()).isEqualTo(UPDATED_LLAVE_PRIV_CONTENT_TYPE);
        assertThat(testRegistInstit.getContrasena()).isEqualTo(UPDATED_CONTRASENA);
        assertThat(testRegistInstit.getFechaExp()).isEqualTo(UPDATED_FECHA_EXP);
        assertThat(testRegistInstit.getDias()).isEqualTo(UPDATED_DIAS);
        assertThat(testRegistInstit.getCorreo()).isEqualTo(UPDATED_CORREO);
        assertThat(testRegistInstit.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testRegistInstit.getFechaMod()).isEqualTo(UPDATED_FECHA_MOD);
    }

    @Test
    void putNonExistingRegistInstit() throws Exception {
        int databaseSizeBeforeUpdate = registInstitRepository.findAll().collectList().block().size();
        registInstit.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, registInstit.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(registInstit))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the RegistInstit in the database
        List<RegistInstit> registInstitList = registInstitRepository.findAll().collectList().block();
        assertThat(registInstitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchRegistInstit() throws Exception {
        int databaseSizeBeforeUpdate = registInstitRepository.findAll().collectList().block().size();
        registInstit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(registInstit))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the RegistInstit in the database
        List<RegistInstit> registInstitList = registInstitRepository.findAll().collectList().block();
        assertThat(registInstitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamRegistInstit() throws Exception {
        int databaseSizeBeforeUpdate = registInstitRepository.findAll().collectList().block().size();
        registInstit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(registInstit))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the RegistInstit in the database
        List<RegistInstit> registInstitList = registInstitRepository.findAll().collectList().block();
        assertThat(registInstitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateRegistInstitWithPatch() throws Exception {
        // Initialize the database
        registInstitRepository.save(registInstit).block();

        int databaseSizeBeforeUpdate = registInstitRepository.findAll().collectList().block().size();

        // Update the registInstit using partial update
        RegistInstit partialUpdatedRegistInstit = new RegistInstit();
        partialUpdatedRegistInstit.setId(registInstit.getId());

        partialUpdatedRegistInstit
            .fecha(UPDATED_FECHA)
            .razonSocial(UPDATED_RAZON_SOCIAL)
            .municipio(UPDATED_MUNICIPIO)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .colonia(UPDATED_COLONIA)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .fechaExp(UPDATED_FECHA_EXP)
            .usuario(UPDATED_USUARIO);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedRegistInstit.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedRegistInstit))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the RegistInstit in the database
        List<RegistInstit> registInstitList = registInstitRepository.findAll().collectList().block();
        assertThat(registInstitList).hasSize(databaseSizeBeforeUpdate);
        RegistInstit testRegistInstit = registInstitList.get(registInstitList.size() - 1);
        assertThat(testRegistInstit.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testRegistInstit.getIdInstit()).isEqualTo(DEFAULT_ID_INSTIT);
        assertThat(testRegistInstit.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
        assertThat(testRegistInstit.getNivel()).isEqualTo(DEFAULT_NIVEL);
        assertThat(testRegistInstit.getRazonSocial()).isEqualTo(UPDATED_RAZON_SOCIAL);
        assertThat(testRegistInstit.getrFC()).isEqualTo(DEFAULT_R_FC);
        assertThat(testRegistInstit.getPais()).isEqualTo(DEFAULT_PAIS);
        assertThat(testRegistInstit.getMunicipio()).isEqualTo(UPDATED_MUNICIPIO);
        assertThat(testRegistInstit.getLocalidad()).isEqualTo(DEFAULT_LOCALIDAD);
        assertThat(testRegistInstit.getCodigoPostal()).isEqualTo(UPDATED_CODIGO_POSTAL);
        assertThat(testRegistInstit.getColonia()).isEqualTo(UPDATED_COLONIA);
        assertThat(testRegistInstit.getCalle()).isEqualTo(DEFAULT_CALLE);
        assertThat(testRegistInstit.getNumExt()).isEqualTo(DEFAULT_NUM_EXT);
        assertThat(testRegistInstit.getNumInt()).isEqualTo(DEFAULT_NUM_INT);
        assertThat(testRegistInstit.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testRegistInstit.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testRegistInstit.getCertificado()).isEqualTo(DEFAULT_CERTIFICADO);
        assertThat(testRegistInstit.getCertificadoContentType()).isEqualTo(DEFAULT_CERTIFICADO_CONTENT_TYPE);
        assertThat(testRegistInstit.getLlavePriv()).isEqualTo(DEFAULT_LLAVE_PRIV);
        assertThat(testRegistInstit.getLlavePrivContentType()).isEqualTo(DEFAULT_LLAVE_PRIV_CONTENT_TYPE);
        assertThat(testRegistInstit.getContrasena()).isEqualTo(DEFAULT_CONTRASENA);
        assertThat(testRegistInstit.getFechaExp()).isEqualTo(UPDATED_FECHA_EXP);
        assertThat(testRegistInstit.getDias()).isEqualTo(DEFAULT_DIAS);
        assertThat(testRegistInstit.getCorreo()).isEqualTo(DEFAULT_CORREO);
        assertThat(testRegistInstit.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testRegistInstit.getFechaMod()).isEqualTo(DEFAULT_FECHA_MOD);
    }

    @Test
    void fullUpdateRegistInstitWithPatch() throws Exception {
        // Initialize the database
        registInstitRepository.save(registInstit).block();

        int databaseSizeBeforeUpdate = registInstitRepository.findAll().collectList().block().size();

        // Update the registInstit using partial update
        RegistInstit partialUpdatedRegistInstit = new RegistInstit();
        partialUpdatedRegistInstit.setId(registInstit.getId());

        partialUpdatedRegistInstit
            .fecha(UPDATED_FECHA)
            .idInstit(UPDATED_ID_INSTIT)
            .estatus(UPDATED_ESTATUS)
            .nivel(UPDATED_NIVEL)
            .razonSocial(UPDATED_RAZON_SOCIAL)
            .rFC(UPDATED_R_FC)
            .pais(UPDATED_PAIS)
            .municipio(UPDATED_MUNICIPIO)
            .localidad(UPDATED_LOCALIDAD)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .colonia(UPDATED_COLONIA)
            .calle(UPDATED_CALLE)
            .numExt(UPDATED_NUM_EXT)
            .numInt(UPDATED_NUM_INT)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .certificado(UPDATED_CERTIFICADO)
            .certificadoContentType(UPDATED_CERTIFICADO_CONTENT_TYPE)
            .llavePriv(UPDATED_LLAVE_PRIV)
            .llavePrivContentType(UPDATED_LLAVE_PRIV_CONTENT_TYPE)
            .contrasena(UPDATED_CONTRASENA)
            .fechaExp(UPDATED_FECHA_EXP)
            .dias(UPDATED_DIAS)
            .correo(UPDATED_CORREO)
            .usuario(UPDATED_USUARIO)
            .fechaMod(UPDATED_FECHA_MOD);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedRegistInstit.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedRegistInstit))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the RegistInstit in the database
        List<RegistInstit> registInstitList = registInstitRepository.findAll().collectList().block();
        assertThat(registInstitList).hasSize(databaseSizeBeforeUpdate);
        RegistInstit testRegistInstit = registInstitList.get(registInstitList.size() - 1);
        assertThat(testRegistInstit.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testRegistInstit.getIdInstit()).isEqualTo(UPDATED_ID_INSTIT);
        assertThat(testRegistInstit.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testRegistInstit.getNivel()).isEqualTo(UPDATED_NIVEL);
        assertThat(testRegistInstit.getRazonSocial()).isEqualTo(UPDATED_RAZON_SOCIAL);
        assertThat(testRegistInstit.getrFC()).isEqualTo(UPDATED_R_FC);
        assertThat(testRegistInstit.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testRegistInstit.getMunicipio()).isEqualTo(UPDATED_MUNICIPIO);
        assertThat(testRegistInstit.getLocalidad()).isEqualTo(UPDATED_LOCALIDAD);
        assertThat(testRegistInstit.getCodigoPostal()).isEqualTo(UPDATED_CODIGO_POSTAL);
        assertThat(testRegistInstit.getColonia()).isEqualTo(UPDATED_COLONIA);
        assertThat(testRegistInstit.getCalle()).isEqualTo(UPDATED_CALLE);
        assertThat(testRegistInstit.getNumExt()).isEqualTo(UPDATED_NUM_EXT);
        assertThat(testRegistInstit.getNumInt()).isEqualTo(UPDATED_NUM_INT);
        assertThat(testRegistInstit.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testRegistInstit.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testRegistInstit.getCertificado()).isEqualTo(UPDATED_CERTIFICADO);
        assertThat(testRegistInstit.getCertificadoContentType()).isEqualTo(UPDATED_CERTIFICADO_CONTENT_TYPE);
        assertThat(testRegistInstit.getLlavePriv()).isEqualTo(UPDATED_LLAVE_PRIV);
        assertThat(testRegistInstit.getLlavePrivContentType()).isEqualTo(UPDATED_LLAVE_PRIV_CONTENT_TYPE);
        assertThat(testRegistInstit.getContrasena()).isEqualTo(UPDATED_CONTRASENA);
        assertThat(testRegistInstit.getFechaExp()).isEqualTo(UPDATED_FECHA_EXP);
        assertThat(testRegistInstit.getDias()).isEqualTo(UPDATED_DIAS);
        assertThat(testRegistInstit.getCorreo()).isEqualTo(UPDATED_CORREO);
        assertThat(testRegistInstit.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testRegistInstit.getFechaMod()).isEqualTo(UPDATED_FECHA_MOD);
    }

    @Test
    void patchNonExistingRegistInstit() throws Exception {
        int databaseSizeBeforeUpdate = registInstitRepository.findAll().collectList().block().size();
        registInstit.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, registInstit.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(registInstit))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the RegistInstit in the database
        List<RegistInstit> registInstitList = registInstitRepository.findAll().collectList().block();
        assertThat(registInstitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchRegistInstit() throws Exception {
        int databaseSizeBeforeUpdate = registInstitRepository.findAll().collectList().block().size();
        registInstit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(registInstit))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the RegistInstit in the database
        List<RegistInstit> registInstitList = registInstitRepository.findAll().collectList().block();
        assertThat(registInstitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamRegistInstit() throws Exception {
        int databaseSizeBeforeUpdate = registInstitRepository.findAll().collectList().block().size();
        registInstit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(registInstit))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the RegistInstit in the database
        List<RegistInstit> registInstitList = registInstitRepository.findAll().collectList().block();
        assertThat(registInstitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteRegistInstit() {
        // Initialize the database
        registInstitRepository.save(registInstit).block();

        int databaseSizeBeforeDelete = registInstitRepository.findAll().collectList().block().size();

        // Delete the registInstit
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, registInstit.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<RegistInstit> registInstitList = registInstitRepository.findAll().collectList().block();
        assertThat(registInstitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
