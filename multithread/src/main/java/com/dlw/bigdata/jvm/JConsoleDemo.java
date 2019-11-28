package com.dlw.bigdata.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dengliwen
 * @date 2019/8/1
 * @desc JConsole监控程序状态
 */
public class JConsoleDemo {

    //内存占位符对象 一个对象64k
    byte[] bytes = new byte[1024 * 64];//64k

    public static void createObj(int n) {
        List<JConsoleDemo> list = new ArrayList<>();
        for (int i = 0;i<n;i++) {
            list.add(new JConsoleDemo());
        }
//        System.gc();//gc
    }

    public static void main(String[] args) {
        createObj(100);
        System.gc();//gc
    }
}
