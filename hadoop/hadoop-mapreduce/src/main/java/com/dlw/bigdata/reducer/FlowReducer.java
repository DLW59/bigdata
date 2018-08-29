package com.dlw.bigdata.reducer;

import com.dlw.bigdata.bean.Flow;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author dlw
 * @date 2018/8/28
 * @desc
 */
public class FlowReducer extends Reducer<Text,Flow,Text,Flow> {

    @Override
    protected void reduce(Text key, Iterable<Flow> values, Context context) throws IOException, InterruptedException {
        long up = 0L;
        long down = 0L;
        for (Flow value : values) {
             up += value.getUp();
             down += value.getDown();
        }
        context.write(key,new Flow(up,down,up + down));
    }
}
