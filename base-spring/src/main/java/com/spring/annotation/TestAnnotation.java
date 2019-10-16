package com.spring.annotation;

import com.spring.annotation.controller.UserController;
import com.spring.annotation.dao.UserDao;
import com.spring.annotation.dao.UserDaoImpl;
import com.spring.annotation.service.UserService;
import com.spring.annotation.service.UserServiceImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Lee
 * @create 2019/10/16 10:32
 */
public class TestAnnotation {
    public static void main(String[] args) {
        test1();
    }

    private static void test1()
    {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("spring-annotation.xml");
        UserController userController = classPathXmlApplicationContext.getBean("userController", UserController.class);
        System.out.println(userController);

      /* UserService userServiceImpl = classPathXmlApplicationContext.getBean("userServiceImpl", UserServiceImpl.class);
        System.out.println(userServiceImpl);

        UserDao userDao = classPathXmlApplicationContext.getBean("userDaoImpl", UserDaoImpl.class);
        System.out.println(userDao);*/

        userController.regist();
    }
}
