package com.dlw.bigdata.driver;

import com.dlw.bigdata.mapper.WordCountMapper;
import com.dlw.bigdata.reducer.WordCountReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author dlw
 * @date 2018/8/28
 * @desc
 *  相当于一个yarn集群的客户端
 * 需要在此封装我们的mr程序的相关运行参数，指定jar包
 * 最后提交给yarn
 */
public class WordCountDriver {

    private static final String INPUT = "hdfs://hadoopname:9000/wordcount/input/Stock.txt";
    private static final String OUTPUT = "hdfs://hadoopname:9000/wordcount/output";
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
//        job.setJar("/home/hadoop/wc.jar");
        //指定本程序的jar包所在的本地路径
        job.setJarByClass(WordCountDriver.class);

        //指定本业务job要使用的mapper/Reducer业务类
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);

        //指定mapper输出数据的kv类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        //指定最终输出的数据的kv类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        //指定job的输入原始文件所在目录
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        //指定job的输出结果所在目录
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        //将job中配置的相关参数，以及job所用的java类所在的jar包，提交给yarn去运行
//        job.submit();
        FileSystem fileSystem = FileSystem.get(conf);
        Path path = new Path(args[1]);
        if (fileSystem.exists(path)) {
//            Path path1 = new Path(OUTPUT + "_" + System.currentTimeMillis());
//            fileSystem.mkdirs(path1);
//            FSDataInputStream fis = fileSystem.open(path);
//            FileOutputStream fos = new FileOutputStream(path1.getParent() + "/" + path1.getName());
////            FSDataOutputStream out = new FSDataOutputStream(fos, new FileSystem.Statistics("hdfs"));
//            int len = 4096;
//            byte[] bytes = new byte[len];
//            while (fis.read() != -1) {
//                fos.write(bytes, 0, len);
//                fos.flush();
//                fos.close();
//                fis.close();
//            }
            fileSystem.delete(path,true);
        }
        boolean res = job.waitForCompletion(true);
        System.exit(res?0:1);
    }
}
