package com.spring.di;

import com.spring.di.bean.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;

/**
 * @author Lee
 * @create 2019-09-22 18:40
 */
public class Main {
    public static void main(String[] args) {
       //test1();
        //test2();
        //test3();
        //test4();
        //test5();
        //tet6();
        //test7();
        //test8();
        test9();
        //test10();
    }

    private static void test1()
    {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-di.xml");
        Car car = applicationContext.getBean("car", Car.class);
        System.out.println(car);
    }

    private static void test2()
    {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-di.xml");
        Car car = applicationContext.getBean("car2", Car.class);
        System.out.println(car);
    }

    private static void test3()
    {
        Class<Car> carClass = Car.class;
        Constructor<?>[] declaredConstructors = carClass.getDeclaredConstructors();
        for (Constructor constructor:declaredConstructors)
        {
            Class[] parameterTypes = constructor.getParameterTypes();
            for (Class type:parameterTypes)
            {
                System.out.print(type.getName()+" ");
            }
            System.out.println();
        }
    }

    private static void test4()
    {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("spring-di.xml");
        Car car = classPathXmlApplicationContext.getBean("car3", Car.class);
        System.out.println(car);
    }

    private static void test5()
    {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("spring-di.xml");
        Book book = classPathXmlApplicationContext.getBean("book", Book.class);
        System.out.println(book);
    }

    private static void tet6()
    {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("spring-di.xml");
        Person person = classPathXmlApplicationContext.getBean("person", Person.class);
        System.out.println(person);
    }

    private static void test7()
    {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("spring-di.xml");
        Person person = classPathXmlApplicationContext.getBean("person1", Person.class);
        System.out.println(person);
    }

    private static void test8()
    {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("spring-di.xml");
        Person person = classPathXmlApplicationContext.getBean("person2", Person.class);
        System.out.println(person);
    }

    private static void test9()
    {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("spring-di.xml");
        PersonList personlist = classPathXmlApplicationContext.getBean("personlist", PersonList.class);
        System.out.println(personlist);
    }

    private static void test10()
    {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("spring-di.xml");
        PersonMap personmap = classPathXmlApplicationContext.getBean("personmap", PersonMap.class);
        System.out.println(personmap);
    }
}
