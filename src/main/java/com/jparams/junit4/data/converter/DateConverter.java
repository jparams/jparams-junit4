package com.jparams.junit4.data.converter;

import com.jparams.junit4.reflection.MethodParameter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class DateConverter implements Converter {
    private static final Pattern TIME_STAMP_PATTERN = Pattern.compile("^[0-9]*$");
    private static final String ISO_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    @Override
    public Object convert(Object data, MethodParameter methodParameter) {
        String input = data.toString();

        if (TIME_STAMP_PATTERN.matcher(input).matches()) {
            return new Date(Long.valueOf(input));
        }

        try {
            return new SimpleDateFormat(ISO_DATE_PATTERN).parse(input);
        } catch (ParseException e) {
            throw new ConverterException("Could not convert date. Make sure input is a valid ISO 8601 date", e);
        }
    }
}
