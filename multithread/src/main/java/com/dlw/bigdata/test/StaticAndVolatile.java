package com.dlw.bigdata.test;

/**
 * @author dengliwen
 * @date 2019/8/17
 * @desc 测试static与volatile的区别
 * static修改的变量会存在方法区，线程间共享，多线程下非线程安全
 * volatile修饰的变量在线程间共享，修改它的值会强制刷到主存，也防止对指令的重排序。
 *
 * 但是只使用static修饰的变量第一次刷到线程栈贞后不会每次都去刷新
 * 用volatile则会每次刷新到主存
 *
 * 调用System.out.println  方法会刷新一次内存
 */
public class StaticAndVolatile {

    private static int a;

    public static void main(String[] args) {

        for (int i = 1;i<=5;i++) {
            int finalI = i;
            new Thread(() -> {
                if ((finalI & 1) == 0) {
                    System.out.println(Thread.currentThread().getName() + a++);
                }

            }, "T" + i).start();
        }
    }
}
