package com.dlw.bigdata.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author dengliwen
 * @date 2019/1/22
 */
@Data
public class Log implements Serializable {

    private static final long serialVersionUID = -3064852848934741730L;

    public Timestamp createTime;
    public String logId;
    public LogTypeEnum logType;


    public enum LogTypeEnum{

        LOGIN,LOGOUT,OPT;
    }


}
