# JParams for JUnit 4

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.jparams/jparams-junit4/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.jparams/jparams-junit4)
 [![Build Status](https://travis-ci.org/jparams/jparams-junit4.svg?branch=master)](https://travis-ci.org/jparams/jparams-junit4)

## Getting Started

### Get JParams

Maven:
```
<dependency>
    <groupId>com.jparams</groupId>
    <artifactId>jparams-junit4</artifactId>
    <version>1.x.x</version>
    <scope>test</scope>
</dependency>
```

Gradle:
```
testCompile 'com.jparams:jparams-junit4:1.x.x'
```

### Create a Test
Create a test class and run it with `JParamsTestRunner`

```java
@RunWith(JParamsTestRunner.class)
public class SomeTest {

}
```

### Choose a Reader
Readers are used to load data from a data source. These are annotations placed on test methods to enable parameterization.

```java
@RunWith(JParamsTestRunner.class)
public class SomeTest {
    // Inline data
    @Data({
        "Hi,     Bye",
        "Good,   Bad",
        "Happy,  Sad",
        "'null', null" // Compare null string to null value
    })
    @Test
    public void inline(String input1, String input2) throws Exception {
        assertThat(input1).isNotEqualTo(input2);
    }

    Reader
    @DataFile(path = "data.csv", location = Location.CLASSPATH)
    @Test
    public void fileInClassPath(String input1, String input2) throws Exception {
        assertThat(input1).isNotEqualTo(input2);
    }

    Reader
    @DataFile(path = "/data.csv", location = Location.FILE)
    @Test
    public void fileInFileSystem(String input1, String input2) throws Exception {
        assertThat(input1).isNotEqualTo(input2);
    }

    // Get data from provider
    @DataProvider(SampleDataProvider.class)
    @Test
    public void provider(String input1, String input2) throws Exception {
        assertThat(input1).isNotEqualTo(input2);
    }

    // Get data from method
    @DataMethod(source = SomeTest.class, method = "provideData")
    @Test
    public void provider(String input1, String input2) throws Exception {
        assertThat(input1).isNotEqualTo(input2);
    }

    // Non parameterized test
    @Test
    public void simpleTest() throws Exception {
        assertThat(input1).isNotEqualTo(input2);
    }

    public static Object[][] provideData() {
        return new Object[][] {
            {"Hi", "Bye"},
            {"Good", "Bad"},
            {"Happy", "Sad"}
        };
    }

    public static class SampleDataProvider implements Provider {
        @Override
        public Object[][] provide() {
            return new Object[][] {
                {"Hi", "Bye"},
                {"Good", "Bad"},
                {"Happy", "Sad"}
            };
        }
    }
}
```

### Name your Test
Name your test using the `@Name` annotation. If your test is not annotated, the default pattern `[{index}] - {params}` will be used.

Tokens:
- {class} - Simple name of the class under test
- {method} - Name of the method under test
- {index} - Index of the current row of parameters under test
- {params} - Comma separated list of all parameters
- {0}, {1}, {2} etc - Reference to individual parameters

```java
@RunWith(JParamsTestRunner.class)
public class SomeTest {
    @Data({
        "Hi,    Bye",
        "Good,  Bad",
        "Happy, Sad"
    })
    @Name("Assert that {0} does not equal {1}")
    @Test
    public void inline(String input1, String input2) throws Exception {
        assertThat(input1).isNotEqualTo(input2);
    }
}
```

## Type Conversion
Converters are used to convert representation of parameters into Java Types. Out of the box, JParams supports the following type conversions without any additional mark ups required:
- BigDecimal
- Boolean - "true" or "false"
- Class -  fully qualified class name
- Date - time stamp e.g. "1490860142531" or ISO-8601 date format "yyyy-MM-dd'T'HH:mm:ss'Z'" e.g. "2017-03-30T08:49:30Z"
- Double
- Enum
- Float
- Integer
- Long

## Custom Converters
You can create a custom Converter assign it to method parameter using the `@Convert` annotation:

```java
@RunWith(JParamsTestRunner.class)
public class SomeTest {
    @Data({
        "'John, Doe',   25",
        "'Susan, Smith', 22"
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

    public static class NameConverter implements Converter<Name> {
        @Override
        public Name convert(String data) {
            String[] split = data.split(",");
            return new Name(split[0], split[1].trim());
        }
    }
}
```

## Custom Readers
To create a customer reader, create an annotation and mark it with @ReadWith. An example is shown below:

```java
@ReadWith(CustomReader.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CustomData {
    String customValue();
}
```

Now create the reader for this annotation.

```java
public class CustomReader implements Reader<CustomData> {
    @Override
    public Object[][] readData(CustomData annotation) {
        return new Object[][] {{ annotation.customValue() }};
    }
}
```

Now go ahead and use it in your test.

```java
@RunWith(JParamsTestRunner.class)
public class SomeTest {
    @CustomData(customValue = "bob")
    @Test
    public void customReader(String input1) throws Exception {
        assertThat(input1).isEqualTo("bob");
    }
}
```

## Compatibility
This library is compatible with: JUnit 4.12 and Java 7 and above.

## License
Free to use, modify an distribute under the [MIT License](http://www.opensource.org/licenses/mit-license.php)
