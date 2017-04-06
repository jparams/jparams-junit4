package com.jparams.junit4.data.reader;

import com.jparams.junit4.data.DataMethod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class DataMethodReader implements Reader<DataMethod> {
    @Override
    public Object[][] readData(DataMethod annotation) {
        try {
            Method method = annotation.source().getDeclaredMethod(annotation.method());
            int modifiers = method.getModifiers();

            if (!Modifier.isPublic(modifiers) || !Modifier.isStatic(modifiers) || !method.getReturnType().equals(Object[][].class)) {
                throw new ReaderException("Method must have the signature public static Object[][]");
            }

            return (Object[][]) method.invoke(null);
        } catch (NoSuchMethodException e) {
            throw new ReaderException("Unable to find method with name " + annotation.method(), e);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new ReaderException("Error invoking data method", e);
        }
    }
}
