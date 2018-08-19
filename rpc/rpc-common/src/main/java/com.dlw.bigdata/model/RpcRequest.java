package com.dlw.bigdata.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dlw
 * @date 2018/8/18
 * @desc 请求传输对象
 */
@Data
@Builder
public class RpcRequest {

    private String requestId;
    private String className;
    private String methodName;
    private Class<?>[] paramsType;
    private Object[] params;

}
