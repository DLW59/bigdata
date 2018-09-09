package com.dlw.bigdata.repository;

import com.dlw.bigdata.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author dlw
 * @date 2018/9/9
 * @desc
 */
@Repository
public interface UserRepository extends JpaRepository<User,Integer>{

}
