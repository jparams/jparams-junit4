package com.jparams.junit4.data.reader;

public class ReaderException extends RuntimeException {
    public ReaderException(String message) {
        super(message);
    }

    public ReaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
