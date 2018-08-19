package com.dlw.bigdata;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author dlw
 * @date 2018/8/15
 * @desc zk 实现分布式锁
 */
public class ZookeeperDistributeLock {
    private static String connectString = "192.168.52.130:2181,192.168.52.131:2181,192.168.52.132:2181";
    private static int sessionTimeout = 2000;
    private static final String parentNode = "/locks";
    private static final String subNode = "/lock";
    private static volatile String thisPath = null;//当前路劲
    private static ZooKeeper zooKeeper = null;
    private static CountDownLatch latch = new CountDownLatch(1);
    public static void getConnect() throws Exception {
            zooKeeper = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
                //注册监听
                public void process(WatchedEvent watchedEvent) {
//                    if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
//                        if (watchedEvent.getType() == Event.EventType.NodeDeleted &&
//                                watchedEvent.getPath().equals(thisPath)) {
//                            try {
//                                zooKeeper.create(parentNode + subNode, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
////                        }
//                    }
                    latch.countDown();
                }
            });
        latch.await();
    }

    /**
     * 创建零时有序节点（分布式锁）
     */
    public static void registeLock() throws Exception {
        Stat stat = zooKeeper.exists(parentNode, false);
        if (null == stat) {
            //创建父节点
            zooKeeper.create(parentNode, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        //创建零时节点
        thisPath = zooKeeper.create(parentNode + subNode, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(thisPath);
        List<String> children = zooKeeper.getChildren(parentNode, true);
        if (children.isEmpty()) {
            return;
        }
        //对节点名称排序
        Collections.sort(children);
        //当前节点为最小节点获得锁
        if (thisPath.equals(children.get(0))) {
            //TODO 处理获得了锁的逻辑
            doSomething(thisPath);
        }
        Thread.sleep(Long.MAX_VALUE);
    }

    public static void doSomething(String path) throws Exception {
        try {
            System.out.println("当前获得锁的节点是：" + path);
        }finally {
            //释放锁
            zooKeeper.delete(path,-1);
        }
    }
    public static void main(String[] args) throws Exception {
        //获取zk连接
        getConnect();
        //注册零时有序节点到zk
        registeLock();
    }
}
