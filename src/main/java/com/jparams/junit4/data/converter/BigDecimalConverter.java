package com.jparams.junit4.data.converter;

import com.jparams.junit4.reflection.MethodParameter;

import java.math.BigDecimal;

public class BigDecimalConverter implements Converter {
    @Override
    public Object convert(Object data, MethodParameter methodParameter) {
        return new BigDecimal(data.toString());
    }
}
