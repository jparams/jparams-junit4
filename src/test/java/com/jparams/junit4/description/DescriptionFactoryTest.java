package com.jparams.junit4.description;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DescriptionFactoryTest {
    private DescriptionFactory subject;

    @Before
    public void setUp() throws Exception {
        subject = new DescriptionFactory(DescriptionFactoryTest.class);
    }

    @Test
    public void createsSuiteDescription() throws Exception {
        FrameworkMethod mockMethod = mock(FrameworkMethod.class);
        when(mockMethod.getName()).thenReturn("methodName");

        Description description = subject.createDescription(mockMethod, new Object[0][]);
        assertThat(description.getDisplayName()).isEqualTo("methodName");
    }

    @Test
    public void hasChildrenPerDataRow() throws Exception {
        FrameworkMethod mockMethod = mock(FrameworkMethod.class);
        when(mockMethod.getName()).thenReturn("methodName");

        Description description = subject.createDescription(mockMethod, new Object[][] {{"a"}, {"b"}, {"c"}});
        assertThat(description.getChildren().size()).isEqualTo(3);
    }

    @Test
    public void usesFormatFromNameAnnotation() throws Exception {
        Name mockName = mock(Name.class);
        doReturn("{class} {method} {index} [{params}] {0} {1} - Test!").when(mockName).value();

        FrameworkMethod mockMethod = mock(FrameworkMethod.class);
        when(mockMethod.getName()).thenReturn("methodName");
        when(mockMethod.getAnnotation(Name.class)).thenReturn(mockName);

        Description description = subject.createDescription(mockMethod, new Object[][] {{"a", "1"}, {"b", "2"}});

        Description description1 = description.getChildren().get(0);
        assertThat(description1.getMethodName()).isEqualTo("DescriptionFactoryTest methodName 0 [a, 1] a 1 - Test!");

        Description description2 = description.getChildren().get(1);
        assertThat(description2.getMethodName()).isEqualTo("DescriptionFactoryTest methodName 1 [b, 2] b 2 - Test!");
    }

    @Test
    public void usesDefaultNameFormat() throws Exception {
        FrameworkMethod mockMethod = mock(FrameworkMethod.class);
        when(mockMethod.getName()).thenReturn("methodName");

        Description description = subject.createDescription(mockMethod, new Object[][] {{"a", "1"}, {"b", "2"}});

        Description description1 = description.getChildren().get(0);
        assertThat(description1.getMethodName()).isEqualTo("[0] - a, 1");

        Description description2 = description.getChildren().get(1);
        assertThat(description2.getMethodName()).isEqualTo("[1] - b, 2");
    }
}
