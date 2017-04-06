package com.jparams.junit4.test.data.reader;

import com.jparams.junit4.test.data.provider.Provider;
import com.jparams.junit4.test.data.reader.exception.ReaderException;
import org.junit.Before;
import org.junit.Test;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.assertj.core.api.Assertions.assertThat;

public class ProvidedDataReaderTest {
    private ProvidedDataReader subject;

    @Before
    public void setUp() throws Exception {
        subject = new ProvidedDataReader();
    }

    @Test
    public void getsDataFromProvider() throws Exception {
        Object[][] data = subject.readData(SimpleProvider.class);
        assertThat(data).hasSize(1);
        assertThat(data[0]).containsExactly(1, "2", "3");
    }

    @Test
    public void throwsReaderExceptionWhenProviderInstantiationFails() throws Exception {
        catchException(subject).readData(BrokenProvider.class);

        assertThat(caughtException()).isInstanceOf(ReaderException.class)
            .hasMessage("Failed to Instantiate provider")
            .hasCauseExactlyInstanceOf(InstantiationException.class);
    }

    @Test
    public void throwsReaderExceptionWhenProviderAccessFails() throws Exception {
        catchException(subject).readData(PrivateProvider.class);

        assertThat(caughtException()).isInstanceOf(ReaderException.class)
            .hasMessage("Failed to Instantiate provider")
            .hasCauseExactlyInstanceOf(IllegalAccessException.class);
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
