package com.jparams.junit4.data.reader;

import com.jparams.junit4.data.DataProvider;
import com.jparams.junit4.data.provider.Provider;

public class DataProviderReader implements Reader<DataProvider> {
    @Override
    public Object[][] readData(DataProvider annotation) {
        try {
            Provider provider = annotation.value().newInstance();
            return provider.provide();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ReaderException("Failed to Instantiate provider", e);
        }
    }
}
