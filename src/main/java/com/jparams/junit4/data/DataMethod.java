package com.jparams.junit4.data;

import com.jparams.junit4.data.reader.DataMethodReader;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ReadWith(DataMethodReader.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DataMethod {
    Class<?> source();

    String method();
}
