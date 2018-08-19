package com.dlw.bigdata.reflect;

import java.lang.reflect.Constructor;

/**
 * @author dlw
 * @date 2018/8/16
 * @desc 反射
 */
public class ReflectTest {
    public static void main(String[] args) throws Exception {
        Class<?> aClass = Class.forName("com.dlw.bigdata.queue.BlockingQueueTest");
        //调用默认无参构造
        Object instance = aClass.newInstance();
        System.out.println(instance.toString());
    }
}
