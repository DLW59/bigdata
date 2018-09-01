package com.dlw.bigdata.mapper;

import com.dlw.bigdata.bean.Order;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * author dlw
 * date 2018/9/1.
 */
public class DistributedCacheMapper extends Mapper<LongWritable,Text,Text,Text> {

    FileReader fr = null;
    BufferedReader br = null;
    Map<String,String> productMap = new HashMap<>();
    String localPath = null;
    String uriPath = null;

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String s = value.toString();
        String[] split = s.split("\t");
        int orderId = Integer.parseInt(split[0]);
        int amount = Integer.parseInt(split[1]);
        context.write(new Text(String.valueOf(orderId)),new Text(amount+"\t" + ":"
                + localPath + "\t" + productMap.get(orderId)));


    }

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {

        fr = new FileReader("product.txt");
        br = new BufferedReader(fr);
        Path[] localCacheFiles = context.getLocalCacheFiles();
        URI[] cacheFiles = context.getCacheFiles();
        localPath = localCacheFiles[0].toString();
        uriPath = cacheFiles[0].toString();
        String line;
        while (null != (line = br.readLine())) {
            String[] split = line.split(",");
            productMap.put(split[0],split[1]);
        }
        IOUtils.closeStream(br);
        IOUtils.closeStream(fr);

    }
}
