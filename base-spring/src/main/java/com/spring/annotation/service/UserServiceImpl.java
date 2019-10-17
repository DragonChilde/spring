package com.spring.annotation.service;

import com.spring.annotation.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author Lee
 * @create 2019/10/16 10:31
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired(required = false)
    @Qualifier("userDao")
    private UserDao userDao;

   /* @Autowired
    @Qualifier("userDao")
    public void setUserDao(UserDao userDao)
    {
        this.userDao = userDao;
    }*/


    public void handleAddUser() {
        userDao.addUser();
    }
}
