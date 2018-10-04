package com.dlw.bigdata;

import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author dlw
 * @date 2018/8/18
 * @desc 服务注册
 */
public class ServerRegister {
    private static final Logger log = LoggerFactory.getLogger(ServerRegister.class);
    private String zookeeperConnectString;
    private String persistentPath;
    private String tempPath;
    private int timeout;
    private String registerData;
    private ZooKeeper zooKeeper = null;
    private CountDownLatch latch = new CountDownLatch(1);
    private Lock lock = new ReentrantLock();

    public ServerRegister() {
    }

    public ServerRegister(String zookeeperConnectString, String persistentPath, String tempPath, int timeout) {
        this.zookeeperConnectString = zookeeperConnectString;
        this.persistentPath = persistentPath;
        this.tempPath = tempPath;
        this.timeout = timeout;
    }

    /**
     * 初始化连接
     * @return
     */
    private ZooKeeper initConnect() throws Exception {
        try {
            zooKeeper = new ZooKeeper(zookeeperConnectString, timeout, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    eventMonitor(watchedEvent);
                }
            });
            latch.await();
        }catch (Exception e) {
            log.error(e.getMessage());
        }
        return zooKeeper;
    }

    /**
     * 事件状态监听
     * @param watchedEvent
     */
    private void eventMonitor(WatchedEvent watchedEvent) {
        try {
            lock.lock();
            if (watchedEvent.getState() == Watcher.Event.KeeperState.SyncConnected) {
                latch.countDown();
            }else if (watchedEvent.getState() == Watcher.Event.KeeperState.Disconnected) {
                log.info("链接断开，或session迁移，重试链接并链接成功后重新注册数据....");
                register(registerData);
            }else if (watchedEvent.getState() == Watcher.Event.KeeperState.AuthFailed) {
                close();
                throw new RuntimeException("ZK Connection auth failed...");
            }else if (watchedEvent.getState() == Watcher.Event.KeeperState.Expired) {
                // session过期,这是个非常严重的问题,有可能client端出现了问题,也有可能zk环境故障
                // 此处仅仅是重新实例化zk client
                log.info("Expired(重连)并重新进行数据在zk上的注册...");
                register(registerData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    /**
     * 注册服务
     */
    public void register(String data) throws Exception {
        if (StringUtils.isEmpty(data)) {
            return;
        }
        this.registerData = data;
        if (null == initConnect()) {
            return;
        }
        createNode(data);
    }

    /**
     * 随机获取zk集群中的一台服务器地址
     * @return
     */
    private String getZkHostAddr() {
        String[] addrs = zookeeperConnectString.split(",");
        String addr = addrs[new Random().nextInt(addrs.length)];
        return addr;
    }

    /**
     * 创建节点
     */
    private void createNode(String data) throws Exception {
        if (null == zooKeeper.exists(persistentPath, false)) {
            zooKeeper.create(persistentPath,null , ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT );
        }
        String path = zooKeeper.create(tempPath, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        log.info("create zookeeper node ({} => {})", path, data);
    }

    /**
     * 关闭zk连接
     */
    private void close() {
        if (null != zooKeeper) {
            try {
                zooKeeper.close();
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        }
    }
}
