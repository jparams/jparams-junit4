package com.jparams.junit4.test.data.reader;

import com.jparams.junit4.test.data.parser.DataParser;
import com.jparams.junit4.test.data.reader.exception.ReaderException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class FileDataReader implements DataReader<String> {
    public static final String CLASSPATH_PREFIX = "classpath://";

    @Override
    public Object[][] readData(String source) {
        if (source.startsWith(CLASSPATH_PREFIX)) {
            return readDataFromClassPath(source.substring(CLASSPATH_PREFIX.length()));
        }

        return readDataFromFileSystem(source);
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
            return scanner.hasNext() ? scanner.next() : null;
        } catch (IOException e) {
            throw new ReaderException("Error reading data file", e);
        }
    }
}
