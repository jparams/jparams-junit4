package com.jparams.junit4.test.data;

import com.jparams.junit4.test.data.reader.InlineDataReader;
import com.jparams.junit4.test.data.reader.Reader;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Reader(InlineDataReader.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DataFile {
    String value();
}
