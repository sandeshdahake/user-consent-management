package com.user.consent.management.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.user.consent.management.web.rest.TestUtil;

public class DelegateTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Delegate.class);
        Delegate delegate1 = new Delegate();
        delegate1.setId("id1");
        Delegate delegate2 = new Delegate();
        delegate2.setId(delegate1.getId());
        assertThat(delegate1).isEqualTo(delegate2);
        delegate2.setId("id2");
        assertThat(delegate1).isNotEqualTo(delegate2);
        delegate1.setId(null);
        assertThat(delegate1).isNotEqualTo(delegate2);
    }
}
