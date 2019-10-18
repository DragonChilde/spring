package com.spring.aop.poxy;

import java.lang.reflect.Proxy;
import java.util.Properties;

/**
 * @author Lee
 * @create 2019/10/18 11:45
 */
public class Main {
    public static void main(String[] args) throws Exception {
        test1();
    }

    private static void test1() throws Exception
    {

      /*  Properties properties = System.getProperties();
        properties.put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");*/
        //目标对象
        ArithmeticCalculator target = new ArithmeticCalculatorImpl();

        ArithmeticCalculatorProxy2 arithmeticCalculatorProxy = new ArithmeticCalculatorProxy2(target);
        //获取代理对象
        Object proxy = arithmeticCalculatorProxy.getProxy();
        // 转回具体的类型.
        ArithmeticCalculator arithmeticCalculator = (ArithmeticCalculator) proxy;
        int result = arithmeticCalculator.add(1, 2);
        System.out.println("result is "+result);

        System.out.println(arithmeticCalculator.getClass().getName());
    }
}
