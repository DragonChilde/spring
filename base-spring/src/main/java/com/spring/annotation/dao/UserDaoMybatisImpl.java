package com.spring.annotation.dao;

import org.springframework.stereotype.Repository;

/**
 * @author Lee
 * @create 2019/10/16 17:54
 */
@Repository("userDao")
public class UserDaoMybatisImpl implements UserDao{

    public void addUser() {
        System.out.println("UserDao  Mybatis .....");
    }
}
