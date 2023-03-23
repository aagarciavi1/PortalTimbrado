package mx.conacyt.crip.proyecto03.domain;

import static org.assertj.core.api.Assertions.assertThat;

import mx.conacyt.crip.proyecto03.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MotivosCancelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MotivosCancel.class);
        MotivosCancel motivosCancel1 = new MotivosCancel();
        motivosCancel1.setId(1L);
        MotivosCancel motivosCancel2 = new MotivosCancel();
        motivosCancel2.setId(motivosCancel1.getId());
        assertThat(motivosCancel1).isEqualTo(motivosCancel2);
        motivosCancel2.setId(2L);
        assertThat(motivosCancel1).isNotEqualTo(motivosCancel2);
        motivosCancel1.setId(null);
        assertThat(motivosCancel1).isNotEqualTo(motivosCancel2);
    }
}
