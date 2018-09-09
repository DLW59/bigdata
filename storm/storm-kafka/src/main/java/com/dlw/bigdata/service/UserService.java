package com.dlw.bigdata.service;

import com.dlw.bigdata.domain.User;

import java.util.Collection;
import java.util.List;

/**
 * @author dlw
 * @date 2018/9/9
 * @desc
 */
public interface UserService  extends BaseService<User> {

    @Override
    void add(User user);

    @Override
    void batchAdd(List<User> users);

    @Override
    Collection<User> listAll();

    @Override
    User getById(int id);
}
