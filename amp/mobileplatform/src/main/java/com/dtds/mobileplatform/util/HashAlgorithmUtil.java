package com.dtds.mobileplatform.util;

import java.security.MessageDigest;

/**
 * Created by lixiaohui on 2017/2/24.
 * Hash算法工具类
 */
public class HashAlgorithmUtil {
    /**
     * 映射16进制字符
     *
     * @param args
     */
    public static char[] hexChar = {'0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 不抛异常的获得MD5 String值
     *
     * @param str
     * @return
     */
    public static String getMd5StrSafe(String str) {
        String md5Str = null;
        try {
            md5Str = getMd5Str(str);
        } catch (Exception e) {
            md5Str = str;//hash失败直接使用原值，失败可能性很低
        }
        return md5Str;
    }

    public static String getMd5Str(String str) throws Exception {
        return getHash(str, "MD5");
    }


    private static String getHash(String str, String hashType)
            throws Exception {
        MessageDigest md5 = MessageDigest.getInstance(hashType);
        md5.update(str.getBytes("UTF-8"));
        return toHexString(md5.digest());
    }

    private static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(hexChar[(b[i] & 0xf0) >>> 4]);
            sb.append(hexChar[b[i] & 0x0f]);
        }
        return sb.toString();
    }


}
