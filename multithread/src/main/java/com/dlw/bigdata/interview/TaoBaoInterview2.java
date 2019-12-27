package com.dlw.bigdata.interview;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author dlw
 * @date 2019/12/19
 * @desc 实现一个固定同步容器，有put、get、getCount方法，支持两个生产者线程写数据和10个消费者线程消费
 */
public class TaoBaoInterview2 {
    private static final int MAX_SIZE = 10;
    private static BlockingQueue queue = new ArrayBlockingQueue(MAX_SIZE);
    private static List list = Collections.synchronizedList(new ArrayList<>());
    private static ExecutorService executor = Executors.newFixedThreadPool(12);
    @SneakyThrows
    public static void main(String[] args) {
        AtomicLong produce = new AtomicLong();
        AtomicLong consume = new AtomicLong();
        CountDownLatch produceLatch = new CountDownLatch(1);
        CountDownLatch consumeLatch = new CountDownLatch(1);
//        TimeUnit.SECONDS.sleep(10);
        for (int i = 0; i < 1; i++) {
            executor.execute(new Consumer(consume,consumeLatch,consumeLatch));
        }
        for (int i = 0; i < 1; i++) {
            executor.execute(new Producer(produce,produceLatch,consumeLatch));
        }
    }

    private static class Producer implements Runnable {
        private AtomicLong count;
        CountDownLatch produceLatch;
        CountDownLatch consumeLatch;

        public Producer(AtomicLong count, CountDownLatch produceLatch, CountDownLatch consumeLatch) {
            this.count = count;
            this.produceLatch = produceLatch;
            this.consumeLatch = consumeLatch;
        }

        @SneakyThrows
        @Override
        public void run() {
            for (;;) {
//                queue.put(1);
                long i = count.incrementAndGet();
                System.out.println("生产：" + i);
                list.add(i);
                if (list.size() == MAX_SIZE) {
                    System.out.println("停止生产");
                    produceLatch.await();
                }
                consumeLatch.countDown();
            }
        }
    }

    private static class Consumer implements Runnable {
        private AtomicLong count;
        CountDownLatch produceLatch;
        CountDownLatch consumeLatch;
        public Consumer(AtomicLong count, CountDownLatch produceLatch, CountDownLatch consumeLatch) {
            this.count = count;
            this.produceLatch = produceLatch;
            this.consumeLatch = consumeLatch;
        }

        @SneakyThrows
        @Override
        public void run() {
            for (;;) {
                consumeLatch.await();
//                queue.take();
                long i = count.incrementAndGet();
                System.out.println("消费：" + i);
                list.remove(i);
                if (list.size() == 0) {
                    System.out.println("停止消费");
//                    produceLatch.countDown();
                }
            }
        }
    }
}
