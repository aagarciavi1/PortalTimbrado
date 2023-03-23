package mx.conacyt.crip.proyecto03.domain;

import static org.assertj.core.api.Assertions.assertThat;

import mx.conacyt.crip.proyecto03.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EstatusCFDITest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstatusCFDI.class);
        EstatusCFDI estatusCFDI1 = new EstatusCFDI();
        estatusCFDI1.setId(1L);
        EstatusCFDI estatusCFDI2 = new EstatusCFDI();
        estatusCFDI2.setId(estatusCFDI1.getId());
        assertThat(estatusCFDI1).isEqualTo(estatusCFDI2);
        estatusCFDI2.setId(2L);
        assertThat(estatusCFDI1).isNotEqualTo(estatusCFDI2);
        estatusCFDI1.setId(null);
        assertThat(estatusCFDI1).isNotEqualTo(estatusCFDI2);
    }
}
