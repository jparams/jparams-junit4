package com.jparams.junit4.test.data.reader;

public interface DataReader<T> {
    Object[][] readData(T source);
}
