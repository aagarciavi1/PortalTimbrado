package mx.conacyt.crip.proyecto03.domain;

import static org.assertj.core.api.Assertions.assertThat;

import mx.conacyt.crip.proyecto03.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RegistInstitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegistInstit.class);
        RegistInstit registInstit1 = new RegistInstit();
        registInstit1.setId(1L);
        RegistInstit registInstit2 = new RegistInstit();
        registInstit2.setId(registInstit1.getId());
        assertThat(registInstit1).isEqualTo(registInstit2);
        registInstit2.setId(2L);
        assertThat(registInstit1).isNotEqualTo(registInstit2);
        registInstit1.setId(null);
        assertThat(registInstit1).isNotEqualTo(registInstit2);
    }
}
