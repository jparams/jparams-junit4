package com.jparams.junit4.data;

import com.jparams.junit4.data.reader.DataReader;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ReadWith(DataReader.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DataFile {
    String path();

    Location location();

    enum Location {
        FILE, CLASSPATH
    }
}
