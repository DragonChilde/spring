package com.spring.lifecycle.bean;

/**
 * @author Lee
 * @create 2019/10/14 17:32
 */
public class Car {
    private String brand ;

    private Double price ;

    public Car() {
        System.out.println("===>1. 调用构造器创建bean对象 ");
    }

    /**
     * 初始化方法
     * 需要通过 init-method来指定初始化方法
     */
    public void init()
    {
        System.out.println("===>3. 调用初始化方法");
    }


    /**
     * 销毁方法： IOC容器关闭， bean对象被销毁.
     */
    public void destory()
    {
        System.out.println("===>5. 调用销毁方法");
    }

    public Car(String brand, Double price) {
        this.brand = brand;
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        System.out.println("===>2. 调用set方法给对象的属性赋值");
        this.brand = brand;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", price=" + price +
                '}';
    }
}
