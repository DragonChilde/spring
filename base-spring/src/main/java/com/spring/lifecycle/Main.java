package com.spring.lifecycle;

import com.spring.lifecycle.bean.Car;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Lee
 * @create 2019/10/14 17:35
 */
public class Main {
    public static void main(String[] args) {
        test1();
    }

    private static void test1()
    {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring-lifecycle.xml");
        Car car = context.getBean("car", Car.class);
        System.out.println("===>4. 使用bean对象" + car);
        //关闭容器

        context.close();
    }
}
