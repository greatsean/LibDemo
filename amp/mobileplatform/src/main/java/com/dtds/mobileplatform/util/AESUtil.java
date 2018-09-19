package com.dtds.mobileplatform.util;

import android.annotation.SuppressLint;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@SuppressLint("DefaultLocale")
public class AESUtil {
    public static byte[] encryptionStr(String str) {
        try {
            return encryptionStr(str, "!@#987321ascwfds");
        } catch (Exception e) {
        }
        return null;
    }

    public static String decryptStr(byte[] str) {
        try {
            return decryptStr(str, "!@#987321ascwfds");
        } catch (Exception e) {
        }
        return null;
    }

    public static byte[] encryptionStr(String str, String pawnKey) throws Exception {
        try {
            byte[] raw = pawnKey.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(pawnKey.getBytes());
            cipher.init(1, skeySpec, iv);

            return cipher.doFinal(str.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encrypStr(String str) throws Exception {
        return parseByte2HexStr(encryptionStr(str));
    }

    public static String decryptStr(byte[] str, String pawnKey) {
        try {
            byte[] raw = pawnKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(pawnKey.getBytes());

            cipher.init(2, skeySpec, iv);

            return new String(cipher.doFinal(str), "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 灏嗕簩杩涘埗杞崲鎴�6杩涘埗
     *
     * @param buf
     * @return
     */
    @SuppressLint("DefaultLocale")
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr 16进制的数组转换成String类型再传过来的参数
     * @return 转换回来的二进制数组
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static String encrypStr(String str, String key) throws Exception {
        return parseByte2HexStr(encryptionStr(str, MD5bit16(key)));
    }

    public static String decryptStr(String str, String key) throws Exception {
        return decryptStr(parseHexStr2Byte(str), MD5bit16(key));
    }

    public static String MD5bit16(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return result.substring(8, 24);
    }

    public static void main(String[] args) throws Exception {
        String origin = "hello";
        String encryptOrigin = encrypStr(origin, "wltest");
        System.out.println(encryptOrigin);
        System.out.println(decryptStr(encryptOrigin, "wltest"));
    }
}
