package com.jparams.junit4.util;

import com.jparams.junit4.data.Data;
import com.jparams.junit4.data.ReadWith;
import com.jparams.junit4.data.reader.Reader;
import com.jparams.junit4.description.Name;
import org.junit.Test;
import org.junit.runners.model.FrameworkMethod;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ParametersTest {
    @Test
    public void returnsTrueIfReaderPresentOnAnnotation() throws Exception {
        FrameworkMethod mockFrameworkMethod = mock(FrameworkMethod.class);
        Data mockDataAnnotation = mockAnnotation(Data.class);
        when(mockFrameworkMethod.getAnnotations()).thenReturn(new Annotation[] {mockDataAnnotation});

        boolean parameterized = Parameters.isParameterized(mockFrameworkMethod);

        assertThat(parameterized).isTrue();
    }

    @Test
    public void returnsFalseIfReaderAnnotationNotPresent() throws Exception {
        FrameworkMethod mockFrameworkMethod = mock(FrameworkMethod.class);
        Name mockAnnotation = mockAnnotation(Name.class);
        when(mockFrameworkMethod.getAnnotations()).thenReturn(new Annotation[] {mockAnnotation});

        boolean parameterized = Parameters.isParameterized(mockFrameworkMethod);

        assertThat(parameterized).isFalse();
    }

    @Test
    public void getParameterizedDataFromReaderOnAnnotation() throws Exception {
        FrameworkMethod mockFrameworkMethod = mock(FrameworkMethod.class);
        DummyAnnotation mockDummyAnnotation = mockAnnotation(DummyAnnotation.class);
        doReturn("a, b, c").when(mockDummyAnnotation).value();
        when(mockFrameworkMethod.getAnnotations()).thenReturn(new Annotation[] {mockDummyAnnotation});

        Object[][] data = Parameters.getParameterizedData(mockFrameworkMethod);

        assertThat(data).hasSize(1);
        assertThat(data[0]).containsExactly("a", "b", "c");
    }

    @Test
    public void getParameterizedDataFromReaderOnAnnotationWhenNoSource() throws Exception {
        FrameworkMethod mockFrameworkMethod = mock(FrameworkMethod.class);
        AnnotationWithoutValue mockDummyAnnotation = mockAnnotation(AnnotationWithoutValue.class);
        when(mockFrameworkMethod.getAnnotations()).thenReturn(new Annotation[] {mockDummyAnnotation});

        Object[][] data = Parameters.getParameterizedData(mockFrameworkMethod);

        assertThat(data).hasSize(1);
        assertThat(data[0]).containsExactly("a", "b", "c");
    }

    @Test
    public void getParameterizedDataReturnsNullWhenAnnotationNotFound() throws Exception {
        FrameworkMethod mockFrameworkMethod = mock(FrameworkMethod.class);
        when(mockFrameworkMethod.getAnnotations()).thenReturn(new Annotation[] {});

        Object[][] data = Parameters.getParameterizedData(mockFrameworkMethod);

        assertThat(data).isNull();
    }

    private <T extends Annotation> T mockAnnotation(Class<T> clazz) {
        T mock = mock(clazz);
        doReturn(clazz).when(mock).annotationType();
        return mock;
    }

    public static class DummyReader implements Reader<AnnotationWithoutValue> {
        @Override
        public Object[][] readData(AnnotationWithoutValue annotation) {
            return new Object[][] {
                {
                    "a", "b", "c"
                }
            };
        }
    }

    public static class DummyReader2 implements com.jparams.junit4.data.reader.Reader<DummyAnnotation> {
        @Override
        public Object[][] readData(DummyAnnotation annotation) {
            assertThat(annotation.value()).isEqualTo("a, b, c");

            return new Object[][] {
                {
                    "a", "b", "c"
                }
            };
        }
    }

    @ReadWith(value = DummyReader2.class)
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface DummyAnnotation {
        String value();
    }

    @ReadWith(value = DummyReader.class)
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface AnnotationWithoutValue {
    }
}
