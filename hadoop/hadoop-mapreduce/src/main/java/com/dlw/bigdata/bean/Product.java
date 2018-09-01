package com.dlw.bigdata.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * author dlw
 * date 2018/9/1.
 */
@Data
public class Product {

    private Integer id;
    private String name;
    private BigDecimal price;
    private Integer store;

}
