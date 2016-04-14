package me.wbprime.springmvctesting.common.test;


import java.lang.reflect.Field;

/**
 * Class: ReflectionHelper
 * Date: 2016/04/14 12:32
 *
 * @author Elvis Wang [bo.wang35@renren-inc.com]
 */
public final class ReflectionHelper {
    public static void setField(final Object obj, final String fieldName, final Object value) {
        Field field = null;
        try {
            field = obj.getClass().getDeclaredField(fieldName);
        } catch (NoSuchFieldException ex) {
            throw new IllegalArgumentException(
                "Unexpected reflection exception - " + ex.getClass().getName() + ": " + ex.getMessage()
            );
        }

        field.setAccessible(true);

        try {
            field.set(obj, value);
        } catch (IllegalAccessException ex) {
            throw new IllegalStateException(
                "Unexpected reflection exception - " + ex.getClass().getName() + ": " + ex.getMessage());
        }

    }
}
