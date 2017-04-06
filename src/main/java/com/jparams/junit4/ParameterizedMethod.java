package com.jparams.junit4;

import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;

public class ParameterizedMethod {
    private final FrameworkMethod method;
    private final Description description;
    private final Object[][] data;
    private int index = 0;

    public ParameterizedMethod(FrameworkMethod method, Description description, Object[][] data) {
        this.method = method;
        this.description = description;
        this.data = data;
    }

    public FrameworkMethod getMethod() {
        return method;
    }

    public Description getDescription() {
        return description;
    }

    public Object[] getParameters() {
        if (index < data.length) {
            return data[index++];
        }

        return null;
    }
}
