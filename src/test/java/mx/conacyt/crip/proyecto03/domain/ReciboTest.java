package mx.conacyt.crip.proyecto03.domain;

import static org.assertj.core.api.Assertions.assertThat;

import mx.conacyt.crip.proyecto03.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReciboTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Recibo.class);
        Recibo recibo1 = new Recibo();
        recibo1.setId(1L);
        Recibo recibo2 = new Recibo();
        recibo2.setId(recibo1.getId());
        assertThat(recibo1).isEqualTo(recibo2);
        recibo2.setId(2L);
        assertThat(recibo1).isNotEqualTo(recibo2);
        recibo1.setId(null);
        assertThat(recibo1).isNotEqualTo(recibo2);
    }
}
