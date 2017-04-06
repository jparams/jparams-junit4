package com.jparams.junit4.data.converter;

import com.jparams.junit4.reflection.MethodParameter;

public interface Converter {
    Object convert(Object data, MethodParameter methodParameter);
}
