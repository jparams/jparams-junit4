package com.jparams.junit4.test;

import com.jparams.junit4.test.data.Data;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class JParamsTestRunnerJUnit4Test {
    @Test
    public void returnsDescriptionOfTestClass() throws Exception {
        JParamsJUnit4TestRunner subject = new JParamsJUnit4TestRunner(TestClass.class);

        Description description = subject.getDescription();

        assertThat(description.getDisplayName()).isEqualTo(TestClass.class.getName());
    }

    @Test
    public void returnsDescriptionIsSuite() throws Exception {
        JParamsJUnit4TestRunner subject = new JParamsJUnit4TestRunner(TestClass.class);

        Description description = subject.getDescription();

        assertThat(description.isSuite()).isTrue();
    }

    @Test
    public void returnsDescriptionOfChildTests() throws Exception {
        JParamsJUnit4TestRunner subject = new JParamsJUnit4TestRunner(TestClass.class);

        Description description = subject.getDescription();

        ArrayList<Description> children = description.getChildren();
        assertThat(children.size()).isEqualTo(2);

        List<String> displayNames = Arrays.asList(children.get(0).getDisplayName(), children.get(1).getDisplayName());
        assertThat(displayNames).containsExactly(
            "test1(com.jparams.junit4.test.JParamsTestRunnerJUnit4Test$TestClass)",
            "test2(com.jparams.junit4.test.JParamsTestRunnerJUnit4Test$TestClass)"
        );
    }

    @Test
    public void descriptionForParameterizedTestIsSuite() throws Exception {
        JParamsJUnit4TestRunner subject = new JParamsJUnit4TestRunner(ParameterizedTestClass.class);

        Description description = subject.getDescription();

        assertThat(description.getChildren().get(0).isSuite()).isTrue();
    }

    @Test
    public void descriptionForParameterizedTestHasChildren() throws Exception {
        JParamsJUnit4TestRunner subject = new JParamsJUnit4TestRunner(ParameterizedTestClass.class);

        Description description = subject.getDescription();
        ArrayList<Description> children = description.getChildren().get(0).getChildren();

        assertThat(children).hasSize(2);

        assertThat(children.get(0).getDisplayName()).isEqualTo("[0] - 1(test)");
        assertThat(children.get(0).isTest()).isTrue();

        assertThat(children.get(1).getDisplayName()).isEqualTo("[1] - 2(test)");
        assertThat(children.get(1).isTest()).isTrue();
    }

    public static class TestClass {
        @Test
        public void test1() throws Exception {

        }

        @Test
        public void test2() throws Exception {

        }
    }

    @RunWith(JParamsJUnit4TestRunner.class)
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
