package com.jparams.junit4.statement;

import com.jparams.junit4.ParameterizedMethod;
import com.jparams.junit4.data.converter.Convert;
import com.jparams.junit4.data.converter.Converter;
import com.jparams.junit4.reflection.MethodParameter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.model.FrameworkMethod;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class ParameterizedTestStatementTest {
    private DummyClass dummyObject;

    @Before
    public void setUp() throws Exception {
        dummyObject = new DummyClass();
    }

    @Test
    public void convertToInteger() throws Throwable {
        new ParameterizedTestStatement(getMethod(Integer.class, "123"), dummyObject).evaluate();

        assertThat(dummyObject.type).isEqualTo(Integer.class);
        assertThat(dummyObject.value).isEqualTo(123);
    }

    @Test
    public void convertToInt() throws Throwable {
        new ParameterizedTestStatement(getMethod(int.class, "123"), dummyObject).evaluate();

        assertThat(dummyObject.type).isEqualTo(int.class);
        assertThat(dummyObject.value).isEqualTo(123);
    }

    @Test
    public void convertToBoolean() throws Throwable {
        new ParameterizedTestStatement(getMethod(Boolean.class, "true"), dummyObject).evaluate();

        assertThat(dummyObject.type).isEqualTo(Boolean.class);
        assertThat(dummyObject.value).isEqualTo(true);
    }

    @Test
    public void convertToBooleanPrimitive() throws Throwable {
        new ParameterizedTestStatement(getMethod(boolean.class, "true"), dummyObject).evaluate();

        assertThat(dummyObject.type).isEqualTo(boolean.class);
        assertThat(dummyObject.value).isEqualTo(true);
    }

    @Test
    public void convertToBigDecimal() throws Throwable {
        new ParameterizedTestStatement(getMethod(BigDecimal.class, "10"), dummyObject).evaluate();

        assertThat(dummyObject.type).isEqualTo(BigDecimal.class);
        assertThat(dummyObject.value).isEqualTo(BigDecimal.TEN);
    }

    @Test
    public void convertToDate() throws Throwable {
        Date date = new Date();
        new ParameterizedTestStatement(getMethod(Date.class, String.valueOf(date.getTime())), dummyObject).evaluate();

        assertThat(dummyObject.type).isEqualTo(Date.class);
        assertThat(dummyObject.value).isEqualTo(date);
    }

    @Test
    public void convertToDouble() throws Throwable {
        new ParameterizedTestStatement(getMethod(Double.class, "10"), dummyObject).evaluate();

        assertThat(dummyObject.type).isEqualTo(Double.class);
        assertThat(dummyObject.value).isEqualTo(10D);
    }

    @Test
    public void convertToDoublePrimitive() throws Throwable {
        new ParameterizedTestStatement(getMethod(double.class, "10"), dummyObject).evaluate();

        assertThat(dummyObject.type).isEqualTo(double.class);
        assertThat(dummyObject.value).isEqualTo(10d);
    }

    @Test
    public void convertToFloat() throws Throwable {
        new ParameterizedTestStatement(getMethod(Float.class, "10"), dummyObject).evaluate();

        assertThat(dummyObject.type).isEqualTo(Float.class);
        assertThat(dummyObject.value).isEqualTo(10F);
    }

    @Test
    public void convertToFloatPrimitive() throws Throwable {
        new ParameterizedTestStatement(getMethod(float.class, "10"), dummyObject).evaluate();

        assertThat(dummyObject.type).isEqualTo(float.class);
        assertThat(dummyObject.value).isEqualTo(10f);
    }

    @Test
    public void convertToLong() throws Throwable {
        new ParameterizedTestStatement(getMethod(Long.class, "10"), dummyObject).evaluate();

        assertThat(dummyObject.type).isEqualTo(Long.class);
        assertThat(dummyObject.value).isEqualTo(10L);
    }

    @Test
    public void convertToLongPrimitive() throws Throwable {
        new ParameterizedTestStatement(getMethod(long.class, "10"), dummyObject).evaluate();

        assertThat(dummyObject.type).isEqualTo(long.class);
        assertThat(dummyObject.value).isEqualTo(10l);
    }

    @Test
    public void convertToClass() throws Throwable {
        new ParameterizedTestStatement(getMethod(Class.class, String.class.getName()), dummyObject).evaluate();

        assertThat(dummyObject.type).isEqualTo(Class.class);
        assertThat(dummyObject.value).isEqualTo(String.class);
    }

    @Test
    public void convertToEnum() throws Throwable {
        new ParameterizedTestStatement(getMethod(Colour.class, "RED"), dummyObject).evaluate();

        assertThat(dummyObject.type).isEqualTo(Colour.class);
        assertThat(dummyObject.value).isEqualTo(Colour.RED);
    }

    @Test
    public void convertToString() throws Throwable {
        new ParameterizedTestStatement(getMethod(String.class, 123), dummyObject).evaluate();

        assertThat(dummyObject.type).isEqualTo(String.class);
        assertThat(dummyObject.value).isEqualTo("123");
    }

    @Test
    public void convertUsingConverter() throws Throwable {
        new ParameterizedTestStatement(getMethod(ReallyRandom.class, "random"), dummyObject).evaluate();

        assertThat(dummyObject.type).isEqualTo(ReallyRandom.class);
    }

    @Test(expected = NoConverterException.class)
    public void throwsNoConverterExceptionOnUnknownType() throws Throwable {
        new ParameterizedTestStatement(getMethod(InputStream.class, "str"), dummyObject).evaluate();
    }

    @Test
    public void assignsToType() throws Throwable {
        Random data = new Random();
        new ParameterizedTestStatement(getMethod(Random.class, data), dummyObject).evaluate();

        assertThat(dummyObject.type).isEqualTo(Random.class);
        assertThat(dummyObject.value).isSameAs(data);
    }

    @Test
    public void assignsToTypeIfAssignable() throws Throwable {
        ReallyRandom data = new ReallyRandom();
        new ParameterizedTestStatement(getMethod(Random.class, data), dummyObject).evaluate();

        assertThat(dummyObject.type).isEqualTo(ReallyRandom.class);
        assertThat(dummyObject.value).isSameAs(data);
    }

    @Test
    public void callMethodWithOutDataDefaultsToNull() throws Throwable {
        FrameworkMethod frameworkMethod = getFrameworkMethod(String.class);
        ParameterizedMethod parameterizedMethod = new ParameterizedMethod(frameworkMethod, null, new Object[][] {});

        new ParameterizedTestStatement(parameterizedMethod, dummyObject).evaluate();

        assertThat(dummyObject.type).isEqualTo(String.class);
        assertThat(dummyObject.value).isNull();
    }

    private static ParameterizedMethod getMethod(Class<?> type, Object data) throws NoSuchMethodException {
        FrameworkMethod frameworkMethod = getFrameworkMethod(type);
        return new ParameterizedMethod(frameworkMethod, null, new Object[][] {{data}});
    }

    private static FrameworkMethod getFrameworkMethod(Class<?> type) throws NoSuchMethodException {
        Method method = DummyClass.class.getDeclaredMethod("method", type);
        return new FrameworkMethod(method);
    }

    public static class DummyClass {
        public Class<?> type;
        public Object value;

        public void method(Integer value) {
            this.value = value;
            this.type = Integer.class;
        }

        public void method(int value) {
            this.value = value;
            this.type = int.class;
        }

        public void method(Boolean value) {
            this.value = value;
            this.type = Boolean.class;
        }

        public void method(boolean value) {
            this.value = value;
            this.type = boolean.class;
        }

        public void method(BigDecimal value) {
            this.value = value;
            this.type = BigDecimal.class;
        }

        public void method(Date value) {
            this.value = value;
            this.type = Date.class;
        }

        public void method(Double value) {
            this.value = value;
            this.type = Double.class;
        }

        public void method(double value) {
            this.value = value;
            this.type = double.class;
        }

        public void method(Float value) {
            this.value = value;
            this.type = Float.class;
        }

        public void method(float value) {
            this.value = value;
            this.type = float.class;
        }

        public void method(Long value) {
            this.value = value;
            this.type = Long.class;
        }

        public void method(long value) {
            this.value = value;
            this.type = long.class;
        }

        public void method(Class<?> value) {
            this.value = value;
            this.type = Class.class;
        }

        public void method(Colour value) {
            this.value = value;
            this.type = Colour.class;
        }

        public void method(InputStream value) {
            fail("this method should not be called");
        }

        public void method(String value) {
            this.value = value;
            this.type = String.class;
        }

        public void method() {
            this.value = value;
            this.type = Colour.class;
        }

        public void method(Random value) {
            this.value = value;
            this.type = value.getClass();
        }

        public void method(@Convert(DummyConverter.class) ReallyRandom value) {
            this.value = value;
            this.type = value.getClass();
        }
    }

    public static class DummyConverter implements Converter {
        @Override
        public Object convert(Object data, MethodParameter methodParameter) {
            return new ReallyRandom();
        }
    }

    public enum Colour {
        RED
    }

    public static class Random {

    }

    public static class ReallyRandom extends Random {

    }
}
