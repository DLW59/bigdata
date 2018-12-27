package com.dlw.bigdata.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;

import java.io.File;

/**
 * @author dengliwen
 * @date 2018/11/18
 */
public class TestingServerDemo {

    public static void main(String[] args) throws Exception{
        String path = "/zkDemo";
        String dir = "F:\\workspace\\local\\bigdata\\zookeeper\\src\\main\\java\\com\\dlw\\bigdata\\curator";
        try (TestingServer testingServer = new TestingServer(2181,new File(dir))) {
            CuratorFramework client = CuratorFrameworkFactory.builder()
                    .connectString(testingServer.getConnectString())
                    .sessionTimeoutMs(5000)
                    .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                    .build();
            client.start();
            System.out.println(client.getChildren().forPath(path));

        }
    }
}
