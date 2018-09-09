package com.dlw.bigdata.service;

import java.util.Collection;
import java.util.List;

/**
 * @author dlw
 * @date 2018/9/9
 * @desc
 */
public interface BaseService<T> {

    /**
     * 添加
     * @param t
     */
     void add(T t);

    /**
     * 批量添加
     * @param t
     */
     void batchAdd(List<T> t);

    /**
     * 查询所有
     * @return
     */
    Collection<T> listAll();

    /**
     * 根据id查询
     * @param id
     * @return
     */
    T getById(int id);
}
