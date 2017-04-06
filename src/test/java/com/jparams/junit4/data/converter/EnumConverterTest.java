package com.jparams.junit4.data.converter;

import com.jparams.junit4.reflection.MethodParameter;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class EnumConverterTest {
    @Test
    public void returnsEnumFromValue() throws Exception {
        MethodParameter mockMethodParameter = mock(MethodParameter.class);
        doReturn(Colour.class).when(mockMethodParameter).getType();

        Object value = new EnumConverter().convert("RED", mockMethodParameter);
        assertThat(value).isEqualTo(Colour.RED);
    }

    public enum Colour {
        RED
    }
}
