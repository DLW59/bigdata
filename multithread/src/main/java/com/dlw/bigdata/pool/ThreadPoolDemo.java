package com.dlw.bigdata.pool;

/**
 * @author dengliwen
 * @date 2019/7/28
 * @desc
 */
public class ThreadPoolDemo {



    public static void main(String[] args) throws InterruptedException {
        ThreadLocal local = ThreadLocal.withInitial(() -> "This is the initial value");
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                local.set("t1 value");
                System.out.println(local.get());
            }
        },"t1");
        t1.start();
//        t1.join();
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                local.set("t2 value");
                System.out.println(local.get());
            }
        },"t2");
        t2.start();
//        t2.join();
        System.out.println(local.get());
    }
}
