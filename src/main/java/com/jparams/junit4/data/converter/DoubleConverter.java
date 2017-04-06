package com.jparams.junit4.data.converter;

import com.jparams.junit4.reflection.MethodParameter;

public class DoubleConverter implements Converter {
    @Override
    public Object convert(Object data, MethodParameter methodParameter) {
        return Double.valueOf(data.toString());
    }
}
