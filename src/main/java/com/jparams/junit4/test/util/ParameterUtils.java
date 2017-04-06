package com.jparams.junit4.test.util;

import com.jparams.junit4.test.data.reader.DataReader;
import com.jparams.junit4.test.data.reader.Reader;
import com.jparams.junit4.test.util.exception.InstanceCreationException;
import org.junit.runners.model.FrameworkMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class ParameterUtils {
    private ParameterUtils() {
    }

    public static boolean isParameterized(FrameworkMethod method) {
        for (Annotation annotation : method.getAnnotations()) {
            if (annotation.annotationType() == Reader.class || annotation.annotationType().isAnnotationPresent(Reader.class)) {
                return true;
            }
        }

        return false;
    }

    public static Object[][] getParameterizedData(FrameworkMethod method) {
        for (Annotation annotation : method.getAnnotations()) {
            Class<? extends Annotation> type = annotation.annotationType();

            if (type == Reader.class) {
                return read((Reader) annotation, null);
            }

            if (type.isAnnotationPresent(Reader.class)) {
                Reader reader = type.getAnnotation(Reader.class);
                Object source = getSource(annotation);
                return read(reader, source);
            }
        }

        return null;
    }

    private static Object[][] read(Reader reader, Object source) {
        try {
            DataReader dataReader = reader.value().newInstance();
            return dataReader.readData(source);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new InstanceCreationException("Error instantiating data reader", e);
        }
    }

    private static Object getSource(Annotation annotation) {
        try {
            Method method = annotation.annotationType().getMethod("value");
            return method.invoke(annotation);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }
}
