package com.jparams.junit4.data.converter;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DoubleConverterTest {
    @Test
    public void convertsValue() throws Exception {
        Double num = (Double) new DoubleConverter().convert("123.1", null);
        assertThat(num).isEqualTo(123.1d);
    }
}
