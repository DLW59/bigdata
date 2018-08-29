package com.dlw.bigdata;


import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.permission.FsPermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Iterator;
import java.util.Map;

/**
 * @author dlw
 * @date 2018/8/21
 * @desc hdfs客户端 对HDFS的crud操作
 *
 * 客户端去操作hdfs时，是有一个用户身份的
 * 默认情况下，hdfs客户端api会从jvm中获取一个参数来作为自己的用户身份：
 * -DHADOOP_USER_NAME=hadoop
 *
 * 也可以在构造客户端fs对象时，通过参数传递进去
 *
 */
public class HdfsClient {
    private static final Logger log = LoggerFactory.getLogger(HdfsClient.class);
    FileSystem fileSystem = null;
    Configuration conf = null;
    private static final String HADOOP_USER_NAME = "hadoop";
    private static final String URI = "hdfs://hadoop01:9000";
    private void getFileSystem() throws Exception {
        conf = new Configuration();
//        conf.set("dfs.replication","5");//配置副本数  代码优先级最高
//        conf.set("fs.defaultFS", "hdfs://hadoop01:9000");
//        fileSystem = FileSystem.get(conf);//默认获取一个本地文件系统
        fileSystem = FileSystem.get(new URI(URI),conf,HADOOP_USER_NAME);//最后一个参数为用户名
    }
    public static void main(String[] args) throws Exception {
        HdfsClient hdfsClient = new HdfsClient();
        hdfsClient.getFileSystem();
//        hdfsClient.mkdir();
//        hdfsClient.upload();
//        hdfsClient.ioUpload();
//        hdfsClient.remove();
//        hdfsClient.printConf();
        hdfsClient.testCat();
    }

    public void printConf() {
        Iterator<Map.Entry<String, String>> iterator = conf.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            log.info(next.getKey() + ":" + next.getValue());
        }
    }



    /**
     * 创建目录
     */
    private void mkdir() throws Exception {
        boolean exists = fileSystem.exists(new Path("/demo"));
        if (exists) {
            log.info("目录已经存在");
            return;
        }
        boolean b = fileSystem.mkdirs(new Path("/demo"));
        if (b) {
            log.info("在HDFS系统创建目录成功");
        }else {
            log.info("在HDFS系统创建目录失败");
        }
    }

    /**
     * 上传文件到HDFS
     */
    private void upload() throws Exception {
        fileSystem.copyFromLocalFile(new Path("G:\\学习资料\\1\\a\\tuoyi.mp4"), new Path("hdfs://hadoop01:9000/"));
    }

    /**
     * 上传文件到HDFS
     */
    private void ioUpload() throws Exception {
        FSDataOutputStream outputStream = fileSystem.create(new Path("/upload"),true);
        FileInputStream inputStream = new FileInputStream("C:\\kms10.log");
        IOUtils.copy(inputStream,outputStream );
    }

    private void testCat() throws IOException {
        FSDataInputStream in = fileSystem.open(new Path("/test.txt"));
        FileStatus[] fileStatuses = fileSystem.listStatus(new Path("/test.txt"));
        BlockLocation[] locations = fileSystem.getFileBlockLocations(new Path("/test.txt"), 0L, fileStatuses[0].getLen());
        //第一个block的长度
        long length = locations[0].getLength();
        //第一个block的起始偏移量
        long offset = locations[0].getOffset();
        log.info(length + ":" +offset);
        org.apache.hadoop.io.IOUtils.copyBytes(in,System.out , (int) length);
        byte[] bytes = new byte[4096];
        try (FileOutputStream out = new FileOutputStream("c:\\block0")) {
            while (in.read(offset,bytes ,0 ,4096 ) != -1) {
                out.write(bytes);
                offset += 4096;
                if (offset >= length) {
                    return;
                }
            }
            out.flush();
            out.close();
            in.close();
        }
    }

    /**
     * 删除文件
     */
    private void remove() throws IOException {
        boolean b = fileSystem.deleteOnExit(new Path("/demo"));
//        fileSystem.delete(new Path("/"),true );//递归删除
        if (b) {
            log.info("在HDFS系统删除文件成功");
        }else {
            log.info("在HDFS系统删除文件失败");
        }
    }
}
