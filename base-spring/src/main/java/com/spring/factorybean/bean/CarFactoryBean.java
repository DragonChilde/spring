package com.spring.factorybean.bean;

import com.spring.di.bean.Car;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author Lee
 * @create 2019/10/14 14:02
 */
public class CarFactoryBean implements FactoryBean<Car> {

    /**
     * 工厂bean具体创建的bean对象是由getObject方法来返回的.
     */
    public Car getObject() throws Exception {
        return  new Car("五菱宏光", "五菱", 50000);
    }

    /**
     *  返回具体的bean对象的类型
     */
    public Class<?> getObjectType() {
        return Car.class;
    }

    /**
     * bean 可以是单例的   也可以是原型的(非单例)： 后面讲bean的作用域再研究.
     */
    public boolean isSingleton() {
        return true;
    }
}
