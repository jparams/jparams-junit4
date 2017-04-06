package com.jparams.junit4.data.converter;

import com.jparams.junit4.data.ReadWith;
import com.jparams.junit4.data.reader.DataReader;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ReadWith(value = DataReader.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Convert {
    Class<? extends Converter> value();
}
