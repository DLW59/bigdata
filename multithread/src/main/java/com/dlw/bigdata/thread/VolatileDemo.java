package com.dlw.bigdata.thread;

import sun.misc.Unsafe;

/**
 * @author dlw
 * @date 2019/12/13
 * @desc 1.如果不加volatile，用system打印下也会从主内存从新拉取值，所以线程会停止
 * 2.加了volatile直接从主内存获取
 */
public class VolatileDemo {
    private static volatile boolean flag = false;

    public static void main(String[] args) {
        System.out.println("开始执行");
        new Thread(() -> {
            while (flag) {
//                System.out.println("flag当前值："+ flag);
                break;
            }
            System.out.println("循环线程结束");
        }).start();
        flag = true;
        System.out.println("flag变为："+ flag);
    }
}
