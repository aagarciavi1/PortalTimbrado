package mx.conacyt.crip.proyecto03.domain;

import static org.assertj.core.api.Assertions.assertThat;

import mx.conacyt.crip.proyecto03.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RegimenFisTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegimenFis.class);
        RegimenFis regimenFis1 = new RegimenFis();
        regimenFis1.setId(1L);
        RegimenFis regimenFis2 = new RegimenFis();
        regimenFis2.setId(regimenFis1.getId());
        assertThat(regimenFis1).isEqualTo(regimenFis2);
        regimenFis2.setId(2L);
        assertThat(regimenFis1).isNotEqualTo(regimenFis2);
        regimenFis1.setId(null);
        assertThat(regimenFis1).isNotEqualTo(regimenFis2);
    }
}
