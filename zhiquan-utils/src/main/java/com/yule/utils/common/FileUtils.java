package com.yule.utils.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtils {

    /**
     * 重资源流中读取第一行内容
     * @param in
     * @return
     * @throws IOException
     */
    public static String readFristLineFormResource(InputStream in) throws IOException{
        // JDK 7 起支持 try-with-resources，无需手动关闭流
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            return br.readLine();
        }
    }


}
