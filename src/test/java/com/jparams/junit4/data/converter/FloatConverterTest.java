package com.jparams.junit4.data.converter;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FloatConverterTest {
    @Test
    public void convertsValue() throws Exception {
        Float num = (Float) new FloatConverter().convert("123.1", null);
        assertThat(num).isEqualTo(123.1f);
    }
}
