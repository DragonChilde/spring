package com.spring.factorybean;

import com.spring.di.bean.Car;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Lee
 * @create 2019/10/14 14:02
 */
public class Main {
    public static void main(String[] args) {
        test1();
    }

    private static void test1()
    {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("spring-factorybean.xml");
        Car car = classPathXmlApplicationContext.getBean("car", Car.class);
        System.out.println(car);
    }
}
