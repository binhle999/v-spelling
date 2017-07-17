package com.binhle.vspelling.common.util;

/**
 * Created by BinhLe on 7/16/2017.
 */
public final class StringUtil {

    /**
     * Check string is null or empty.
     * @param value
     * @return
     */
    public static boolean isNullOrEmpty(String value) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return false;
    }
}
