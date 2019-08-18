package com.example.clone.util;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;

@Slf4j
public class StringUtil {

    public StringUtil() {
    }

    /**
     * 字符串反转(递归)
     *
     * @param originStr 原始字符串
     * @return 反转后的字符串
     */
    private static String reverse(String originStr) {
        if (null == originStr || originStr.length() <= 1) return originStr;
        return reverse(originStr.substring(1)) + originStr.charAt(0);
    }


    public static void main(String[] args) {
        String str = "advance";
        System.out.println(StringUtil.reverse(str));

        //怎样将GB2312编码的字符串转换为ISO-8859-1编码的字符串
        try {
            String s = "你好";
            new String(s.getBytes("GB2312"), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }



    }
}
