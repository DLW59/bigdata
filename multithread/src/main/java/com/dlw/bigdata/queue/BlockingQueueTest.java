package com.dlw.bigdata.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dlw
 * @date 2018/8/16
 * @desc
 */
public class BlockingQueueTest {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(1);
        BlockingQueue blockingQueue = new LinkedBlockingQueue(3);
        for (int i=0;i<5;i++) {
            Producer producer = new Producer(blockingQueue,atomicInteger);
            new Thread(producer).start();
            atomicInteger.incrementAndGet();
        }

        for (int i=0;i<3;i++) {
            Consumer consumer = new Consumer(blockingQueue,atomicInteger);
            new Thread(consumer).start();
            atomicInteger.incrementAndGet();
        }
    }

    static class Producer implements Runnable{
        BlockingQueue blockingQueue;
        AtomicInteger atomicInteger;
        public Producer(BlockingQueue blockingQueue, AtomicInteger atomicInteger) {
            this.blockingQueue = blockingQueue;
            this.atomicInteger = atomicInteger;
        }

        @Override
        public void run() {
            try {
                blockingQueue.put(atomicInteger.get());
                System.out.println("生产者" + atomicInteger.get() + "开始生产");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Consumer implements Runnable{
        BlockingQueue blockingQueue;
        AtomicInteger atomicInteger;
        public Consumer(BlockingQueue blockingQueue, AtomicInteger atomicInteger) {
            this.blockingQueue = blockingQueue;
            this.atomicInteger = atomicInteger;
        }

        @Override
        public void run() {
            try {
                int take = (int) blockingQueue.take();
                System.out.println("消费者" + atomicInteger.get() + "开始消费");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
