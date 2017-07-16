package com.binhle.vspelling.common.extension;

/**
 * Created by BinhLe on 7/16/2017.
 */
public final class StringExtension {

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
