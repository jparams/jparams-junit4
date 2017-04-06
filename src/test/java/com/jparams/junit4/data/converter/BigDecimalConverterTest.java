package com.jparams.junit4.data.converter;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class BigDecimalConverterTest {
    private BigDecimalConverter subject;

    @Before
    public void setUp() throws Exception {
        subject = new BigDecimalConverter();
    }

    @Test
    public void convertsValue() throws Exception {
        BigDecimal num = (BigDecimal) subject.convert("10", null);
        assertThat(num).isEqualTo(BigDecimal.TEN);
    }
}
