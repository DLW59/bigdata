package com.dlw.bigdata.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * @author dlw
 * @date 2018/8/15
 * @desc 客户端监控
 */
public class ClientOnlineOrOffloneMonitor {
    private static String connectString = "192.168.52.130:2181,192.168.52.131:2181,192.168.52.132:2181";
    private static int sessionTimeout = 2000;
    private static final String parentNode = "/servers";
    private static ZooKeeper zooKeeper = null;
    private static volatile List<String> list = new ArrayList<>();

    public static void getConnect() throws Exception {
        zooKeeper = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            //注册监听
            @Override
            public void process(WatchedEvent watchedEvent) {
                try {
                    //再次注册监听
                    getServerList();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取服务器列表信息
     */
    public static void getServerList() throws Exception {
        //获取服务器的孩子节点数据
        List<String> children = zooKeeper.getChildren(parentNode, true);
        if (children.isEmpty()) {
            list.clear();
            System.out.println(list.toString());
            return;
        }
        children.forEach(s -> {
            byte[] data = new byte[0];
            try {
                data = zooKeeper.getData(parentNode + "/" + s, false, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            list.add(new String(data));
        });
        System.out.println(list.toString());
    }


    /**
     * 处理业务
     */
    public static void handleBusiness() throws InterruptedException {
        System.out.println("客户端开始。。。");
        Thread.sleep(Long.MAX_VALUE);
    }
    public static void main(String[] args) throws Exception {

        //1.获取zk连接
        getConnect();
        //2.获取服务器列表数据
        getServerList();
        //3.业务操作
        handleBusiness();
    }
}
