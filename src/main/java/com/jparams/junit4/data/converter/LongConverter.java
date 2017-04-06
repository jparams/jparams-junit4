package com.jparams.junit4.data.converter;

import com.jparams.junit4.reflection.MethodParameter;

public class LongConverter implements Converter {
    @Override
    public Object convert(Object data, MethodParameter methodParameter) {
        return Long.valueOf(data.toString());
    }
}
