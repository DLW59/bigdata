package com.dlw.bigdata.bean;

import lombok.*;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author dlw
 * @date 2018/8/28
 * @desc 流量
 */
@Data
@AllArgsConstructor
public class Flow implements WritableComparable<Flow>{
    /**
     * 上行流量
     */
    private long up;
    /**
     * 下行流量
     */
    private long down;
    /**
     * 总流量
     */
    private long total;

    private Flow() {}

    public static Flow newInstance() {
        return new Flow();
    }
    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(up);
        dataOutput.writeLong(down);
        dataOutput.writeLong(total);
    }

    /**
     * 反序列化与序列化顺序一致
     * @param dataInput
     * @throws IOException
     */
    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.up = dataInput.readLong();
        this.down = dataInput.readLong();
        this.total = dataInput.readLong();
    }

    @Override
    public int compareTo(Flow flow) {
        return Long.compare(flow.total, this.total);
    }

    @Override
    public String toString() {
        return "上行：" + up +
                ", 下行：" + down +
                ", 总共：" + total;
    }
}
