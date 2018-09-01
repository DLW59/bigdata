package com.dlw.bigdata.bean;

import lombok.Data;

import java.util.Date;

/**
 * author dlw
 * date 2018/9/1.
 */
@Data
public class Order {

    private Integer id;
    private Date date;
    private Integer productId;
    private Integer amount;

}
