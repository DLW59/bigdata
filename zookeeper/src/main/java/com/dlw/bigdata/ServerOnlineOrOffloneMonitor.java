package com.dlw.bigdata;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * @author dlw
 * @date 2018/8/15
 * @desc 服务器上线下线监控感知
 */
public class ServerOnlineOrOffloneMonitor {

    private static String connectString = "192.168.52.130:2181,192.168.52.131:2181,192.168.52.132:2181";
    private static int sessionTimeout = 2000;
    private static final String parentNode = "/servers";
    private static ZooKeeper zooKeeper = null;
    public static void getConnect() throws Exception {
        zooKeeper = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            //注册监听
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("事件类型/路劲:" + watchedEvent.getType() +"/"+watchedEvent.getPath());
            }
        });
    }

    /**
     * 注册服务
     */
    public static void registeServer(String ip) throws Exception {
        Stat stat = zooKeeper.exists(parentNode, false);
        if (null == stat) {
            System.out.println("父节点不存在，请创建");
            //创建父节点
            zooKeeper.create(parentNode, " 父节点创建成功!".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        zooKeeper.create(parentNode + "/server", ip.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(ip + " 开始上线");
    }

    /**
     * 处理业务
     */
    public static void handleBusiness(String ip) throws InterruptedException {
        System.out.println("处理" + ip + "的业务");
        Thread.sleep(Long.MAX_VALUE);
    }
    public static void main(String[] args) throws Exception {

        //1.创建连接
        getConnect();
        //2.注册服务器到zk
        registeServer(args[0]);
        //3.业务应用
        handleBusiness(args[0]);
    }
}
