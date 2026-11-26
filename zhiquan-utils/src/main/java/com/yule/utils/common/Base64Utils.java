package com.yule.utils.common;

import java.util.Base64;

/**
 * Base64 工具类（JDK 8 起使用 java.util.Base64 替代已移除的 sun.misc.BASE64Encoder/BASE64Decoder）
 */
public class Base64Utils {

    private static final Base64.Encoder ENCODER = Base64.getMimeEncoder();

    private static final Base64.Decoder DECODER = Base64.getMimeDecoder();

    /**
     * 解码
     *
     * @param base64 密文
     * @return 明文字节数组，解码失败返回 null
     */
    public static byte[] decode(String base64) {
        if (base64 == null) {
            return null;
        }
        try {
            return DECODER.decode(base64);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * 编码
     *
     * @param data 明文字节数组
     * @return 密文
     */
    public static String encode(byte[] data) {
        return data == null ? null : ENCODER.encodeToString(data);
    }
}
