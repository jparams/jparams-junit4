package com.jparams.junit4.reflection;

import com.jparams.junit4.data.ReadWith;
import com.jparams.junit4.data.converter.Convert;
import org.junit.Before;
import org.junit.Test;

import java.lang.annotation.Annotation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class MethodParameterTest {
    private MethodParameter subject;
    private Convert mockAnnotation;

    @Before
    public void setUp() throws Exception {
        mockAnnotation = mock(Convert.class);
        doReturn(Convert.class).when(mockAnnotation).annotationType();

        subject = new MethodParameter(String.class, new Annotation[] {mockAnnotation});
    }

    @Test
    public void returnsType() throws Exception {
        assertThat(subject.getType()).isEqualTo(String.class);
    }

    @Test
    public void returnsTrueIfAnnotationPresent() throws Exception {
        assertThat(subject.isAnnotationPresent(Convert.class)).isTrue();
    }

    @Test
    public void returnsFalseIfAnnotationNotPresent() throws Exception {
        assertThat(subject.isAnnotationPresent(ReadWith.class)).isFalse();
    }

    @Test
    public void returnsAnnotationByType() throws Exception {
        Convert annotation = subject.getAnnotation(Convert.class);
        assertThat(annotation).isSameAs(mockAnnotation);
    }

    @Test
    public void returnsNullIfAnnotationNotFound() throws Exception {
        ReadWith annotation = subject.getAnnotation(ReadWith.class);
        assertThat(annotation).isNull();
    }
}
