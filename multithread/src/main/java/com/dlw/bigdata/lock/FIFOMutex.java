package com.dlw.bigdata.lock;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author dengliwen
 * @date 2019/7/18
 * @desc 先进先出非重入锁
 */
public class FIFOMutex {

    private final AtomicBoolean locked = new AtomicBoolean();
    private final Queue<Thread> waiters = new ConcurrentLinkedQueue<>();

    public void lock() {
       boolean wasInterrupted = false;
        Thread thread = Thread.currentThread();
        //如果容量不足会抛IllegalStateException异常
        //对于容量指定的队列用offer方法更好
        waiters.add(thread);

        //阻塞不是第一个进队列或不能获取锁的线程
        //peek获取对头元素，如果队列为空返回null
        // element方法如果队列为空抛NoSuchElementException异常
        //两个方法共同点：只获取不会删除元素
        while (waiters.peek() != thread || !locked.compareAndSet(false, true)) {
            LockSupport.park(this);
            if (thread.isInterrupted()) {
                wasInterrupted = true;
            }
        }
        //如果队列为空会抛NoSuchElementException异常，poll不会抛异常，会返回null
        //共同点：获取并删除元素
        waiters.remove();

        if (wasInterrupted) {
            thread.interrupt();
        }
    }

    public void unlock() {
        locked.set(false);
        LockSupport.unpark(waiters.peek());
    }

}
