package com.dlw.bigdata.mapper;

import com.dlw.bigdata.bean.OrderProduct;
import com.dlw.bigdata.reducer.DistributedCacheReducer;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * author dlw
 * date 2018/9/1.
 */
public class DistributedCacheMapper extends Mapper<LongWritable,Text,Text,OrderProduct> {
    private static final Logger log = LoggerFactory.getLogger(DistributedCacheMapper.class);
    FileReader fr = null;
    BufferedReader br = null;
    Map<String,String> productMap = new HashMap<>();
    String localPath = null;
    String uriPath = null;
    OrderProduct op = new OrderProduct();
    Text text = new Text();
    Date date = new Date();
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String s = value.toString();
        String[] split = s.split(" ");
        int orderId = Integer.parseInt(split[0]);
        try {
            date = DateUtils.parseDate(split[1],new String[]{"yyyy-MM-dd"} );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int productId = Integer.parseInt(split[2]);
        BigDecimal total = BigDecimal.valueOf(Float.parseFloat(split[3]));
        String name = productMap.get(String.valueOf(productId));
        if (StringUtils.isEmpty(name)) {
            return;
        }
        op.setOrderId(orderId);
        op.setDate(date);
        op.setProductId(productId);
        op.setTotal(total);
        op.setName(name);
        text.set(String.valueOf(orderId));
        log.info("map join后的数据：{}",op.toString());
        context.write(text,op );
//         FileSplit fileSplit = (FileSplit) context.getInputSplit();
//        String fileName = fileSplit.getPath().getName();

    }

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {

        fr = new FileReader("G:\\hadoop\\input\\product.txt");
        br = new BufferedReader(fr);
//        Path[] localCacheFiles = context.getLocalCacheFiles();
        URI[] cacheFiles = context.getCacheFiles();
//        localPath = localCacheFiles[0].toString();
        uriPath = cacheFiles[0].toString();
        String line;
        while (null != (line = br.readLine())) {
            String[] split = line.split(" ");
            productMap.put(split[0],split[1]);
        }
        log.info("productMap:{}",productMap);
        IOUtils.closeStream(br);
        IOUtils.closeStream(fr);

    }
}
