package com.dlw.bigdata.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

/**
 * author dlw
 * date 2018/9/1.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProduct implements Writable{

    //订单id
    private Integer orderId;
    private Date date;
    //产品id
    private Integer productId;
    //总价
    private BigDecimal total;
    //产品名称
    private String name;
    //价格
//    private BigDecimal price;
    //库存
//    private Integer store;
    //0表示订单数据 1表示商品数据
//    private Integer flag;

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.write(orderId);
        dataOutput.writeLong(date.getTime());
        dataOutput.write(productId);
        dataOutput.writeFloat(total.floatValue());
        dataOutput.writeUTF(name);
//        dataOutput.writeFloat(price.floatValue());
//        dataOutput.write(store);
//        dataOutput.write(flag);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        orderId = dataInput.readInt();
        date.setTime(dataInput.readLong());
        productId = dataInput.readInt();
        total= BigDecimal.valueOf(dataInput.readFloat());
        name = dataInput.readUTF();
//        price= BigDecimal.valueOf(dataInput.readFloat());
//        store = dataInput.readInt();
//        flag = dataInput.readInt();
    }
}
