package com.dlw.bigdata.service.impl;

import com.dlw.bigdata.domain.User;
import com.dlw.bigdata.repository.UserRepository;
import com.dlw.bigdata.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author dlw
 * @date 2018/9/9
 * @desc
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepository userRepository;

    @Override
    public void add(User user) {
        log.info("要添加的用户:{}",user);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void batchAdd(List<User> users) {
        log.info("要添加的用户:{}",users);
        userRepository.saveAll(users);
    }

    @Override
    public Collection<User> listAll() {
        List<User> users = userRepository.findAll();
        log.info("所有用户:{}",users);
        return users;
    }

    @Override
    public User getById(int id) {
        Optional<User> user = userRepository.findById(id);
        log.info("单个用户:{}",user);
        return user.orElse(null);
    }
}
