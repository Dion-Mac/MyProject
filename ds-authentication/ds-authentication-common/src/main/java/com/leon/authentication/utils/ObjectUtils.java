package com.leon.authentication.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author LeonMac
 * @description
 * @date 2020/4/9
 */

public class ObjectUtils {

    public static String toString(Object object) {
        if (object == null) {
            return null;
        }
        return object.toString();
    }

    public static Long toLong(Object object) {
        if (object == null) {
            return 0L;
        }
        if (object instanceof Double || object instanceof Float) {
            return Long.valueOf(StringUtils.substringBefore(object.toString(), "."));
        }
        if (object instanceof Number) {
            return Long.valueOf(object.toString());
        }
        if (object instanceof String) {
            return Long.valueOf(object.toString());
        } else return 0L;
    }

    public static Integer toInt(Object object) {
        return toLong(object).intValue();
    }

}
