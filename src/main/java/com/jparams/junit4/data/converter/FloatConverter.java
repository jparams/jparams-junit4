package com.jparams.junit4.data.converter;

import com.jparams.junit4.reflection.MethodParameter;

public class FloatConverter implements Converter {
    @Override
    public Object convert(Object data, MethodParameter methodParameter) {
        return Float.valueOf(data.toString());
    }
}
