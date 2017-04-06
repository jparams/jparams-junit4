package com.jparams.junit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class ParameterizedMethodTest {
    private ParameterizedMethod subject;
    private FrameworkMethod mockFrameworkMethod;
    private Description mockDescription;

    @Before
    public void setUp() throws Exception {
        mockFrameworkMethod = mock(FrameworkMethod.class);
        mockDescription = mock(Description.class);
        subject = new ParameterizedMethod(mockFrameworkMethod, mockDescription, new Object[][] {{"a", "1"}, {"b", "2"}});
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

    @Test
    public void returnsFrameworkMethod() throws Exception {
        assertThat(subject.getMethod()).isSameAs(mockFrameworkMethod);
    }

    @Test
    public void returnsDescription() throws Exception {
        assertThat(subject.getDescription()).isSameAs(mockDescription);
    }
}
