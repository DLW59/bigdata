package com.dlw.bigdata.reducer;

import com.dlw.bigdata.bean.Flow;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author dlw
 * @date 2018/8/28
 * @desc
 */
public class FlowSortReducer extends Reducer<Flow,Text,Text,Flow> {

    @Override
    protected void reduce(Flow key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        Text phone = values.iterator().next();
        context.write(phone, key);
    }
}
