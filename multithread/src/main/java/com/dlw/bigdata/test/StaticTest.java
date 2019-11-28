package com.dlw.bigdata.test;

import java.util.ArrayList;
import java.util.List;

/**
 * author dlw
 * date 2018/10/4.
 */
public class StaticTest {
    private static int a = 1;

    public static void main(String[] args) {
        new Inner().print();
        String s1 = "123";
        String s2 = "456";
        String s3 = "hello" + s2;
        List<String> list = new ArrayList<>();
        list.add(s1);
    }

    public void print1() {
//        new Inner().print();
        System.out.println(a++);
    }

    static class Inner{
        int b = 2;
        public void print() {
            new StaticTest().print1();
//            System.out.println(a + b);
        }
    }

}
