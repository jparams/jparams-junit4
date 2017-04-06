package com.jparams.junit4.data.converter;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BooleanConverterTest {
    private BooleanConverter subject;

    @Before
    public void setUp() throws Exception {
        subject = new BooleanConverter();
    }

    @Test
    public void mapsTrue() throws Exception {
        Boolean bool = (Boolean) subject.convert("true", null);
        assertThat(bool).isTrue();
    }

    @Test
    public void mapsFalse() throws Exception {
        Boolean bool = (Boolean) subject.convert("false", null);
        assertThat(bool).isFalse();
    }
}
