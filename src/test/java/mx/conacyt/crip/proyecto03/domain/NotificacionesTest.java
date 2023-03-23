package mx.conacyt.crip.proyecto03.domain;

import static org.assertj.core.api.Assertions.assertThat;

import mx.conacyt.crip.proyecto03.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NotificacionesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Notificaciones.class);
        Notificaciones notificaciones1 = new Notificaciones();
        notificaciones1.setId(1L);
        Notificaciones notificaciones2 = new Notificaciones();
        notificaciones2.setId(notificaciones1.getId());
        assertThat(notificaciones1).isEqualTo(notificaciones2);
        notificaciones2.setId(2L);
        assertThat(notificaciones1).isNotEqualTo(notificaciones2);
        notificaciones1.setId(null);
        assertThat(notificaciones1).isNotEqualTo(notificaciones2);
    }
}
