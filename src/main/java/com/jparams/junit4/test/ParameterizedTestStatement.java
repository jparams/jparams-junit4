package com.jparams.junit4.test;

import org.junit.runners.model.Statement;

class ParameterizedTestStatement extends Statement {
    private final ParameterizedMethod parameterizedMethod;
    private final Object test;

    public ParameterizedTestStatement(ParameterizedMethod parameterizedMethod, Object test) {
        this.parameterizedMethod = parameterizedMethod;
        this.test = test;
    }

    @Override
    public void evaluate() throws Throwable {

    }
}
