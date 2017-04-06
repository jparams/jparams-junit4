package com.jparams.junit4.reflection;

import java.lang.annotation.Annotation;

public class MethodParameter {
    private final Class<?> type;
    private final Annotation[] annotations;

    public MethodParameter(Class<?> type, Annotation[] annotations) {
        this.type = type;
        this.annotations = annotations;
    }

    public Class<?> getType() {
        return type;
    }

    public <T extends Annotation> boolean isAnnotationPresent(Class<T> type) {
        return getAnnotation(type) != null;
    }

    public <T extends Annotation> T getAnnotation(Class<T> type) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(type)) {
                return (T) annotation;
            }
        }

        return null;
    }
}
