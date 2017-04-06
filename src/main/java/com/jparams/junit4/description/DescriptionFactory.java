package com.jparams.junit4.description;

import com.google.common.base.Joiner;
import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;

public class DescriptionFactory {
    private static final String DEFAULT_TEST_NAME_PATTERN = "[{index}] - {params}";

    private Class<?> testClass;

    public DescriptionFactory(Class<?> testClass) {
        this.testClass = testClass;
    }

    public Description createDescription(FrameworkMethod method, Object[][] data) {
        Description suite = Description.createSuiteDescription(method.getName(), method.getAnnotations());

        for (int i = 0; i < data.length; i++) {
            Name name = method.getAnnotation(Name.class);

            String pattern = name == null ? DEFAULT_TEST_NAME_PATTERN : name.value();
            String testName = createTestName(pattern, data[i], i, method.getName());

            suite.addChild(Description.createTestDescription(method.getName(), testName));
        }

        return suite;
    }

    private String createTestName(String pattern, Object[] row, int index, String method) {
        String testName = pattern;

        for (int i = 0; i < row.length; i++) {
            testName = testName.replace("{" + i + "}", String.valueOf(row[i]));
        }

        return testName
            .replace("{class}", testClass.getSimpleName())
            .replace("{method}", method)
            .replace("{index}", String.valueOf(index))
            .replace("{params}", Joiner.on(", ").join(row));
    }
}
