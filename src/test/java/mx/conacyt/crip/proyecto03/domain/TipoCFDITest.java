package mx.conacyt.crip.proyecto03.domain;

import static org.assertj.core.api.Assertions.assertThat;

import mx.conacyt.crip.proyecto03.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TipoCFDITest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoCFDI.class);
        TipoCFDI tipoCFDI1 = new TipoCFDI();
        tipoCFDI1.setId(1L);
        TipoCFDI tipoCFDI2 = new TipoCFDI();
        tipoCFDI2.setId(tipoCFDI1.getId());
        assertThat(tipoCFDI1).isEqualTo(tipoCFDI2);
        tipoCFDI2.setId(2L);
        assertThat(tipoCFDI1).isNotEqualTo(tipoCFDI2);
        tipoCFDI1.setId(null);
        assertThat(tipoCFDI1).isNotEqualTo(tipoCFDI2);
    }
}
