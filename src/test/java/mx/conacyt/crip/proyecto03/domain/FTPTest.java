package mx.conacyt.crip.proyecto03.domain;

import static org.assertj.core.api.Assertions.assertThat;

import mx.conacyt.crip.proyecto03.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FTPTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FTP.class);
        FTP fTP1 = new FTP();
        fTP1.setId(1L);
        FTP fTP2 = new FTP();
        fTP2.setId(fTP1.getId());
        assertThat(fTP1).isEqualTo(fTP2);
        fTP2.setId(2L);
        assertThat(fTP1).isNotEqualTo(fTP2);
        fTP1.setId(null);
        assertThat(fTP1).isNotEqualTo(fTP2);
    }
}
