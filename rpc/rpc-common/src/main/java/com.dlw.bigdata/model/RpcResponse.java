package com.dlw.bigdata.model;

import com.dlw.bigdata.enums.RpcResponseCode;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dlw
 * @date 2018/8/18
 * @desc
 */
@Data
@NoArgsConstructor
public class RpcResponse {

    private int status = RpcResponseCode.SUCCESS.code;
    private String msg = RpcResponseCode.SUCCESS.msg;
    private Object data;
}
