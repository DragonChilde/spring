package com.spring.aop.poxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @author Lee
 * @create 2019/10/18 16:35
 */
public class ArithmeticCalculatorProxy2 {
    private ArithmeticCalculator target;

    public ArithmeticCalculatorProxy2(ArithmeticCalculator target) {
        this.target = target;
    }

    public Object getProxy() throws Exception
    {
        Object proxy;


        ClassLoader classLoader = target.getClass().getClassLoader();
        Class<?>[] interfaces = target.getClass().getInterfaces();
        Class<?> proxyClass = Proxy.getProxyClass(classLoader,interfaces);

        Constructor<?> con = proxyClass.getDeclaredConstructor(InvocationHandler.class);

        proxy = con.newInstance(new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //将方法的调用转回到目标对象上.

                //获取方法的名字
                String methodName = method.getName();
                //记录日志
                System.out.println("LoggingProxy: method is "+methodName+"! params is "+ Arrays.asList(args));
                Object result = method.invoke(target,args);     // 目标对象执行目标方法. 相当于执行ArithmeticCalculatorImpl中的+ - * /

                //记录日志
                System.out.println("LoggingProxy: result is "+result);
                return result;
            }
        });


        return proxy;

    }
}
