package com.jparams.junit4.data.parser;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DataParserTest {
    @Test
    public void returnsAnArrayOfStrings() throws Exception {
        String[][] parse = DataParser.parse("'john', doe");

        assertThat(parse).hasSize(1);
        assertThat(parse[0]).containsExactly("john", "doe");
    }

    @Test
    public void acceptsNull() throws Exception {
        String[][] parse = DataParser.parse("'null', null");

        assertThat(parse).hasSize(1);
        assertThat(parse[0]).containsExactly("null", null);
    }

    @Test
    public void ignoresWhiteSpaceOutsideOfQuotes() throws Exception {
        String[][] parse = DataParser.parse("  'john'     ,           'doe'");

        assertThat(parse).hasSize(1);
        assertThat(parse[0]).containsExactly("john", "doe");
    }

    @Test
    public void retainsWhiteSpaceInsideOfQuote() throws Exception {
        String[][] parse = DataParser.parse("'  j ohn   ', '    '");

        assertThat(parse).hasSize(1);
        assertThat(parse[0]).containsExactly("  j ohn   ", "    ");
    }

    @Test
    public void ignoresWhiteSpaceWhenNoQuotes() throws Exception {
        String[][] parse = DataParser.parse("  jo hn     ,           doe");

        assertThat(parse).hasSize(1);
        assertThat(parse[0]).containsExactly("jo hn", "doe");
    }

    @Test
    public void escapesQuotes() throws Exception {
        String[][] parse = DataParser.parse("\\'john\\', \\'doe\\'");

        assertThat(parse).hasSize(1);
        assertThat(parse[0]).containsExactly("'john'", "'doe'");
    }

    @Test
    public void commasInsideQuotesIsString() throws Exception {
        String[][] parse = DataParser.parse("'1,2,3,4', 5");

        assertThat(parse).hasSize(1);
        assertThat(parse[0]).containsExactly("1,2,3,4", "5");
    }

    @Test
    public void supportsMultiLineString() throws Exception {
        String[][] parse = DataParser.parse("'john', doe" + System.lineSeparator() + "'jane', doe");

        assertThat(parse).hasSize(2);
        assertThat(parse[0]).containsExactly("john", "doe");
        assertThat(parse[1]).containsExactly("jane", "doe");
    }

    @Test
    public void quoteOpenedButNotClosedAutoClosesAtEndOfLine() throws Exception {
        String[][] parse = DataParser.parse("1, 2, 3, '4, 5, 6" + System.lineSeparator() + " 7, 8, 9");

        assertThat(parse).hasSize(2);
        assertThat(parse[0]).containsExactly("1", "2", "3", "4, 5, 6");
        assertThat(parse[1]).containsExactly("7", "8", "9");
    }
}
