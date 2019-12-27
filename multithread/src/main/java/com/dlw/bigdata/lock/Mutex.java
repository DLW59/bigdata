package com.dlw.bigdata.lock;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author dengliwen
 * @date 2019/7/17
 * @desc 自定义互斥锁  独占模式
 */
public class Mutex implements Lock, Serializable {

    private static final long serialVersionUID = -4791152830799433592L;
    private final Sync sync = new Sync();

    private static class Sync extends AbstractQueuedSynchronizer {

        private static final long serialVersionUID = 1868889894305990657L;

        @Override
        public boolean tryAcquire(int acquires) {
            //acquires 必须为1 因为是互斥锁一次只能有一个线程获取到锁
            assert acquires == 1;
            if (compareAndSetState(0, acquires)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int releases) {
            assert releases == 1;
            if (getState() == 0) {
                throw new IllegalMonitorStateException();
            }
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
         }


        Condition newCondition() {
            return new ConditionObject();
        }
    }

    public boolean isLocked() {
        return sync.isHeldExclusively();
    }

    public boolean hasQueuedThreads() {
        return sync.hasQueuedThreads();
    }

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }


    //测试mutex功能
    //最终一次只有一个线程获取到锁

    private final static Mutex mutex = new Mutex();

    private static class Worker extends Thread {

        @Override
        public void run() {
            try {
                mutex.lock();
                Thread.sleep(1000L);
                System.out.println(Thread.currentThread().getName());
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                mutex.unlock();
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
