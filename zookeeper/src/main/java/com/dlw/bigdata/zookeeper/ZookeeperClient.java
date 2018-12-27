package com.dlw.bigdata.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dlw
 * @date 2018/8/14
 * @desc
 */
public class ZookeeperClient {

//    private static final Logger log = LoggerFactory.getLogger(ZookeeperClient.class);
    private static String connectString = "192.168.52.130:2181,192.168.52.131:2181,192.168.52.132:2181";
    private static int sessionTimeout = 2000;
    private static ZooKeeper zooKeeper = null;
    private volatile List<String> dataList = new ArrayList<>();
    public static void main(String[] args) throws Exception {
        zooKeeper = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            //注册监听
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("事件类型/路劲:" + watchedEvent.getType() +"/"+watchedEvent.getPath());
                //创建目录改变的监听  一直监听
//                getChildren("/registry",true);
            }
        });
        ZookeeperClient client = new ZookeeperClient();
        client.getChildren("/registry",true);
//        createNode();
//        getData();
    }

    /**
     * 创建节点
     * @throws KeeperException
     * @throws InterruptedException
     */
    public static void createNode() throws KeeperException, InterruptedException {
        String path = "/zk";
        zooKeeper.create(path, "helloZk".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        if(exist(path)) {
            System.out.println("节点已存在");
            return;
        }
        System.out.println("节点创建成功");
        Thread.sleep(Long.MAX_VALUE);
    }

    private static boolean exist(String path){
        try {
            Stat stat = zooKeeper.exists(path, false);
            return null != stat;
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**\
     * 获取子节点
     * @param path
     * @param b
     */
    private void getChildren(String path, boolean b) throws KeeperException, InterruptedException {
       /* try {
            List<String> children = zooKeeper.getChildren(path, b);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } {

        }*/
        List<String> children = zooKeeper.getChildren(path, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                if (watchedEvent.getType() == Event.EventType.NodeChildrenChanged) {
                    try {
//                        watchNode(zooKeeper);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        });
        children.forEach(s -> {
            try {
                byte[] data = zooKeeper.getData("/registry" + "/" + s, false, null);
                this.dataList.add(new String(data));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        System.out.println(dataList);
    }

    /**
     * 获取节点数据
     * @throws Exception
     */
    public static void getData() throws Exception {
        byte[] data = zooKeeper.getData("/zk", false, null);
        System.out.println(new String(data));
    }
}
