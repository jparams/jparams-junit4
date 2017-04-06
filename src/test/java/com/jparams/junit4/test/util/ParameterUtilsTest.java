package com.jparams.junit4.test.util;

import com.jparams.junit4.test.data.Data;
import com.jparams.junit4.test.data.reader.DataReader;
import com.jparams.junit4.test.data.reader.Reader;
import com.jparams.junit4.test.description.Name;
import com.jparams.junit4.test.util.exception.InstanceCreationException;
import org.junit.Test;
import org.junit.runners.model.FrameworkMethod;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static junit.framework.TestCase.fail;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ParameterUtilsTest {
    @Test
    public void returnsTrueIfReaderAnnotationPresent() throws Exception {
        FrameworkMethod mockFrameworkMethod = mock(FrameworkMethod.class);
        Reader mockReader = mockAnnotation(Reader.class);
        when(mockFrameworkMethod.getAnnotations()).thenReturn(new Annotation[] {mockReader});

        boolean parameterized = ParameterUtils.isParameterized(mockFrameworkMethod);

        assertThat(parameterized).isTrue();
    }

    @Test
    public void returnsTrueIfReaderPresentOnAnnotation() throws Exception {
        FrameworkMethod mockFrameworkMethod = mock(FrameworkMethod.class);
        Data mockDataAnnotation = mockAnnotation(Data.class);
        when(mockFrameworkMethod.getAnnotations()).thenReturn(new Annotation[] {mockDataAnnotation});

        boolean parameterized = ParameterUtils.isParameterized(mockFrameworkMethod);

        assertThat(parameterized).isTrue();
    }

    @Test
    public void returnsFalseIfReaderAnnotationNotPresent() throws Exception {
        FrameworkMethod mockFrameworkMethod = mock(FrameworkMethod.class);
        Name mockAnnotation = mockAnnotation(Name.class);
        when(mockFrameworkMethod.getAnnotations()).thenReturn(new Annotation[] {mockAnnotation});

        boolean parameterized = ParameterUtils.isParameterized(mockFrameworkMethod);

        assertThat(parameterized).isFalse();
    }

    @Test
    public void getParameterizedDataFromReader() throws Exception {
        FrameworkMethod mockFrameworkMethod = mock(FrameworkMethod.class);
        Reader mockReader = mockReader(DummyReader.class);
        when(mockFrameworkMethod.getAnnotations()).thenReturn(new Annotation[] {mockReader});

        Object[][] data = ParameterUtils.getParameterizedData(mockFrameworkMethod);
        assertThat(data).hasSize(1);
        assertThat(data[0]).containsExactly("a", "b", "c");
    }

    @Test
    public void throwsInstanceCreationExceptionWhenReaderCannotBeCreated() throws Exception {
        FrameworkMethod mockFrameworkMethod = mock(FrameworkMethod.class);
        Reader mockReader = mockReader(ReaderWithConstructor.class);
        when(mockFrameworkMethod.getAnnotations()).thenReturn(new Annotation[] {mockReader});

        try {
            ParameterUtils.getParameterizedData(mockFrameworkMethod);
            fail("exception expected");
        } catch (InstanceCreationException e) {
            assertThat(e).hasMessage("Error instantiating data reader")
                .hasCauseExactlyInstanceOf(InstantiationException.class);
        }
    }

    @Test
    public void throwsInstanceCreationExceptionWhenReaderCannotBeAccessed() throws Exception {
        FrameworkMethod mockFrameworkMethod = mock(FrameworkMethod.class);
        Reader mockReader = mockReader(PrivateReader.class);
        when(mockFrameworkMethod.getAnnotations()).thenReturn(new Annotation[] {mockReader});

        try {
            ParameterUtils.getParameterizedData(mockFrameworkMethod);
            fail("exception expected");
        } catch (InstanceCreationException e) {
            assertThat(e).hasMessage("Error instantiating data reader")
                .hasCauseExactlyInstanceOf(IllegalAccessException.class);
        }
    }

    @Test
    public void getParameterizedDataFromReaderOnAnnotation() throws Exception {
        FrameworkMethod mockFrameworkMethod = mock(FrameworkMethod.class);
        DummyAnnotation mockDummyAnnotation = mockAnnotation(DummyAnnotation.class);
        doReturn("a, b, c").when(mockDummyAnnotation).value();
        when(mockFrameworkMethod.getAnnotations()).thenReturn(new Annotation[] {mockDummyAnnotation});

        Object[][] data = ParameterUtils.getParameterizedData(mockFrameworkMethod);

        assertThat(data).hasSize(1);
        assertThat(data[0]).containsExactly("a", "b", "c");
    }

    @Test
    public void getParameterizedDataFromReaderOnAnnotationWhenNoSource() throws Exception {
        FrameworkMethod mockFrameworkMethod = mock(FrameworkMethod.class);
        AnnotationWithoutValue mockDummyAnnotation = mockAnnotation(AnnotationWithoutValue.class);
        when(mockFrameworkMethod.getAnnotations()).thenReturn(new Annotation[] {mockDummyAnnotation});

        Object[][] data = ParameterUtils.getParameterizedData(mockFrameworkMethod);

        assertThat(data).hasSize(1);
        assertThat(data[0]).containsExactly("a", "b", "c");
    }

    @Test
    public void getParameterizedDataReturnsNullWhenAnnotationNotFound() throws Exception {
        FrameworkMethod mockFrameworkMethod = mock(FrameworkMethod.class);
        when(mockFrameworkMethod.getAnnotations()).thenReturn(new Annotation[] {});

        Object[][] data = ParameterUtils.getParameterizedData(mockFrameworkMethod);

        assertThat(data).isNull();
    }

    private <T extends Annotation> T mockAnnotation(Class<T> clazz) {
        T mock = mock(clazz);
        doReturn(clazz).when(mock).annotationType();
        return mock;
    }

    private Reader mockReader(Class<? extends DataReader> readerClass) {
        Reader mockReader = mockAnnotation(Reader.class);
        doReturn(readerClass).when(mockReader).value();
        return mockReader;
    }

    public static class DummyReader implements DataReader<Object> {
        @Override
        public Object[][] readData(Object source) {
            assertThat(source).isNull();

            return new Object[][] {
                {
                    "a", "b", "c"
                }
            };
        }
    }

    public static class DummyReader2 implements DataReader<String> {
        @Override
        public Object[][] readData(String source) {
            assertThat(source).isEqualTo("a, b, c");

            return new Object[][] {
                {
                    "a", "b", "c"
                }
            };
        }
    }

    public static class ReaderWithConstructor implements DataReader<Object> {
        public ReaderWithConstructor(String someArg) {
        }

        @Override
        public Object[][] readData(Object source) {
            return new Object[0][];
        }
    }

    private static class PrivateReader implements DataReader<Object> {
        @Override
        public Object[][] readData(Object source) {
            return new Object[0][];
        }
    }

    @Reader(DummyReader2.class)
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface DummyAnnotation {
        String value();
    }

    @Reader(DummyReader.class)
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface AnnotationWithoutValue {
    }
}
