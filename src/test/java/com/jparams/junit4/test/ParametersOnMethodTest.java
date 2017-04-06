package com.jparams.junit4.test;

import com.jparams.junit4.test.data.Data;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JParamsJUnit4TestRunner.class)
public class ParametersOnMethodTest {
    @Data({
        "'a', 'b', 'ab'",
        "'c', 'd', 'cd'",
        "'e', 'f', 'ef'"
    })
    @Test
    public void injectsValues(String letter1, String letter2, String concat) throws Exception {
        assertThat(letter1 + letter2).isEqualTo(concat);
    }

    @Data({
        "'a', 'b', 'ab'",
        "'c', 'd', 'cd'",
        "'e', 'f', 'ef'"
    })
    @Test
    public void injectsValues1(String letter1, String letter2, String concat) throws Exception {
        assertThat(letter1 + letter2).isEqualTo(concat);
    }

    @Test
    public void abc() {
    }
}
