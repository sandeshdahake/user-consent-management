package com.user.consent.management.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.user.consent.management.web.rest.TestUtil;

public class PreferenceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Preference.class);
        Preference preference1 = new Preference();
        preference1.setId("id1");
        Preference preference2 = new Preference();
        preference2.setId(preference1.getId());
        assertThat(preference1).isEqualTo(preference2);
        preference2.setId("id2");
        assertThat(preference1).isNotEqualTo(preference2);
        preference1.setId(null);
        assertThat(preference1).isNotEqualTo(preference2);
    }
}
