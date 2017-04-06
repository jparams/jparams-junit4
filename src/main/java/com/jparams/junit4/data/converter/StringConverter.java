package com.jparams.junit4.data.converter;

import com.jparams.junit4.reflection.MethodParameter;

public class StringConverter implements Converter {
    @Override
    public Object convert(Object data, MethodParameter methodParameter) {
        return data.toString();
    }
}
