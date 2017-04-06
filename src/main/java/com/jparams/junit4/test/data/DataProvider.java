package com.jparams.junit4.test.data;

import com.jparams.junit4.test.data.provider.Provider;
import com.jparams.junit4.test.data.reader.ProvidedDataReader;
import com.jparams.junit4.test.data.reader.Reader;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Reader(ProvidedDataReader.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DataProvider {
    Class<? extends Provider> value();
}
