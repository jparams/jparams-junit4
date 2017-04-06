package com.jparams.junit4.data.converter;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IntegerConverterTest {
    @Test
    public void convertsValue() throws Exception {
        Integer integer = (Integer) new IntegerConverter().convert("123", null);
        assertThat(integer).isEqualTo(123);
    }
}
