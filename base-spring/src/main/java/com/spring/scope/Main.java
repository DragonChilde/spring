package com.spring.scope;

import com.spring.di.bean.Car;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Lee
 * @create 2019/10/14 17:13
 */
public class Main {
    public static void main(String[] args) {
        test1();
    }

    private static void test1()
    {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("spring-scope.xml");
        Car car1 = classPathXmlApplicationContext.getBean("car", Car.class);
        Car car2 = classPathXmlApplicationContext.getBean("car", Car.class);
        System.out.println(car1==car2);
    }
}
