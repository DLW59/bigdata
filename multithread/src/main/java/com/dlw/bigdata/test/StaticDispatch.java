package com.dlw.bigdata.test;

import java.awt.*;

/**
 * @author dengliwen
 * @date 2019/8/17
 * @desc 静态分派
 * Human man = new Man();
 * 左边是变量静态类型  右边是实际类型
 * 静态类型在编译期间可知，实际类型在运行时可知
 * 注：编译器在重载时是根据参数的静态类型来确定重载方法的。
 *
 * 静态分派的应用的典型场景是 重载。
 * 静态分派属于多分派  动态分派属于单分派
 */
public class StaticDispatch {

    static abstract class Human { }

    static class Man extends Human {}

    static class Woman extends Human {}

    public void hello(Human human) {
        System.out.println("hello human");
    }

    public void hello(Man man) {
        System.out.println("hello man");
    }

    public void hello(Woman woman) {
        System.out.println("hello woman");
    }

    public static void main(String[] args) {
        StaticDispatch dispatch = new StaticDispatch();
        Human man = new Man();
        Human woman = new Woman();
        if (man instanceof Human) {
            System.out.println(1);
        }
        if (man instanceof Man) {
            System.out.println(2);
        }
        dispatch.hello(man);//        hello human
        dispatch.hello(woman);//        hello human
        dispatch.hello((Man) man);//        hello man
        dispatch.hello((Woman) woman);//        hello woman
    }
}
