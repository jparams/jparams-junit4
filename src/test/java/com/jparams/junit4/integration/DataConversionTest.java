package com.jparams.junit4.integration;

import com.jparams.junit4.JParamsTestRunner;
import com.jparams.junit4.data.Data;
import com.jparams.junit4.data.converter.Convert;
import com.jparams.junit4.data.converter.Converter;
import com.jparams.junit4.reflection.MethodParameter;
import com.jparams.junit4.statement.NoConverterException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

@Ignore
@RunWith(JParamsTestRunner.class)
public class DataConversionTest {
    @Data({
        "'John,Doe',   25",
        "'Susan,Smith', 22"
    })
    @Test
    public void customConverter(@Convert(NameConverter.class) Name name, int age) throws Exception {
        System.out.println("Name: " + name.getFirstName() + " " + name.getLastName() + ", Age: " + age);
    }

    public static class Name {
        private final String firstName;
        private final String lastName;

        public Name(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }
    }

    public static class NameConverter implements Converter {
        @Override
        public Name convert(Object data, MethodParameter methodParameter) {
            String[] parts = data.toString().split(",");
            return new Name(parts[0], parts[1]);
        }
    }

    @Data("1")
    @Test
    public void convertsStringToInt(int num) throws Exception {
        assertThat(num).isEqualTo(1);
    }

    @Data("1")
    @Test
    public void convertsStringToInteger(Integer num) throws Exception {
        assertThat(num).isEqualTo(1);
    }

    @Data("false")
    @Test
    public void convertsStringToBoolean(boolean bool) throws Exception {
        assertThat(bool).isFalse();
    }

    @Data("true")
    @Test
    public void convertsStringToInteger(Boolean bool) throws Exception {
        assertThat(bool).isTrue();
    }

    @Data("1, 1")
    @Test
    public void customConverter(@Convert(AlwaysReturn99Converter.class) Integer num1, @Convert(AlwaysReturn99Converter.class) Integer num2) throws Exception {
        assertThat(num1).isEqualTo(99);
        assertThat(num2).isEqualTo(99);
    }

    @Data("1")
    @Test(expected = NoConverterException.class)
    public void noConverterFound(InputStream num1) throws Exception {

    }

    @Data("null")
    @Test
    public void converterNotCalledForNull(@Convert(AlwaysFailConverter.class) InputStream num1) throws Exception {

    }

    public static class AlwaysReturn99Converter implements Converter {
        @Override
        public Integer convert(Object data, MethodParameter methodParameter) {
            return 99;
        }
    }

    public static class AlwaysFailConverter implements Converter {
        @Override
        public Integer convert(Object data, MethodParameter methodParameter) {
            throw new RuntimeException("should not be called");
        }
    }
}
