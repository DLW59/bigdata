package com.dlw.bigdata.queue.mq;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * author dlw
 * date 2018/10/1.
 */
@Data
@AllArgsConstructor
public class Message implements Serializable {

    private String id;
    private String content;
}
