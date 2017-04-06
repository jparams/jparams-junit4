package com.jparams.junit4.test.data.reader;

import com.jparams.junit4.test.data.provider.Provider;
import com.jparams.junit4.test.data.reader.exception.ReaderException;

public class ProvidedDataReader implements DataReader<Class<? extends Provider>> {
    @Override
    public Object[][] readData(Class<? extends Provider> source) {
        try {
            Provider provider = source.newInstance();
            return provider.provide();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ReaderException("Failed to Instantiate provider", e);
        }
    }
}
