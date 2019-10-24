package com.spring.aop.xml;

import com.spring.aop.poxy.ArithmeticCalculator;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Lee
 * @create 2019/10/21 11:46
 */
public class Main {
    public static void main(String[] args) {
        test1();
    }

    private static void test1()
    {
        ClassPathXmlApplicationContext con = new ClassPathXmlApplicationContext("spring-aop-xml.xml");
        ArithmeticCalculator bean = con.getBean("arithmeticCalculatorImpl", ArithmeticCalculator.class);

       System.out.println(bean.getClass().getName());
        int result = bean.add(2, 2);
        System.out.println("Main result is "+result);


       /* int result2 = bean.div(5, 0);
        System.out.println("Main Result: " + result2 );*/
    }

}
