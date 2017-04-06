package com.jparams.junit4.data.reader;

import com.google.common.base.Joiner;
import com.jparams.junit4.data.Data;
import com.jparams.junit4.data.parser.DataParser;

public class DataReader implements Reader<Data> {
    @Override
    public Object[][] readData(Data annotation) {
        String lines = Joiner.on(System.lineSeparator()).join(annotation.value());
        return DataParser.parse(lines);
    }
}
