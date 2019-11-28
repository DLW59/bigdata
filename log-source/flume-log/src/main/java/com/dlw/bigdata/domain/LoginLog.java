package com.dlw.bigdata.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author dengliwen
 * @date 2019/1/22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginLog {
    private static final long serialVersionUID = 3267080830068206499L;

    private String username;
    private String ip;
    private String phone;
    private String sex;

    public Timestamp createTime;
    public String logId;
    public Log.LogTypeEnum logType;


    public enum LogTypeEnum{

        LOGIN,LOGOUT,OPT;
    }

}
