package com.elon.license.machine.util;

/**
 * 문자열 관련 유틸
 */
public class StringUtil {

    /**
     *  null check
     * @param payload
     * @return
     */
    public static boolean isNull(String payload) {
        if(payload == null)return true;
        if(payload.equals(""))return true;
        return false;
    }
}
