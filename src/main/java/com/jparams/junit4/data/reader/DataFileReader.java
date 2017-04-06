package com.jparams.junit4.data.reader;

import com.jparams.junit4.data.DataFile;
import com.jparams.junit4.data.parser.DataParser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class DataFileReader implements Reader<DataFile> {
    @Override
    public Object[][] readData(DataFile annotation) {
        String path = annotation.path();

        switch (annotation.location()) {
            case FILE:
                return readDataFromFileSystem(path);
            case CLASSPATH:
                return readDataFromClassPath(path);
            default:
                throw new ReaderException("Unknown location type " + annotation.location());
        }
    }

    private Object[][] readDataFromClassPath(String classPath) {
        URL url = this.getClass().getClassLoader().getResource(classPath);

        if (url == null) {
            String message = String.format("Data file not found at path <%s>", classPath);
            throw new ReaderException(message);
        }

        String data = consumeFully(url);

        return DataParser.parse(data);
    }

    private Object[][] readDataFromFileSystem(String filePath) {
        File file = new File(filePath);
        try {
            URL url = file.toURI().toURL();
            String data = consumeFully(url);
            return DataParser.parse(data);
        } catch (MalformedURLException e) {
            String message = String.format("Error reading data file at path <%s>", filePath);
            throw new ReaderException(message, e);
        }
    }

    private String consumeFully(URL url) {
        try (InputStream inputStream = url.openStream()) {
            Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        } catch (IOException e) {
            throw new ReaderException("Error reading data file", e);
        }
    }
}
