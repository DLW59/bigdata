package com.dlw.bigdata.interview;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author dlw
 * @date 2019/12/17
 * @desc 淘宝面试
 * 实现一个容器，提供add、size方法，线程一添加n个元素，线程二监控元素个数达到m时提示并结束
 */
public class TaoBaoInterview1 {

    public static void main(String[] args) throws InterruptedException {
        List<Integer> list = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);
        CountDownLatch latch2 = new CountDownLatch(1);
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                list.add(i);
                System.out.println("生产 " +i);
                if (list.size() == 5) {
                    latch.countDown();
                    try {
                        latch2.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("停止生产");
            latch2.countDown();
        });
        t2.start();
        t1.start();
    }


}
