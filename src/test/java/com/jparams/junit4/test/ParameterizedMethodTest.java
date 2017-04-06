package com.jparams.junit4.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class ParameterizedMethodTest {
    private ParameterizedMethod subject;

    @Before
    public void setUp() throws Exception {
        subject = new ParameterizedMethod(null, mock(Description.class), new Object[][] {{"a", "1"}, {"b", "2"}});
    }

    @Test
    public void readsData() throws Exception {
        Object[] data = subject.getParameters();

        assertThat(data).hasSize(2).containsExactly("a", "1");
    }

    @Test
    public void iteratesOverData() throws Exception {
        assertThat(subject.getParameters()).hasSize(2).containsExactly("a", "1");
        assertThat(subject.getParameters()).hasSize(2).containsExactly("b", "2");
        assertThat(subject.getParameters()).isNull();

        // continues to return null after
        assertThat(subject.getParameters()).isNull();
    }
}
