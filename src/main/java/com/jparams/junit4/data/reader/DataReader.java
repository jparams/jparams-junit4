package com.jparams.junit4.data.reader;

import com.jparams.junit4.data.Data;
import com.jparams.junit4.data.parser.DataParser;
import com.jparams.junit4.util.Joiner;

public class DataReader implements Reader<Data> {
    @Override
    public Object[][] readData(Data annotation) {
        String lines = Joiner.join(annotation.value(), System.lineSeparator());
        return DataParser.parse(lines);
    }
}
