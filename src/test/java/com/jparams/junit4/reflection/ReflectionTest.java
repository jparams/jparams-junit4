package com.jparams.junit4.reflection;

import com.jparams.junit4.data.converter.Convert;
import com.jparams.junit4.data.converter.StringConverter;
import org.junit.Test;

import java.lang.reflect.Method;

import static junit.framework.TestCase.fail;
import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {
    @Test
    public void createsInstance() throws Exception {
        DummyClass instance = Reflection.createInstance(DummyClass.class);
        assertThat(instance).isNotNull();
    }

    @Test
    public void createInstanceThrowsInstanceCreationExceptionOnInstantiationException() throws Exception {
        try {
            Reflection.createInstance(DummyClassWithConstructor.class);
            fail("exception expected");
        } catch (InstanceCreationException e) {
            assertThat(e).hasMessage("Error instantiating class DummyClassWithConstructor")
                .hasCauseExactlyInstanceOf(InstantiationException.class);
        }
    }

    @Test
    public void createInstanceThrowsInstanceCreationExceptionOnIllegalAccessException() throws Exception {
        try {
            Reflection.createInstance(PrivateClass.class);
            fail("exception expected");
        } catch (InstanceCreationException e) {
            assertThat(e).hasMessage("Error instantiating class PrivateClass")
                .hasCauseExactlyInstanceOf(IllegalAccessException.class);
        }
    }

    @Test
    public void getMethodParametersReturnsParametersOnMethods() throws Exception {
        Method method = DummyClass.class.getDeclaredMethod("method", String.class, int.class);

        MethodParameter[] methodParameters = Reflection.getMethodParameters(method);

        assertThat(methodParameters).hasSize(2);

        assertThat(methodParameters[0].getType()).isEqualTo(String.class);
        assertThat(methodParameters[0].isAnnotationPresent(Convert.class)).isTrue();

        assertThat(methodParameters[1].getType()).isEqualTo(int.class);

    }

    public static class DummyClass {
        public void method(@Convert(StringConverter.class) String a, int b) {

        }
    }

    public static class DummyClassWithConstructor {
        public DummyClassWithConstructor(String someArg) {
        }
    }

    private static class PrivateClass {
        private PrivateClass() {
        }
    }
}
