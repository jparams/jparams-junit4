package com.jparams.junit4.data.reader;

import com.jparams.junit4.data.Data;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DataReaderTest {
    private DataReader subject;

    @Before
    public void setUp() throws Exception {
        subject = new DataReader();
    }

    @Test
    public void returnsDataAsList() throws Exception {
        Object[][] data = subject.readData(createAnnotation(
            new String[] {
                "a, b, c, d",
                "e, f, g, h"
            }
        ));

        assertThat(data).hasSize(2);
        assertThat(data[0]).containsExactly("a", "b", "c", "d");
        assertThat(data[1]).containsExactly("e", "f", "g", "h");
    }

    private static Data createAnnotation(String[] data) {
        Data mockAnnotation = mock(Data.class);
        when(mockAnnotation.value()).thenReturn(data);
        return mockAnnotation;
    }
}
