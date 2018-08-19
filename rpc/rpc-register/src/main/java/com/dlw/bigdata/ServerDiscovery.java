package com.dlw.bigdata;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @author dlw
 * @date 2018/8/19
 * @desc 服务发现
 */
public class ServerDiscovery {
    private static final Logger log = LoggerFactory
            .getLogger(ServerDiscovery.class);
    private String zookeeperConnectString;
    private String persistentPath;
    private int timeout;
    private ZooKeeper zooKeeper;
    private volatile List<String> dataList = new ArrayList<>();
    private CountDownLatch latch = new CountDownLatch(1);

    public ServerDiscovery(String zookeeperConnectString, String persistentPath, int timeout) throws Exception {
        this.zookeeperConnectString = zookeeperConnectString;
        this.persistentPath = persistentPath;
        this.timeout = timeout;
        ZooKeeper zooKeeper = initConnect();
        if (null != zooKeeper) {
            watchNode(zooKeeper);
        }
    }

    /**
     * 监听节点
     * @param zooKeeper
     */
    private void watchNode(ZooKeeper zooKeeper) throws Exception {
        List<String> children = zooKeeper.getChildren(persistentPath, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                if (watchedEvent.getType() == Event.EventType.NodeChildrenChanged) {
                    try {
                        watchNode(zooKeeper);
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                }
            }
        });
        children.forEach(s -> {
            try {
                byte[] data = zooKeeper.getData(persistentPath + "/" + s, false, null);
                this.dataList.add(new String(data));
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        });
        log.info("node info:{}",dataList);
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
                    if (watchedEvent.getState() == Watcher.Event.KeeperState.SyncConnected) {
                        latch.countDown();
                    }
                }
            });
            latch.await();
        }catch (Exception e) {
            log.error(e.getMessage());
        }
        return zooKeeper;
    }

    /**
     * 发现节点
     */
    public String discovery() {
        if (CollectionUtils.isEmpty(this.dataList)) {
            return null;
        }
        if (dataList.size() == 1) {
            return dataList.get(0);
        }
        //随机取一个
        return dataList.get(new Random().nextInt(dataList.size()));
    }
}