package com.jparams.junit4.data.converter;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ClassConverterTest {
    private ClassConverter subject;

    @Before
    public void setUp() throws Exception {
        subject = new ClassConverter();
    }

    @Test
    public void returnsClassFromName() throws Exception {
        Class<?> clazz = (Class<?>) subject.convert(String.class.getName(), null);
        assertThat(clazz).isSameAs(String.class);
    }

    @Test(expected = ConverterException.class)
    public void throwsConverterExceptionOnUnknownClass() throws Exception {
        subject.convert("random class name", null);
    }
}
