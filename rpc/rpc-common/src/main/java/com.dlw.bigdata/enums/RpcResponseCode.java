package com.dlw.bigdata.enums;

/**
 * @author dlw
 * @date 2018/8/18
 * @desc
 */
public enum RpcResponseCode {
    SUCCESS(0,"成功"),FAIL(1,"失败"),ERROR(-1,"异常");

    RpcResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int code;
    public String msg;


}
