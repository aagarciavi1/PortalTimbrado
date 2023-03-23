package mx.conacyt.crip.proyecto03.domain;

import static org.assertj.core.api.Assertions.assertThat;

import mx.conacyt.crip.proyecto03.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RepGraficaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RepGrafica.class);
        RepGrafica repGrafica1 = new RepGrafica();
        repGrafica1.setId(1L);
        RepGrafica repGrafica2 = new RepGrafica();
        repGrafica2.setId(repGrafica1.getId());
        assertThat(repGrafica1).isEqualTo(repGrafica2);
        repGrafica2.setId(2L);
        assertThat(repGrafica1).isNotEqualTo(repGrafica2);
        repGrafica1.setId(null);
        assertThat(repGrafica1).isNotEqualTo(repGrafica2);
    }
}
