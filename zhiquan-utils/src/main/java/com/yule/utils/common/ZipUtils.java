package com.yule.utils.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.*;


/**
 * 字符串压缩
 */
public class ZipUtils {

    private static final Base64.Encoder ENCODER = Base64.getMimeEncoder();

    private static final Base64.Decoder DECODER = Base64.getMimeDecoder();

    /**
     * 使用gzip进行压缩
     */
    public static String gzip(String primStr) {
        if (primStr == null || primStr.isEmpty()) {
            return primStr;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (GZIPOutputStream gzip = new GZIPOutputStream(out)) {
            gzip.write(primStr.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ENCODER.encodeToString(out.toByteArray());
    }


    /**
     * <p>
     * Description:使用gzip进行解压缩
     * </p>
     *
     * @param compressedStr 压缩后的文本
     * @return 解压后的字符串
     */
    public static String gunzip(String compressedStr) {
        if (compressedStr == null) {
            return null;
        }

        byte[] compressed = DECODER.decode(compressedStr);
        try (GZIPInputStream ginzip = new GZIPInputStream(new ByteArrayInputStream(compressed))) {
            return new String(ginzip.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 使用zip进行压缩
     *
     * @param str 压缩前的文本
     * @return 返回压缩后的文本
     */
    public static String zip(String str) {
        if (str == null) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (ZipOutputStream zout = new ZipOutputStream(out)) {
            zout.putNextEntry(new ZipEntry("0"));
            zout.write(str.getBytes(StandardCharsets.UTF_8));
            zout.closeEntry();
        } catch (IOException e) {
            return null;
        }
        return ENCODER.encodeToString(out.toByteArray());
    }


    /**
     * 使用zip进行解压缩
     *
     * @param compressedStr 压缩后的文本
     * @return 解压后的字符串
     */
    public static String unzip(String compressedStr) {
        if (compressedStr == null) {
            return null;
        }

        byte[] compressed = DECODER.decode(compressedStr);
        try (ZipInputStream zin = new ZipInputStream(new ByteArrayInputStream(compressed))) {
            zin.getNextEntry();
            return new String(zin.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return null;
        }
    }
}
