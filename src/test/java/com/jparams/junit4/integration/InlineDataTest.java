package com.jparams.junit4.integration;

import com.jparams.junit4.JParamsTestRunner;
import com.jparams.junit4.data.Data;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@Ignore
@RunWith(JParamsTestRunner.class)
public class InlineDataTest {
    @Data({
        "a, b, c"
    })
    @Test
    public void injectsValues(String var1, String var2, String var3) throws Exception {
        assertThat(var1).isEqualTo("a");
        assertThat(var2).isEqualTo("b");
        assertThat(var3).isEqualTo("c");
    }

    @Ignore
    @Data({
        "a"
    })
    @Test
    public void ignoredTest(String var1) throws Exception {
        fail("test should be ignored");
    }

    @Test
    public void nonParameterizedTest() {
        // should pass
    }
}
