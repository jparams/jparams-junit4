package com.jparams.junit4.data.converter;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LongConverterTest {
    @Test
    public void convertsValue() throws Exception {
        Long num = (Long) new LongConverter().convert("123", null);
        assertThat(num).isEqualTo(123);
    }
}
