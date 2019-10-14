package com.spring.relation;

import com.spring.relation.bean.Address;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Lee
 * @create 2019/10/14 14:29
 */
public class Main {
    public static void main(String[] args) {
        test1();
    }

    private static void test1()
    {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("spring-relation.xml");
       /* Address address = classPathXmlApplicationContext.getBean("address", Address.class);
        System.out.println(address);*/
        Address address2 = classPathXmlApplicationContext.getBean("address2", Address.class);
        System.out.println(address2);
    }
}
