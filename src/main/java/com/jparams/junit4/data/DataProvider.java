package com.jparams.junit4.data;

import com.jparams.junit4.data.provider.Provider;
import com.jparams.junit4.data.reader.DataProviderReader;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ReadWith(DataProviderReader.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DataProvider {
    Class<? extends Provider> value();
}
