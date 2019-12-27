package com.dlw.bigdata.lock;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author dengliwen
 * @date 2019/7/17
 * @desc 支持同时多个线程获取锁  类似信号量Semaphore  共享模式
 */
public class MultiLock implements Lock, Serializable {
    private static final long serialVersionUID = 4963235018536097191L;

    //同时允许2个线程获得锁
    private static final Sync sync = new Sync(2);

    private static class Sync extends AbstractQueuedSynchronizer {

        private static final long serialVersionUID = 3904956523041840378L;

        Sync(int permits) {
            if (permits <= 0) {
                throw new IllegalArgumentException("permits 必须大于0");
            }
            setState(permits);
        }

        @Override
        protected final int tryAcquireShared(int arg) {
            for (;;) {
                final int state = getState();
                final int surplus = state - arg;
                if (surplus < 0 || compareAndSetState(state, surplus)) {
                    return surplus;
                }
            }


        }

        @Override
        protected final boolean tryReleaseShared(int arg) {
            for (;;) {
                final int current = getState();
                final int available = current + arg;
                if (available < current) {
                    throw new RuntimeException("超出最大许可数");
                }
                if (compareAndSetState(current, available)) {
                    return true;
                }
            }
        }

        Condition newCondition() {
            return new ConditionObject();
        }
    }

    @Override
    public void lock() {
        sync.acquireShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquireShared(1) >= 0;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireSharedNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.releaseShared(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

    private static MultiLock lock = new MultiLock();
    private static class Worker extends Thread {

        @Override
        public void run() {
            try {
                lock.lock();
                Thread.sleep(1000L);
                System.out.println(Thread.currentThread().getName());
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }

        }
    }

    public static void main(String[] args) throws Exception {

        for (int i=0;i<5;i++) {
            Worker w = new Worker();
            w.setName("thread-" + i);
            w.start();
        }

        Thread.sleep(10000L);
    }
}
