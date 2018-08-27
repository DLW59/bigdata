package com.dlw.bigdata.io;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author dlw
 * @date 2018/8/21
 * @desc
 */
public class Client {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost",7777);
        OutputStream os = socket.getOutputStream();

        Socket socket2 = new Socket("localhost",7778);
        OutputStream os2 = socket.getOutputStream();
        write(os,os2);
    }

    public static void write(OutputStream os1,OutputStream os2) throws Exception {
        File file = new File("G:\\学习资料\\1\\[JSU发布组]昙花女优泷泽萝拉5部合集\\1.wmv");
        FileInputStream fis = new FileInputStream(file);
        byte[] bytes = new byte[1024 * 4];
        long maxSize = 0L;
        while (fis.read() != -1) {
            maxSize++;
            if (maxSize < 1024 * 1024 * 128) {
                System.out.println("往服务器1写文件中...");
                os1.write(bytes,0 ,bytes.length );
                os1.flush();
            }else {
                System.out.println("往服务器2写文件中...");
                os2.write(bytes,0 ,bytes.length );
                os2.flush();
            }
        }
        os1.close();
        os2.close();
        System.out.println("写文件结束");
    }
}
