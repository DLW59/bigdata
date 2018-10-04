package com.dlw.bigdata.test;

/**
 * author dlw
 * date 2018/10/4.
 */
public class StaticTest {
    private static int a = 1;

    public static void main(String[] args) {
        new Inner().print();
    }

    public void print1() {
        new Inner().print();
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
