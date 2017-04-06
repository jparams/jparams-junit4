package com.jparams.junit4.test.data.reader;

import com.jparams.junit4.test.data.reader.exception.ReaderException;
import org.junit.Before;
import org.junit.Test;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.assertj.core.api.Assertions.assertThat;

public class FileDataReaderTest {
    private FileDataReader subject;

    @Before
    public void setUp() throws Exception {
        subject = new FileDataReader();
    }

    @Test
    public void readsDataFromClassPath() throws Exception {
        Object[][] output = subject.readData("classpath://data.csv");

        assertThat(output).hasSize(2);
        assertThat(output[0]).containsExactly("1", "2", "3", "4");
        assertThat(output[1]).containsExactly("5", "6", "7", "8");
    }

    @Test
    public void readsDataFromFile() throws Exception {
        String file = this.getClass().getClassLoader().getResource("data.csv").getFile();

        Object[][] output = subject.readData(file);

        assertThat(output).hasSize(2);
        assertThat(output[0]).containsExactly("1", "2", "3", "4");
        assertThat(output[1]).containsExactly("5", "6", "7", "8");
    }

    @Test
    public void throwsReaderExceptionWhenFileNotExistInClassPath() throws Exception {
        catchException(subject).readData("classpath://abcd.csv");
        assertThat(caughtException()).isInstanceOf(ReaderException.class)
            .hasMessage("Data file not found at path <abcd.csv>");
    }

    @Test
    public void throwsReaderExceptionWhenFileNotExistInFileSystem() throws Exception {
        catchException(subject).readData("/abcd.csv");
        assertThat(caughtException()).isInstanceOf(ReaderException.class)
            .hasMessage("Error reading data file");
    }
}
