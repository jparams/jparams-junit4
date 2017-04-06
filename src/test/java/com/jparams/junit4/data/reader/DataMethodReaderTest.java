package com.jparams.junit4.data.reader;

import com.jparams.junit4.data.DataMethod;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class DataMethodReaderTest {
    private DataMethodReader subject;

    @Before
    public void setUp() throws Exception {
        subject = new DataMethodReader();
    }

    @Test
    public void readsDataFromMethod() throws Exception {
        Object[][] data = subject.readData(createAnnotation("data"));

        assertThat(data).hasSize(1);
        assertThat(data[0]).containsExactly("a", "b", "c");
    }

    @Test
    public void throwsReaderExceptionOnMethodNotFound() throws Exception {
        catchException(subject).readData(createAnnotation("random"));

        assertThat(caughtException()).isInstanceOf(ReaderException.class)
            .hasMessage("Unable to find method with name random")
            .hasCauseExactlyInstanceOf(NoSuchMethodException.class);
    }

    @Test
    public void throwsReaderExceptionOnInvocationTargetException() throws Exception {
        catchException(subject).readData(createAnnotation("throwsException"));

        assertThat(caughtException()).isInstanceOf(ReaderException.class)
            .hasMessage("Error invoking data method")
            .hasCauseExactlyInstanceOf(InvocationTargetException.class);
    }

    @Test
    public void throwsReaderExceptionOnNonPublicMethod() throws Exception {
        catchException(subject).readData(createAnnotation("nonPublic"));

        assertThat(caughtException()).isInstanceOf(ReaderException.class)
            .hasMessage("Method must have the signature public static Object[][]");
    }

    @Test
    public void throwsReaderExceptionOnNonStaticMethod() throws Exception {
        catchException(subject).readData(createAnnotation("notStatic"));

        assertThat(caughtException()).isInstanceOf(ReaderException.class)
            .hasMessage("Method must have the signature public static Object[][]");
    }

    @Test
    public void throwsReaderExceptionOnBadReturnType() throws Exception {
        catchException(subject).readData(createAnnotation("returnsString"));

        assertThat(caughtException()).isInstanceOf(ReaderException.class)
            .hasMessage("Method must have the signature public static Object[][]");
    }

    private static DataMethod createAnnotation(String method) {
        DataMethod mockAnnotation = mock(DataMethod.class);
        doReturn(DataMethodReaderTest.class).when(mockAnnotation).source();
        doReturn(method).when(mockAnnotation).method();
        return mockAnnotation;
    }

    public static Object[][] data() {
        return new Object[][] {{"a", "b", "c"}};
    }


    public static Object[][] throwsException() {
        throw new RuntimeException("exception!");
    }

    static Object[][] nonPublic() {
        throw new RuntimeException("exception!");
    }

    public Object[][] notStatic() {
        throw new RuntimeException("exception!");
    }

    public String returnsString() {
        throw new RuntimeException("exception!");
    }
}
