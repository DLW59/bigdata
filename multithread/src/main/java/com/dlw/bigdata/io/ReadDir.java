package com.dlw.bigdata.io;

import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.Files;

/**
 * author dlw
 * date 2018/10/2.
 * 递归读取文件下的所以文件
 */
public class ReadDir {
    File file = null;
    static String path = "F:\\java架构师教程";
    public static void main(String[] args) {
        new ReadDir().readFile(path);
    }

    public void readFile(String path) {
         file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File file1 : files) {
                readFile(file1.getPath());
            }
        }else {
            System.out.println(file.getAbsolutePath());
        }
    }
}
