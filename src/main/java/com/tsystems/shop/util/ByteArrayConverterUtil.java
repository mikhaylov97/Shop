package com.tsystems.shop.util;

/**
 * This class helps you to convert byte arrays. For example, from primitive byte[]
 * to boxed Byte[] and vice versa
 */
public class ByteArrayConverterUtil {

    /**
     * Empty constructor
     */
    private ByteArrayConverterUtil() {

    }

    /**
     * This method converts Byte[] to byte[]
     * @param bytes - method gets boxed Byte array
     * @return converted primitive byte array
     */
    public static byte[] convertBytes(Byte[] bytes) {
        byte[] resultBytes = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            resultBytes[i] = bytes[i];
        }
        return resultBytes;
    }

    /**
     * This method converts byte[] to Byte[]
     * @param bytes - method gets primitive byte array
     * @return converted boxed Byte array
     */
    public static Byte[] convertBytes(byte[] bytes) {
        Byte[] resultBytes = new Byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            resultBytes[i] = bytes[i];
        }
        return resultBytes;
    }
}
