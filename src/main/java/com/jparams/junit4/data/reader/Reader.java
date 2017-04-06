package com.jparams.junit4.data.reader;

import java.lang.annotation.Annotation;

public interface Reader<T extends Annotation> {
    Object[][] readData(T annotation);
}
