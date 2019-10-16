package com.spring.annotation.controller;

import com.spring.annotation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @Cotroller 注解的作用:
 * 相当于在xml文件中:
 * <bean id="userController" class="com.atguigu.spring.annotation.controller.UserController">
 *
 * 注解默认的id值 就是类名首字母小写， 可以在注解中手动指定id值:@Controller(value="id值"),可以简写为:@Controller("id值")
 *
 *
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    public void regist()
    {
        userService.handleAddUser();
    }
}
