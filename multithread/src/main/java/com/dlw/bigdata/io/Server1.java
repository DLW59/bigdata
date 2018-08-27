package com.dlw.bigdata.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author dlw
 * @date 2018/8/21
 * @desc
 */
public class Server1 {
    public static void main(String[] args) throws Exception {
        System.out.println("启动成功...");
        ServerSocket ss1 = new ServerSocket(7777);
        Socket s1 = ss1.accept();
        InputStream is = s1.getInputStream();
        FileOutputStream fos = new FileOutputStream(new File("G:\\1.wmv"));
        int count = 0;
        while ((count = is.read()) != -1) {
            System.out.println("往本地写文件中。。。");
            fos.write(count);
            fos.flush();
        }
        fos.close();
        s1.close();
        System.out.println("写文件结束");
    }
}
