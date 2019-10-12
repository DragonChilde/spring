package com.spring.helloworld;

import com.spring.helloworld.bean.Person;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Lee
 * @create 2019-09-22 18:40
 */
public class Main {
    public static void main(String[] args) {
        // 获取到Person对象.

        //1. 创建Spring的IOC容器对象
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        //2. 获取Person对象
        //1.根据bean id在applicationContext.xml文件里查找，类型需要进行转换
        //Person person = (Person)ctx.getBean("person");
        /*2.根据class类型进行查找，如果同时定义了多个相同class类型,会报expected single matching com.spring.bean but found 2: person,person2异常*/
        //Person person = ctx.getBean(Person.class);
        /*同时指定bean id和class类型，不需要进行转换*/
        Person person = ctx.getBean("person", Person.class);
        /*注意:getBean是由接口BeanFactory定义的方法*/

        person.sayHello();
    }
}
