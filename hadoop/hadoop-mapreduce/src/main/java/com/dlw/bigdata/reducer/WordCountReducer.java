package com.dlw.bigdata.reducer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dlw
 * @date 2018/8/28
 * @desc
 * KEYIN, VALUEIN 对应  mapper输出的KEYOUT,VALUEOUT类型对应
 *
 * KEYOUT, VALUEOUT 是自定义reduce逻辑处理结果的输出数据类型
 * KEYOUT是单词
 * VLAUEOUT是总次数
 */
public class WordCountReducer extends Reducer<Text,IntWritable,Text,IntWritable> {

    /**
     * 入参key，是一组相同单词kv对的key
     * @param key
     * @param values
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int count = 0;
        for (IntWritable value : values) {
            count += value.get();
        }
        context.write(key,new IntWritable(count) );
    }
}
