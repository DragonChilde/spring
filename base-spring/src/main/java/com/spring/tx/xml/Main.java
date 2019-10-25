package com.spring.tx.xml;

import com.spring.tx.xml.server.BookShopService;
import com.spring.tx.xml.server.Cashier;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;

/**
 * @author Lee
 * @create 2019/10/24 14:03
 */
public class Main {



    public static void main(String[] args) {
        //test1();
        test2();
    }

    private static void test1()
    {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-tx-xml.xml");
        BookShopService bean = context.getBean("bookShopServiceImpl", BookShopService.class);
        System.out.println(bean.getClass().getName());
        bean.buyBook("Tom","1001");
    }

    private static void test2()
    {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-tx-xml.xml");
        Cashier bean = context.getBean("cashierImpl", Cashier.class);

        ArrayList<String> list = new ArrayList<>();
        list.add("1001");
        list.add("1002");

        bean.purchase("Tom",list);
    }
}
