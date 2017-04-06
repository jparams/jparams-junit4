package com.jparams.junit4.data.converter;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class DateConverterTest {
    private DateConverter subject;

    @Before
    public void setUp() throws Exception {
        subject = new DateConverter();
    }

    @Test
    public void convertsDateFromTimeStamp() throws Exception {
        Date expected = new Date();

        Date actual = (Date) subject.convert(String.valueOf(expected.getTime()), null);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void mapsIso8601DateString() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String date = sdf.format(new Date());

        Date actual = (Date) subject.convert(date, null);

        assertThat(actual).isEqualTo(sdf.parse(date));
    }

    @Test(expected = ConverterException.class)
    public void throwsExceptionOnUnknownDateFormat() throws Exception {
        subject.convert("abc", null);
    }
}
