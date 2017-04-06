package com.jparams.junit4.util;

import com.jparams.junit4.data.ReadWith;
import com.jparams.junit4.reflection.Reflection;
import org.junit.runners.model.FrameworkMethod;

import java.lang.annotation.Annotation;

public final class Parameters {
    private Parameters() {
    }

    public static boolean isParameterized(FrameworkMethod method) {
        for (Annotation annotation : method.getAnnotations()) {
            if (annotation.annotationType().isAnnotationPresent(ReadWith.class)) {
                return true;
            }
        }

        return false;
    }

    public static Object[][] getParameterizedData(FrameworkMethod method) {
        for (Annotation annotation : method.getAnnotations()) {
            Class<? extends Annotation> type = annotation.annotationType();

            if (type.isAnnotationPresent(ReadWith.class)) {
                ReadWith readWith = type.getAnnotation(ReadWith.class);
                return read(readWith, annotation);
            }
        }

        return null;
    }

    private static Object[][] read(ReadWith readWith, Annotation annotation) {
        com.jparams.junit4.data.reader.Reader reader = Reflection.createInstance(readWith.value());
        return reader.readData(annotation);
    }
}
