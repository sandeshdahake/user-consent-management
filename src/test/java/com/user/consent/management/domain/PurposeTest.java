package com.user.consent.management.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.user.consent.management.web.rest.TestUtil;

public class PurposeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Purpose.class);
        Purpose purpose1 = new Purpose();
        purpose1.setId("id1");
        Purpose purpose2 = new Purpose();
        purpose2.setId(purpose1.getId());
        assertThat(purpose1).isEqualTo(purpose2);
        purpose2.setId("id2");
        assertThat(purpose1).isNotEqualTo(purpose2);
        purpose1.setId(null);
        assertThat(purpose1).isNotEqualTo(purpose2);
    }
}
