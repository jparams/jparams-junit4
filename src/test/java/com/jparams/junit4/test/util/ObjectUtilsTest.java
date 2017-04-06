package com.jparams.junit4.test.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ObjectUtilsTest {
    @Test
    public void joinsArrays() throws Exception {
        String joined = ObjectUtils.join(new Object[] {
            1, "a", "b"
        }, ", ");

        assertThat(joined).isEqualTo("1, a, b");
    }

    @Test
    public void joinsIterable() throws Exception {
        List<Object> strings = Arrays.<Object>asList(1, "b", "c");
        String joined = ObjectUtils.join(strings, ", ");

        assertThat(joined).isEqualTo("1, b, c");
    }
}
