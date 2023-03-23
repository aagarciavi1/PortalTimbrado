package mx.conacyt.crip.proyecto03.domain;

import static org.assertj.core.api.Assertions.assertThat;

import mx.conacyt.crip.proyecto03.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FuncionalidadesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Funcionalidades.class);
        Funcionalidades funcionalidades1 = new Funcionalidades();
        funcionalidades1.setId(1L);
        Funcionalidades funcionalidades2 = new Funcionalidades();
        funcionalidades2.setId(funcionalidades1.getId());
        assertThat(funcionalidades1).isEqualTo(funcionalidades2);
        funcionalidades2.setId(2L);
        assertThat(funcionalidades1).isNotEqualTo(funcionalidades2);
        funcionalidades1.setId(null);
        assertThat(funcionalidades1).isNotEqualTo(funcionalidades2);
    }
}
