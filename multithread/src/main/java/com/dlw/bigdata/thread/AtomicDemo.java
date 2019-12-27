package com.dlw.bigdata.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author dlw
 * @date 2019/12/15
 * @desc 比较synchronized、atomicLong、longAdder的效率 longAdder内部采用分段锁
 */
public class AtomicDemo {

    private static long count1 = 0L;
    private static AtomicLong count2 = new AtomicLong();
    private static LongAdder count3 = new LongAdder();
    private static AtomicInteger count4 = new AtomicInteger();
    private static final Object lock = new Object();
    public static void main(String[] args) throws InterruptedException {
//        syncCount();
//        atomicCount();
//        adderCount();
        atomicAdds();
    }

    private static void adderCount() throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread[] threads = new Thread[100];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000000; j++) {
                    count3.increment();
                }
            });
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println("count3 = " + count3 + "     adder耗时:" + (System.currentTimeMillis() - start));
    }

    private static void atomicCount() throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread[] threads = new Thread[100];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 100000; j++) {
                   count2.incrementAndGet();
                }
            });
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println("count2 = " + count2 + "     atomic耗时:" + (System.currentTimeMillis() - start));
    }

    public static void syncCount() throws InterruptedException {
        Thread[] threads = new Thread[100];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 100000; j++) {
                    synchronized (lock) {
                        count1++;
                    }
                }
            });
        }
        long start = System.currentTimeMillis();
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println("count1 = " + count1 + "     sync耗时:" + (System.currentTimeMillis() - start));
    }

    public static void atomicAdds() throws InterruptedException {
        Thread[] threads = new Thread[100];
        for (int i = 0; i < threads.length; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    count4.incrementAndGet();
                }
            }).start();
        }
        TimeUnit.SECONDS.sleep(5);
        System.out.println(count4.get());
    }
}
