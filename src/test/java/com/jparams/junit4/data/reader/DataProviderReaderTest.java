package com.jparams.junit4.data.reader;

import com.jparams.junit4.data.DataProvider;
import com.jparams.junit4.data.provider.Provider;
import org.junit.Before;
import org.junit.Test;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class DataProviderReaderTest {
    private DataProviderReader subject;

    @Before
    public void setUp() throws Exception {
        subject = new DataProviderReader();
    }

    @Test
    public void getsDataFromProvider() throws Exception {
        Object[][] data = subject.readData(createAnnotation(SimpleProvider.class));
        assertThat(data).hasSize(1);
        assertThat(data[0]).containsExactly(1, "2", "3");
    }

    @Test
    public void throwsReaderExceptionWhenProviderInstantiationFails() throws Exception {
        catchException(subject).readData(createAnnotation(BrokenProvider.class));

        assertThat(caughtException()).isInstanceOf(ReaderException.class)
            .hasMessage("Failed to Instantiate provider")
            .hasCauseExactlyInstanceOf(InstantiationException.class);
    }

    @Test
    public void throwsReaderExceptionWhenProviderAccessFails() throws Exception {
        catchException(subject).readData(createAnnotation(PrivateProvider.class));

        assertThat(caughtException()).isInstanceOf(ReaderException.class)
            .hasMessage("Failed to Instantiate provider")
            .hasCauseExactlyInstanceOf(IllegalAccessException.class);
    }

    private static DataProvider createAnnotation(Class<? extends Provider> provider) {
        DataProvider mockAnnotation = mock(DataProvider.class);
        doReturn(provider).when(mockAnnotation).value();
        return mockAnnotation;
    }

    public static class SimpleProvider implements Provider {
        @Override
        public Object[][] provide() {
            return new Object[][] {
                {
                    1, "2", "3"
                }
            };
        }
    }

    public static class BrokenProvider implements Provider {
        public BrokenProvider(String someArg) {
        }

        @Override
        public Object[][] provide() {
            return new Object[0][];
        }
    }

    private static class PrivateProvider implements Provider {
        private PrivateProvider() {
        }

        @Override
        public Object[][] provide() {
            return new Object[0][];
        }
    }
}
