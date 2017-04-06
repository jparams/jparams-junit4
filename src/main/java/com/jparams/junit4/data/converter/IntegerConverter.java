package com.jparams.junit4.data.converter;

import com.jparams.junit4.reflection.MethodParameter;

public class IntegerConverter implements Converter {
    @Override
    public Object convert(Object data, MethodParameter methodParameter) {
        return Integer.valueOf(data.toString());
    }
}
