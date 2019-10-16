package com.spring.annotation.dao;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * @author Lee
 * @create 2019/10/16 10:30
 */
@Repository
public class UserDaoImpl implements UserDao{

    public void addUser() {
        System.out.println("UserDao operation...........");
    }
}
