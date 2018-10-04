package com.dlw.bigdata.test;

import com.dlw.bigdata.lock.Test;

import java.util.Collections;

/**
 * author dlw
 * date 2018/10/3.
 */
public class Synchronized {

    public static void main(String[] args) {
        test();
        test1();
    }

    private static void test() {
        long start = System.currentTimeMillis();
        for (int i=0;i<100000;i++) {

        }
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }

    private synchronized static void test1() {
        long start = System.currentTimeMillis();
        for (int i=0;i<100000;i++) {

        }
        long end = System.currentTimeMillis();
        System.out.println(end-start);

    }


}
