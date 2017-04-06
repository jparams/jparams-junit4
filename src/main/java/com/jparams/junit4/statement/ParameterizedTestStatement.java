package com.jparams.junit4.statement;

import com.jparams.junit4.ParameterizedMethod;
import com.jparams.junit4.data.converter.BigDecimalConverter;
import com.jparams.junit4.data.converter.BooleanConverter;
import com.jparams.junit4.data.converter.ClassConverter;
import com.jparams.junit4.data.converter.Convert;
import com.jparams.junit4.data.converter.Converter;
import com.jparams.junit4.data.converter.DateConverter;
import com.jparams.junit4.data.converter.DoubleConverter;
import com.jparams.junit4.data.converter.EnumConverter;
import com.jparams.junit4.data.converter.FloatConverter;
import com.jparams.junit4.data.converter.IntegerConverter;
import com.jparams.junit4.data.converter.LongConverter;
import com.jparams.junit4.data.converter.StringConverter;
import com.jparams.junit4.reflection.MethodParameter;
import com.jparams.junit4.reflection.Reflection;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParameterizedTestStatement extends Statement {
    private static final Map<Class<?>, Converter> converters = getConverters();

    private final ParameterizedMethod parameterizedMethod;
    private final Object test;

    public ParameterizedTestStatement(ParameterizedMethod parameterizedMethod, Object test) {
        this.parameterizedMethod = parameterizedMethod;
        this.test = test;
    }

    @Override
    public void evaluate() throws Throwable {
        FrameworkMethod method = parameterizedMethod.getMethod();
        Object[] parameters = parameterizedMethod.getParameters();
        MethodParameter[] methodParameters = Reflection.getMethodParameters(method.getMethod());
        List<Object> methodArguments = new ArrayList<>();

        for (int i = 0; i < methodParameters.length; i++) {
            Object parameterData = parameters != null && i < parameters.length ? parameters[i] : null;
            Object methodArgument = getMethodArgument(parameterData, methodParameters[i]);
            methodArguments.add(methodArgument);
        }

        method.invokeExplosively(test, methodArguments.toArray());
    }

    private Object getMethodArgument(Object parameter, MethodParameter methodParameter) {
        if (parameter == null) {
            return null;
        }

        if (methodParameter.isAnnotationPresent(Convert.class)) {
            Converter converter = Reflection.createInstance(methodParameter.getAnnotation(Convert.class).value());
            return converter.convert(parameter.toString(), methodParameter);
        }

        if (methodParameter.getType().isAssignableFrom(parameter.getClass())) {
            return parameter;
        }

        return getConverter(methodParameter).convert(parameter, methodParameter);
    }

    private Converter getConverter(MethodParameter methodParameter) {
        Class<?> type = methodParameter.getType();

        if (type.isEnum()) {
            return new EnumConverter();
        }

        if (converters.containsKey(type)) {
            return converters.get(type);
        }

        throw new NoConverterException("No converter found for argument type " + type);
    }

    private static Map<Class<?>, Converter> getConverters() {
        Map<Class<?>, Converter> converters = new HashMap<>();
        converters.put(BigDecimal.class, new BigDecimalConverter());
        converters.put(Boolean.class, new BooleanConverter());
        converters.put(boolean.class, new BooleanConverter());
        converters.put(Class.class, new ClassConverter());
        converters.put(Date.class, new DateConverter());
        converters.put(Double.class, new DoubleConverter());
        converters.put(double.class, new DoubleConverter());
        converters.put(Float.class, new FloatConverter());
        converters.put(float.class, new FloatConverter());
        converters.put(Integer.class, new IntegerConverter());
        converters.put(int.class, new IntegerConverter());
        converters.put(Long.class, new LongConverter());
        converters.put(long.class, new LongConverter());
        converters.put(String.class, new StringConverter());
        return converters;
    }
}
