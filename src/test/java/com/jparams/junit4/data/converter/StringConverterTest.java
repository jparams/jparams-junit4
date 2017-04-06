package com.jparams.junit4.data.converter;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringConverterTest {
    @Test
    public void returnsInput() throws Exception {
        String str = (String) new StringConverter().convert("abc", null);
        assertThat(str).isEqualTo("abc");
    }
}
