package com.jparams.junit4.test.data.reader;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InlineDataReaderTest {
    private InlineDataReader subject;

    @Before
    public void setUp() throws Exception {
        subject = new InlineDataReader();
    }

    @Test
    public void returnsDataAsList() throws Exception {
        Object[][] data = subject.readData(new String[] {
            "a, b, c, d",
            "e, f, g, h"
        });

        assertThat(data).hasSize(2);
        assertThat(data[0]).containsExactly("a", "b", "c", "d");
        assertThat(data[1]).containsExactly("e", "f", "g", "h");
    }
}
