package com.jparams.junit4.test.util;

import java.util.Arrays;
import java.util.Iterator;

public final class ObjectUtils {
    private ObjectUtils() {
    }

    public static String join(Iterable<Object> objects, String separator) {
        StringBuilder builder = new StringBuilder();

        Iterator<Object> iterator = objects.iterator();

        while (iterator.hasNext()) {
            builder.append(iterator.next());

            if (iterator.hasNext()) {
                builder.append(separator);
            }
        }

        return builder.toString();
    }

    public static String join(Object[] objects, String separator) {
        return join(Arrays.asList(objects), separator);
    }
}
