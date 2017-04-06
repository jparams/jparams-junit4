package com.jparams.junit4.data.converter;

import com.jparams.junit4.reflection.MethodParameter;

public class ClassConverter implements Converter {
    @Override
    public Object convert(Object data, MethodParameter methodParameter) {
        try {
            return Class.forName(data.toString());
        } catch (ClassNotFoundException e) {
            throw new ConverterException("Class not found", e);
        }
    }
}
