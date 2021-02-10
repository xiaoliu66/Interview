package com.baidu.utils;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FileUtils {
    // 生成的音频文件存放的目录
    public static String path = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\data\\";

    /**
     * 获取指定文件的大小（单位：KB)
     *
     * @param file
     * @return
     */
    public static double getSize(File file) {
        double size = file.length() / 1024 * 1.0;
        return size;
    }

    /**
     * 获取指定文件的时长（单位：S)
     *
     * @param file
     * @return
     */
    public static int getTime(File file) {
        Encoder encoder = new Encoder();
        long ls = 0;
        MultimediaInfo m;
        try {
            m = encoder.getInfo(file);
            ls = m.getDuration() / 1000;

        } catch (Exception e) {
            System.out.println("获取音频时长有误：" + e.getMessage());
        }
        return (int) ls;
    }
}
