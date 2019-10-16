package com.spring.autowire;

import com.spring.autowire.bean.Person;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Lee
 * @create 2019/10/15 17:09
 */
public class TestAutoWire {
    public static void main(String[] args) {
        test1();
    }

    private static void test1()
    {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("spring-autowire.xml");
        Person person = classPathXmlApplicationContext.getBean("person", Person.class);
        System.out.println(person);
    }
}
