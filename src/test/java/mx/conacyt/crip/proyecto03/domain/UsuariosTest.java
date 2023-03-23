package mx.conacyt.crip.proyecto03.domain;

import static org.assertj.core.api.Assertions.assertThat;

import mx.conacyt.crip.proyecto03.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UsuariosTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Usuarios.class);
        Usuarios usuarios1 = new Usuarios();
        usuarios1.setId(1L);
        Usuarios usuarios2 = new Usuarios();
        usuarios2.setId(usuarios1.getId());
        assertThat(usuarios1).isEqualTo(usuarios2);
        usuarios2.setId(2L);
        assertThat(usuarios1).isNotEqualTo(usuarios2);
        usuarios1.setId(null);
        assertThat(usuarios1).isNotEqualTo(usuarios2);
    }
}
