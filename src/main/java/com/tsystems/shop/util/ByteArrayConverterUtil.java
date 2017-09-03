package com.tsystems.shop.util;


public class ByteArrayConverterUtil {

    public static byte[] convertBytes(Byte[] bytes) {
        byte[] resultBytes = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            resultBytes[i] = bytes[i];
        }
        return resultBytes;
    }

    public static Byte[] convertBytes(byte[] bytes) {
        Byte[] resultBytes = new Byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            resultBytes[i] = bytes[i];
        }
        return resultBytes;
    }
}
