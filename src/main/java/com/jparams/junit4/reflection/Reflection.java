package com.jparams.junit4.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class Reflection {
    public static MethodParameter[] getMethodParameters(Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();

        MethodParameter[] methodParameters = new MethodParameter[parameterTypes.length];

        for (int i = 0; i < parameterTypes.length; i++) {
            methodParameters[i] = new MethodParameter(parameterTypes[i], parameterAnnotations[i]);
        }

        return methodParameters;
    }

    public static <T> T createInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new InstanceCreationException("Error instantiating class " + clazz.getSimpleName(), e);
        }
    }
}
