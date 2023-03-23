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
import mx.conacyt.crip.proyecto03.domain.Usuarios;
import mx.conacyt.crip.proyecto03.domain.enumeration.Estatus;
import mx.conacyt.crip.proyecto03.repository.EntityManager;
import mx.conacyt.crip.proyecto03.repository.UsuariosRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link UsuariosResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class UsuariosResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCR = "AAAAAAAAAA";
    private static final String UPDATED_DESCR = "BBBBBBBBBB";

    private static final Integer DEFAULT_ID_INSTIT = 1;
    private static final Integer UPDATED_ID_INSTIT = 2;

    private static final String DEFAULT_CORREO = "AAAAAAAAAA";
    private static final String UPDATED_CORREO = "BBBBBBBBBB";

    private static final String DEFAULT_CONTRASENA = "AAAAAAAAAA";
    private static final String UPDATED_CONTRASENA = "BBBBBBBBBB";

    private static final String DEFAULT_CONFIRMAR_CONTRAS = "AAAAAAAAAA";
    private static final String UPDATED_CONFIRMAR_CONTRAS = "BBBBBBBBBB";

    private static final Estatus DEFAULT_ESTATUS = Estatus.Activo;
    private static final Estatus UPDATED_ESTATUS = Estatus.Inactivo;

    private static final String DEFAULT_USUARIO_CREA = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_CREA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/usuarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Usuarios usuarios;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Usuarios createEntity(EntityManager em) {
        Usuarios usuarios = new Usuarios()
            .fecha(DEFAULT_FECHA)
            .usuario(DEFAULT_USUARIO)
            .descr(DEFAULT_DESCR)
            .idInstit(DEFAULT_ID_INSTIT)
            .correo(DEFAULT_CORREO)
            .contrasena(DEFAULT_CONTRASENA)
            .confirmarContras(DEFAULT_CONFIRMAR_CONTRAS)
            .estatus(DEFAULT_ESTATUS)
            .usuarioCrea(DEFAULT_USUARIO_CREA);
        return usuarios;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Usuarios createUpdatedEntity(EntityManager em) {
        Usuarios usuarios = new Usuarios()
            .fecha(UPDATED_FECHA)
            .usuario(UPDATED_USUARIO)
            .descr(UPDATED_DESCR)
            .idInstit(UPDATED_ID_INSTIT)
            .correo(UPDATED_CORREO)
            .contrasena(UPDATED_CONTRASENA)
            .confirmarContras(UPDATED_CONFIRMAR_CONTRAS)
            .estatus(UPDATED_ESTATUS)
            .usuarioCrea(UPDATED_USUARIO_CREA);
        return usuarios;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Usuarios.class).block();
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
        usuarios = createEntity(em);
    }

    @Test
    void createUsuarios() throws Exception {
        int databaseSizeBeforeCreate = usuariosRepository.findAll().collectList().block().size();
        // Create the Usuarios
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(usuarios))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Usuarios in the database
        List<Usuarios> usuariosList = usuariosRepository.findAll().collectList().block();
        assertThat(usuariosList).hasSize(databaseSizeBeforeCreate + 1);
        Usuarios testUsuarios = usuariosList.get(usuariosList.size() - 1);
        assertThat(testUsuarios.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testUsuarios.getUsuario()).isEqualTo(DEFAULT_USUARIO);
        assertThat(testUsuarios.getDescr()).isEqualTo(DEFAULT_DESCR);
        assertThat(testUsuarios.getIdInstit()).isEqualTo(DEFAULT_ID_INSTIT);
        assertThat(testUsuarios.getCorreo()).isEqualTo(DEFAULT_CORREO);
        assertThat(testUsuarios.getContrasena()).isEqualTo(DEFAULT_CONTRASENA);
        assertThat(testUsuarios.getConfirmarContras()).isEqualTo(DEFAULT_CONFIRMAR_CONTRAS);
        assertThat(testUsuarios.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
        assertThat(testUsuarios.getUsuarioCrea()).isEqualTo(DEFAULT_USUARIO_CREA);
    }

    @Test
    void createUsuariosWithExistingId() throws Exception {
        // Create the Usuarios with an existing ID
        usuarios.setId(1L);

        int databaseSizeBeforeCreate = usuariosRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(usuarios))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Usuarios in the database
        List<Usuarios> usuariosList = usuariosRepository.findAll().collectList().block();
        assertThat(usuariosList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuariosRepository.findAll().collectList().block().size();
        // set the field null
        usuarios.setFecha(null);

        // Create the Usuarios, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(usuarios))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Usuarios> usuariosList = usuariosRepository.findAll().collectList().block();
        assertThat(usuariosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkUsuarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuariosRepository.findAll().collectList().block().size();
        // set the field null
        usuarios.setUsuario(null);

        // Create the Usuarios, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(usuarios))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Usuarios> usuariosList = usuariosRepository.findAll().collectList().block();
        assertThat(usuariosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDescrIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuariosRepository.findAll().collectList().block().size();
        // set the field null
        usuarios.setDescr(null);

        // Create the Usuarios, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(usuarios))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Usuarios> usuariosList = usuariosRepository.findAll().collectList().block();
        assertThat(usuariosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkIdInstitIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuariosRepository.findAll().collectList().block().size();
        // set the field null
        usuarios.setIdInstit(null);

        // Create the Usuarios, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(usuarios))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Usuarios> usuariosList = usuariosRepository.findAll().collectList().block();
        assertThat(usuariosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllUsuarios() {
        // Initialize the database
        usuariosRepository.save(usuarios).block();

        // Get all the usuariosList
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
            .value(hasItem(usuarios.getId().intValue()))
            .jsonPath("$.[*].fecha")
            .value(hasItem(DEFAULT_FECHA.toString()))
            .jsonPath("$.[*].usuario")
            .value(hasItem(DEFAULT_USUARIO))
            .jsonPath("$.[*].descr")
            .value(hasItem(DEFAULT_DESCR))
            .jsonPath("$.[*].idInstit")
            .value(hasItem(DEFAULT_ID_INSTIT))
            .jsonPath("$.[*].correo")
            .value(hasItem(DEFAULT_CORREO))
            .jsonPath("$.[*].contrasena")
            .value(hasItem(DEFAULT_CONTRASENA))
            .jsonPath("$.[*].confirmarContras")
            .value(hasItem(DEFAULT_CONFIRMAR_CONTRAS))
            .jsonPath("$.[*].estatus")
            .value(hasItem(DEFAULT_ESTATUS.toString()))
            .jsonPath("$.[*].usuarioCrea")
            .value(hasItem(DEFAULT_USUARIO_CREA));
    }

    @Test
    void getUsuarios() {
        // Initialize the database
        usuariosRepository.save(usuarios).block();

        // Get the usuarios
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, usuarios.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(usuarios.getId().intValue()))
            .jsonPath("$.fecha")
            .value(is(DEFAULT_FECHA.toString()))
            .jsonPath("$.usuario")
            .value(is(DEFAULT_USUARIO))
            .jsonPath("$.descr")
            .value(is(DEFAULT_DESCR))
            .jsonPath("$.idInstit")
            .value(is(DEFAULT_ID_INSTIT))
            .jsonPath("$.correo")
            .value(is(DEFAULT_CORREO))
            .jsonPath("$.contrasena")
            .value(is(DEFAULT_CONTRASENA))
            .jsonPath("$.confirmarContras")
            .value(is(DEFAULT_CONFIRMAR_CONTRAS))
            .jsonPath("$.estatus")
            .value(is(DEFAULT_ESTATUS.toString()))
            .jsonPath("$.usuarioCrea")
            .value(is(DEFAULT_USUARIO_CREA));
    }

    @Test
    void getNonExistingUsuarios() {
        // Get the usuarios
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingUsuarios() throws Exception {
        // Initialize the database
        usuariosRepository.save(usuarios).block();

        int databaseSizeBeforeUpdate = usuariosRepository.findAll().collectList().block().size();

        // Update the usuarios
        Usuarios updatedUsuarios = usuariosRepository.findById(usuarios.getId()).block();
        updatedUsuarios
            .fecha(UPDATED_FECHA)
            .usuario(UPDATED_USUARIO)
            .descr(UPDATED_DESCR)
            .idInstit(UPDATED_ID_INSTIT)
            .correo(UPDATED_CORREO)
            .contrasena(UPDATED_CONTRASENA)
            .confirmarContras(UPDATED_CONFIRMAR_CONTRAS)
            .estatus(UPDATED_ESTATUS)
            .usuarioCrea(UPDATED_USUARIO_CREA);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedUsuarios.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedUsuarios))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Usuarios in the database
        List<Usuarios> usuariosList = usuariosRepository.findAll().collectList().block();
        assertThat(usuariosList).hasSize(databaseSizeBeforeUpdate);
        Usuarios testUsuarios = usuariosList.get(usuariosList.size() - 1);
        assertThat(testUsuarios.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testUsuarios.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testUsuarios.getDescr()).isEqualTo(UPDATED_DESCR);
        assertThat(testUsuarios.getIdInstit()).isEqualTo(UPDATED_ID_INSTIT);
        assertThat(testUsuarios.getCorreo()).isEqualTo(UPDATED_CORREO);
        assertThat(testUsuarios.getContrasena()).isEqualTo(UPDATED_CONTRASENA);
        assertThat(testUsuarios.getConfirmarContras()).isEqualTo(UPDATED_CONFIRMAR_CONTRAS);
        assertThat(testUsuarios.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testUsuarios.getUsuarioCrea()).isEqualTo(UPDATED_USUARIO_CREA);
    }

    @Test
    void putNonExistingUsuarios() throws Exception {
        int databaseSizeBeforeUpdate = usuariosRepository.findAll().collectList().block().size();
        usuarios.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, usuarios.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(usuarios))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Usuarios in the database
        List<Usuarios> usuariosList = usuariosRepository.findAll().collectList().block();
        assertThat(usuariosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchUsuarios() throws Exception {
        int databaseSizeBeforeUpdate = usuariosRepository.findAll().collectList().block().size();
        usuarios.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(usuarios))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Usuarios in the database
        List<Usuarios> usuariosList = usuariosRepository.findAll().collectList().block();
        assertThat(usuariosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamUsuarios() throws Exception {
        int databaseSizeBeforeUpdate = usuariosRepository.findAll().collectList().block().size();
        usuarios.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(usuarios))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Usuarios in the database
        List<Usuarios> usuariosList = usuariosRepository.findAll().collectList().block();
        assertThat(usuariosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateUsuariosWithPatch() throws Exception {
        // Initialize the database
        usuariosRepository.save(usuarios).block();

        int databaseSizeBeforeUpdate = usuariosRepository.findAll().collectList().block().size();

        // Update the usuarios using partial update
        Usuarios partialUpdatedUsuarios = new Usuarios();
        partialUpdatedUsuarios.setId(usuarios.getId());

        partialUpdatedUsuarios
            .descr(UPDATED_DESCR)
            .idInstit(UPDATED_ID_INSTIT)
            .correo(UPDATED_CORREO)
            .confirmarContras(UPDATED_CONFIRMAR_CONTRAS);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedUsuarios.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedUsuarios))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Usuarios in the database
        List<Usuarios> usuariosList = usuariosRepository.findAll().collectList().block();
        assertThat(usuariosList).hasSize(databaseSizeBeforeUpdate);
        Usuarios testUsuarios = usuariosList.get(usuariosList.size() - 1);
        assertThat(testUsuarios.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testUsuarios.getUsuario()).isEqualTo(DEFAULT_USUARIO);
        assertThat(testUsuarios.getDescr()).isEqualTo(UPDATED_DESCR);
        assertThat(testUsuarios.getIdInstit()).isEqualTo(UPDATED_ID_INSTIT);
        assertThat(testUsuarios.getCorreo()).isEqualTo(UPDATED_CORREO);
        assertThat(testUsuarios.getContrasena()).isEqualTo(DEFAULT_CONTRASENA);
        assertThat(testUsuarios.getConfirmarContras()).isEqualTo(UPDATED_CONFIRMAR_CONTRAS);
        assertThat(testUsuarios.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
        assertThat(testUsuarios.getUsuarioCrea()).isEqualTo(DEFAULT_USUARIO_CREA);
    }

    @Test
    void fullUpdateUsuariosWithPatch() throws Exception {
        // Initialize the database
        usuariosRepository.save(usuarios).block();

        int databaseSizeBeforeUpdate = usuariosRepository.findAll().collectList().block().size();

        // Update the usuarios using partial update
        Usuarios partialUpdatedUsuarios = new Usuarios();
        partialUpdatedUsuarios.setId(usuarios.getId());

        partialUpdatedUsuarios
            .fecha(UPDATED_FECHA)
            .usuario(UPDATED_USUARIO)
            .descr(UPDATED_DESCR)
            .idInstit(UPDATED_ID_INSTIT)
            .correo(UPDATED_CORREO)
            .contrasena(UPDATED_CONTRASENA)
            .confirmarContras(UPDATED_CONFIRMAR_CONTRAS)
            .estatus(UPDATED_ESTATUS)
            .usuarioCrea(UPDATED_USUARIO_CREA);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedUsuarios.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedUsuarios))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Usuarios in the database
        List<Usuarios> usuariosList = usuariosRepository.findAll().collectList().block();
        assertThat(usuariosList).hasSize(databaseSizeBeforeUpdate);
        Usuarios testUsuarios = usuariosList.get(usuariosList.size() - 1);
        assertThat(testUsuarios.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testUsuarios.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testUsuarios.getDescr()).isEqualTo(UPDATED_DESCR);
        assertThat(testUsuarios.getIdInstit()).isEqualTo(UPDATED_ID_INSTIT);
        assertThat(testUsuarios.getCorreo()).isEqualTo(UPDATED_CORREO);
        assertThat(testUsuarios.getContrasena()).isEqualTo(UPDATED_CONTRASENA);
        assertThat(testUsuarios.getConfirmarContras()).isEqualTo(UPDATED_CONFIRMAR_CONTRAS);
        assertThat(testUsuarios.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testUsuarios.getUsuarioCrea()).isEqualTo(UPDATED_USUARIO_CREA);
    }

    @Test
    void patchNonExistingUsuarios() throws Exception {
        int databaseSizeBeforeUpdate = usuariosRepository.findAll().collectList().block().size();
        usuarios.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, usuarios.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(usuarios))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Usuarios in the database
        List<Usuarios> usuariosList = usuariosRepository.findAll().collectList().block();
        assertThat(usuariosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchUsuarios() throws Exception {
        int databaseSizeBeforeUpdate = usuariosRepository.findAll().collectList().block().size();
        usuarios.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(usuarios))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Usuarios in the database
        List<Usuarios> usuariosList = usuariosRepository.findAll().collectList().block();
        assertThat(usuariosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamUsuarios() throws Exception {
        int databaseSizeBeforeUpdate = usuariosRepository.findAll().collectList().block().size();
        usuarios.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(usuarios))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Usuarios in the database
        List<Usuarios> usuariosList = usuariosRepository.findAll().collectList().block();
        assertThat(usuariosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteUsuarios() {
        // Initialize the database
        usuariosRepository.save(usuarios).block();

        int databaseSizeBeforeDelete = usuariosRepository.findAll().collectList().block().size();

        // Delete the usuarios
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, usuarios.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Usuarios> usuariosList = usuariosRepository.findAll().collectList().block();
        assertThat(usuariosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
