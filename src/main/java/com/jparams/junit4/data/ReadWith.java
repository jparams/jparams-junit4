package com.jparams.junit4.data;

import com.jparams.junit4.data.reader.Reader;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface ReadWith {
    Class<? extends Reader> value();
}
