package com.jparams.junit4.data.parser;

import java.util.ArrayList;
import java.util.List;

public final class DataParser {
    private static final char QUOTE_CHAR = '\'';
    private static final char SEPARATOR_CHAR = ',';
    private static final char ESCAPE_CHAR = '\\';

    private static final String QUOTE = String.valueOf(QUOTE_CHAR);
    private static final String ESCAPED_QUOTE = ESCAPE_CHAR + QUOTE;
    private static final String NULL_VALUE = "null";
    private static final String ESCAPES = "\\\\";

    private DataParser() {
    }

    public static String[][] parse(String input) {
        String[] lines = input.split(System.lineSeparator());
        String[][] output = new String[lines.length][];

        for (int i = 0; i < lines.length; i++) {
            output[i] = parseLine(lines[i]);
        }

        return output;
    }

    private static String[] parseLine(String line) {
        List<String> values = new ArrayList<>();
        StringBuilder builder = new StringBuilder();

        boolean escaped = false;
        boolean insideQuotes = false;

        for (char character : line.toCharArray()) {
            if (character == ESCAPE_CHAR) {
                escaped = true;
            }

            if (!escaped && character == QUOTE_CHAR) {
                insideQuotes = !insideQuotes;
            }

            if (!insideQuotes && character == SEPARATOR_CHAR) {
                values.add(formatValue(builder.toString()));
                builder = new StringBuilder();
            } else {
                builder.append(character);
            }

            escaped = false;
        }

        values.add(formatValue(builder.toString()));
        return values.toArray(new String[values.size()]);
    }

    private static String formatValue(String value) {
        String formatted = value.trim();

        if (formatted.equals(NULL_VALUE)) {
            return null;
        }

        if (formatted.startsWith(QUOTE)) {
            formatted = formatted.substring(1);
        }

        if (formatted.endsWith(QUOTE) && !formatted.endsWith(ESCAPED_QUOTE)) {
            formatted = formatted.substring(0, formatted.length() - 1);
        }

        return formatted.replaceAll(ESCAPES, "");
    }
}
