package com.jparams.junit4.data.reader;

import com.jparams.junit4.data.DataFile;
import org.junit.Before;
import org.junit.Test;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DataFileReaderTest {
    private DataFileReader subject;

    @Before
    public void setUp() throws Exception {
        subject = new DataFileReader();
    }

    @Test
    public void readsDataFromClassPath() throws Exception {
        DataFile annotation = createAnnotation("data.csv", DataFile.Location.CLASSPATH);
        Object[][] output = subject.readData(annotation);

        assertThat(output).hasSize(2);
        assertThat(output[0]).containsExactly("1", "2", "3", "4");
        assertThat(output[1]).containsExactly("5", "6", "7", "8");
    }

    @Test
    public void readsDataFromFile() throws Exception {
        String file = this.getClass().getClassLoader().getResource("data.csv").getFile();
        DataFile annotation = createAnnotation(file, DataFile.Location.FILE);
        Object[][] output = subject.readData(annotation);

        assertThat(output).hasSize(2);
        assertThat(output[0]).containsExactly("1", "2", "3", "4");
        assertThat(output[1]).containsExactly("5", "6", "7", "8");
    }

    @Test
    public void throwsReaderExceptionWhenFileNotExistInClassPath() throws Exception {
        DataFile annotation = createAnnotation("abcd.csv", DataFile.Location.CLASSPATH);

        catchException(subject).readData(annotation);

        assertThat(caughtException()).isInstanceOf(ReaderException.class)
            .hasMessage("Data file not found at path <abcd.csv>");
    }

    @Test
    public void throwsReaderExceptionWhenFileNotExistInFileSystem() throws Exception {
        DataFile annotation = createAnnotation("abcd.csv", DataFile.Location.FILE);

        catchException(subject).readData(annotation);

        assertThat(caughtException()).isInstanceOf(ReaderException.class)
            .hasMessage("Error reading data file");
    }

    private static DataFile createAnnotation(String file, DataFile.Location location) {
        DataFile mockDataFile = mock(DataFile.class);
        when(mockDataFile.path()).thenReturn(file);
        when(mockDataFile.location()).thenReturn(location);
        return mockDataFile;
    }
}
