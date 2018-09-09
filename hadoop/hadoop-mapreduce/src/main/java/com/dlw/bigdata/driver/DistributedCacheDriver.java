package com.dlw.bigdata.driver;

import com.dlw.bigdata.bean.Flow;
import com.dlw.bigdata.bean.OrderProduct;
import com.dlw.bigdata.enums.CalWay;
import com.dlw.bigdata.mapper.DistributedCacheMapper;
import com.dlw.bigdata.mapper.FlowMapper;
import com.dlw.bigdata.reducer.DistributedCacheReducer;
import com.dlw.bigdata.reducer.FlowReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;


/**
 * author dlw
 * date 2018/9/1.
 * 分布式缓存应用
 */
public class DistributedCacheDriver {
    private static final Logger log = LoggerFactory.getLogger(DistributedCacheDriver.class);
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        conf.set("mapreduce.framework.name","local");
        conf.set("fs.defaultFS","file:///");

        Job job = Job.getInstance(conf);
        job.setJarByClass(DistributedCacheDriver.class);
        //指定本业务job要使用的mapper/Reducer业务类
        job.setMapperClass(DistributedCacheMapper.class);
        job.setReducerClass(DistributedCacheReducer.class);
        URI uri = URI.create("hdfs://hadoop01:9000/cache/input/product.txt");
        job.setCacheFiles(new URI[]{uri});
        //指定mapper输出数据的kv类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(OrderProduct.class);

        //指定最终输出的数据的kv类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(OrderProduct.class);
        //如果不设置默认TextInputFormat
        job.setInputFormatClass(CombineTextInputFormat.class);
        CombineTextInputFormat.setMinInputSplitSize(job,1024*1024*2);//2M
        CombineTextInputFormat.setMaxInputSplitSize(job,1024*1024*10);//10M
        //指定job的输入原始文件所在目录
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        //指定job的输出结果所在目录
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        //将job中配置的相关参数，以及job所用的java类所在的jar包，提交给yarn去运行

        FileSystem fileSystem = FileSystem.get(conf);
        Path path = new Path(args[1]);
        if (fileSystem.exists(path)) {
            fileSystem.delete(path,true);
        }
        boolean res = job.waitForCompletion(true);
        System.exit(res ? 0 : 1);

    }
}
