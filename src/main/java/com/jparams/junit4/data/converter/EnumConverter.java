package com.jparams.junit4.data.converter;

import com.jparams.junit4.reflection.MethodParameter;

public class EnumConverter implements Converter {
    @Override
    public Object convert(Object data, MethodParameter methodParameter) {
        Class<? extends Enum> type = (Class<? extends Enum>) methodParameter.getType();
        return Enum.valueOf(type, data.toString());
    }
}
