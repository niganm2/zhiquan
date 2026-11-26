package com.yule.utils.common;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class MD5Utils {

    /**
     * MD5加密
     * @param str
     * @return
     */
    public final static String encode(String str) {
        try {
            //创建具有指定算法名称的摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            //使用指定的字节数组更新摘要
            md.update(str.getBytes(StandardCharsets.UTF_8));
            //进行哈希计算，并用 JDK 17 的 HexFormat 转成小写十六进制字符串
            return HexFormat.of().formatHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String encodeWithSalt(String numStr, String salt) {
        return encode(encode(numStr) + salt);
    }

    public static void main(String[] args) {
        System.out.println(encode("test"));//e10adc3949ba59abbe56e057f20f883e
        System.out.println(encodeWithSalt("123456","123456"));//5f1d7a84db00d2fce00b31a7fc73224f
    }
}
