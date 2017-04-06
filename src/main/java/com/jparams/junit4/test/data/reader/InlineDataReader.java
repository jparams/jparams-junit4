package com.jparams.junit4.test.data.reader;

import com.jparams.junit4.test.data.parser.DataParser;
import com.jparams.junit4.test.util.ObjectUtils;

public class InlineDataReader implements DataReader<String[]> {
    @Override
    public Object[][] readData(String[] source) {
        String lines = ObjectUtils.join(source, System.lineSeparator());
        return DataParser.parse(lines);
    }
}
