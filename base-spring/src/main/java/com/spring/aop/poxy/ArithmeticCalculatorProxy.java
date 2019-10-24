package com.spring.aop.poxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * 生成代理对象。
 *
 * JDK的动态代理:
 * 	 1. Proxy : 是所有动态代理类的父类， 专门用户生成代理类或者是代理对象
 * 		 	public static Class<?> getProxyClass(ClassLoader loader,
 Class<?>... interfaces)
 用于生成代理类的Class对象.

 * 			public static Object newProxyInstance(ClassLoader loader,
 Class<?>[] interfaces,
 InvocationHandler h)
 用于生成代理对象

 *   2. InvocationHandler :完成动态代理的整个过程.
 *   		public Object invoke(Object proxy, Method method, Object[] args)
 throws Throwable;
 *
 */

public class ArithmeticCalculatorProxy {

    //动态代理:    目标对象     如何获取代理对象      代理要做什么

    //目标对象
    private ArithmeticCalculator target;

    public ArithmeticCalculatorProxy(ArithmeticCalculator target) {
        this.target = target;
    }

    //获取代理对象的方法
    public Object getProxy()
    {
        //代理对象
        Object proxy;

        /**
         * loader:  ClassLoader对象。 类加载器对象.  帮我们加载动态生成的代理类。
         *
         * interfaces: 接口们.  提供目标对象的所有的接口.  目的是让代理对象保证与目标对象都有接口中想同的方法.
         *
         * h:  InvocationHandler类型的对象.
         */

        ClassLoader classLoader = target.getClass().getClassLoader();
        Class<?>[] interfaces = target.getClass().getInterfaces();

        proxy = Proxy.newProxyInstance(classLoader, interfaces, new InvocationHandler() {
            /**
             * invoke:  代理对象调用代理方法， 会回来调用invoke方法。
             *
             * proxy: 代理对象 ， 在invoke方法中一般不会使用.
             *
             * method: 正在被调用的方法对象.
             *
             * args:   正在被调用的方法的参数.
             */
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                //将方法的调用转回到目标对象上.

                //获取方法的名字
                String methodName = method.getName();
                //记录日志
                System.out.println("LoggingProxy: method is "+methodName+"! params is "+ Arrays.asList(args));
                Object result = method.invoke(target,args); // 目标对象执行目标方法. 相当于执行ArithmeticCalculatorImpl中的+ - * /

                //记录日志
                System.out.println("LoggingProxy: result is "+result);
                return result;
            }
        });

        return proxy;
    }
}

/**
 * 模拟底层生成的动态代理类
 */

class $Proxy0 extends Proxy implements ArithmeticCalculator{
    public $Proxy0(InvocationHandler h) {
        super(h);
    }

    public int add(int i, int j) {
        //super.h.invoke(this,方法对象,方法参数)
        return 0;
    }

    public int sub(int i, int j) {
        return 0;
    }

    public int mul(int i, int j) {
        return 0;
    }

    public int div(int i, int j) {
        return 0;
    }
}