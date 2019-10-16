package com.spring.annotation.service;

import com.spring.annotation.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Lee
 * @create 2019/10/16 10:31
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    public void handleAddUser() {
        userDao.addUser();
    }
}
