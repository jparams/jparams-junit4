package com.jparams.junit4.test.data.converter;

public interface Conveter<T> {
    T convert(String data);
}
