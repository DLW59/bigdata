package com.dlw.bigdata.queue.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * author dlw
 * date 2018/10/1.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable ,Cloneable{

    private String id;
    private String content;


}
