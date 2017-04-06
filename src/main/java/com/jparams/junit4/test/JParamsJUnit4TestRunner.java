package com.jparams.junit4.test;

import com.jparams.junit4.test.description.DescriptionFactory;
import com.jparams.junit4.test.util.ParameterUtils;
import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JParamsJUnit4TestRunner extends BlockJUnit4ClassRunner {
    private final Map<FrameworkMethod, ParameterizedMethod> parameterizedMethods = new HashMap<>();
    private final DescriptionFactory descriptionFactory;
    private Description description;
    private Filter filter;

    public JParamsJUnit4TestRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
        this.descriptionFactory = new DescriptionFactory(testClass);
    }

    @Override
    public void filter(Filter filter) throws NoTestsRemainException {
        this.filter = filter;
        super.filter(filter);
    }

    @Override
    public Description getDescription() {
        if (description == null) {
            description = Description.createSuiteDescription(getName(), getRunnerAnnotations());

            for (FrameworkMethod child : getChildren()) {
                if (shouldRun(child)) {
                    description.addChild(createDescription(child));
                }
            }
        }

        return description;
    }

    @Override
    protected void validatePublicVoidNoArgMethods(Class<? extends Annotation> annotation, boolean isStatic, List<Throwable> errors) {
        List<FrameworkMethod> methods = getTestClass().getAnnotatedMethods(annotation);

        for (FrameworkMethod method : methods) {
            method.validatePublicVoid(isStatic, errors);
        }
    }

    @Override
    protected void runChild(FrameworkMethod method, RunNotifier notifier) {
        Description description = describeChild(method);

        if (isIgnored(method)) {
            notifier.fireTestIgnored(description);
        } else if (parameterizedMethods.containsKey(method)) {
            runParameterizedMethod(parameterizedMethods.get(method), notifier);
        } else {
            runLeaf(method, notifier, description);
        }
    }

    @Override
    protected Statement methodInvoker(FrameworkMethod method, Object test) {
        if (parameterizedMethods.containsKey(method)) {
            return new ParameterizedTestStatement(parameterizedMethods.get(method), test);
        }

        return super.methodInvoker(method, test);
    }

    private void runLeaf(FrameworkMethod method, RunNotifier notifier, Description description) {
        runLeaf(methodBlock(method), description, notifier);
    }

    private void runParameterizedMethod(ParameterizedMethod parameterizedMethod, RunNotifier notifier) {
        Description description = parameterizedMethod.getDescription();

        notifier.fireTestStarted(description);

        try {
            for (Description child : description.getChildren()) {
                runLeaf(parameterizedMethod.getMethod(), notifier, child);
            }
        } finally {
            notifier.fireTestFinished(description);
        }
    }

    private boolean shouldRun(FrameworkMethod method) {
        return filter == null || filter.shouldRun(describeChild(method));
    }

    private Description createDescription(FrameworkMethod method) {
        if (ParameterUtils.isParameterized(method)) {
            Object[][] data = ParameterUtils.getParameterizedData(method);

            Description description = descriptionFactory.createDescription(method, data);

            ParameterizedMethod parameterizedMethod = new ParameterizedMethod(method, description, data);
            parameterizedMethods.put(method, parameterizedMethod);

            return description;
        }

        return describeChild(method);
    }
}
