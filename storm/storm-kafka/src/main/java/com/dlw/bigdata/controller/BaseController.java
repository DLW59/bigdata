package com.dlw.bigdata.controller;

import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author dlw
 * @date 2018/9/8
 * @desc
 */
public class BaseController {

    public <T> boolean verifyParams(T t) {
        if (null == t) {
            return true;
        } else if (t instanceof Collection) {
            Collection collection = (Collection) t;
            if (CollectionUtils.isEmpty(collection)) {
                return true;
            }
        } else if (t instanceof Map) {
            Map map = (Map) t;
            if (CollectionUtils.isEmpty(map)) {
                return true;
            }
        } else if (t instanceof Object[]) {
            Object[] objects = (Object[]) t;
            if (objects.length <= 0) {
                return true;
            }
        } else {
            return false;
        }
        return true;
    }
}