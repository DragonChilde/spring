package com.spring.datasource;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;

/**
 * @author Lee
 * @create 2019/10/15 10:43
 */
public class TestDataSource {
    public static void main(String[] args) throws Exception {
        test1();
    }

    private static void test1() throws Exception
    {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("spring-datasource.xml");
        DataSource datasource = classPathXmlApplicationContext.getBean("datasource", DataSource.class);
        System.out.println(datasource);
        System.out.println(datasource.getConnection());
    }
}
