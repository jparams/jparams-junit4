package com.jparams.junit4;

import com.jparams.junit4.data.Data;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runner.manipulation.Filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JParamsTestRunnerTest {
    @Test
    public void returnsDescriptionOfTestClass() throws Exception {
        JParamsTestRunner subject = new JParamsTestRunner(TestClass.class);

        Description description = subject.getDescription();

        assertThat(description.getDisplayName()).isEqualTo(TestClass.class.getName());
    }

    @Test
    public void returnsDescriptionIsSuite() throws Exception {
        JParamsTestRunner subject = new JParamsTestRunner(TestClass.class);

        Description description = subject.getDescription();

        assertThat(description.isSuite()).isTrue();
    }

    @Test
    public void returnsDescriptionOfChildTests() throws Exception {
        JParamsTestRunner subject = new JParamsTestRunner(TestClass.class);

        Description description = subject.getDescription();

        ArrayList<Description> children = description.getChildren();
        assertThat(children.size()).isEqualTo(2);

        List<String> displayNames = Arrays.asList(children.get(0).getDisplayName(), children.get(1).getDisplayName());
        assertThat(displayNames).containsExactly(
            "test1(com.jparams.junit4.JParamsTestRunnerTest$TestClass)",
            "test2(com.jparams.junit4.JParamsTestRunnerTest$TestClass)"
        );
    }

    @Test
    public void descriptionForParameterizedTestIsSuite() throws Exception {
        JParamsTestRunner subject = new JParamsTestRunner(ParameterizedTestClass.class);

        Description description = subject.getDescription();

        assertThat(description.getChildren().get(0).isSuite()).isTrue();
    }

    @Test
    public void descriptionForParameterizedTestHasChildren() throws Exception {
        JParamsTestRunner subject = new JParamsTestRunner(ParameterizedTestClass.class);

        Description description = subject.getDescription();
        ArrayList<Description> children = description.getChildren().get(0).getChildren();

        assertThat(children).hasSize(2);

        assertThat(children.get(0).getDisplayName()).isEqualTo("[0] - 1(test)");
        assertThat(children.get(0).isTest()).isTrue();

        assertThat(children.get(1).getDisplayName()).isEqualTo("[1] - 2(test)");
        assertThat(children.get(1).isTest()).isTrue();
    }

    @Test
    public void ignoresTestIfFilterReturnsFalse() throws Exception {
        Filter mockFilter = mock(Filter.class);
        when(mockFilter.shouldRun(any(Description.class))).thenReturn(false);
        when(mockFilter.shouldRun(any(Description.class))).thenReturn(true);

        JParamsTestRunner subject = new JParamsTestRunner(ParameterizedTestClass.class);
        subject.filter(mockFilter);

        Description description = subject.getDescription();
        assertThat(description.getChildren()).hasSize(1);
    }

    public static class TestClass {
        @Test
        public void test1() throws Exception {

        }

        @Test
        public void test2() throws Exception {

        }
    }

    @RunWith(JParamsTestRunner.class)
    public static class ParameterizedTestClass {
        @Data({
            "1",
            "2"
        })
        @Test
        public void test(String num) throws Exception {

        }
    }
}
