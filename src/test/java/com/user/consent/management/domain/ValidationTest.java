package com.user.consent.management.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.user.consent.management.web.rest.TestUtil;

public class ValidationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Validation.class);
        Validation validation1 = new Validation();
        validation1.setId("id1");
        Validation validation2 = new Validation();
        validation2.setId(validation1.getId());
        assertThat(validation1).isEqualTo(validation2);
        validation2.setId("id2");
        assertThat(validation1).isNotEqualTo(validation2);
        validation1.setId(null);
        assertThat(validation1).isNotEqualTo(validation2);
    }
}
