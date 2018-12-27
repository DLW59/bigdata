package com.dlw.bigdata.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

/**
 * @author dlw
 * @date 2018/8/16
 * @desc curator实现分布式锁
 */
public class CuratorDistribuyrLock {

    private static String connectString = "192.168.1.130:2181,192.168.1.131:2181,192.168.1.132:2181";
    private static int sessionTimeout = 2000;
    private static final String parentNode = "/locks";
    public static void main(String[] args) throws Exception {
        //创建zk客户端
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(sessionTimeout,3);
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(connectString, retryPolicy);
        curatorFramework.start();

        //创建分布式锁
        InterProcessMutex mutex = new InterProcessMutex(curatorFramework,parentNode);
        //获取锁  可配置超时时间(看源码)
//        mutex.acquire();//默认方法
        mutex.acquire(30000L, TimeUnit.SECONDS);//超时30秒

        //业务处理
        System.out.println("获得了锁");
        Thread.sleep(Long.MAX_VALUE);
        //释放锁
        mutex.release();
        //关闭客户端
        curatorFramework.close();
    }
}
