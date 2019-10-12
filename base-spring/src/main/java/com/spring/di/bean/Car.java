package com.spring.di.bean;

/**
 * @author Lee
 * @create 2019/10/11 17:44
 */
public class Car {
    private String brand ;   // 品牌
    private String crop ;    // 厂商
    private Double price ;   // 价格

    private Integer speed ;  // 速度

    public Car() {
    }

    public Car(String brand, String crop, Double price) {
        this.brand = brand;
        this.crop = crop;
        this.price = price;
    }

    public Car(String brand, String crop, Integer speed) {
        this.brand = brand;
        this.crop = crop;
        this.speed = speed;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        System.out.println("brand is "+brand);
        this.brand = brand;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        System.out.println("crop is "+crop);
        this.crop = crop;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        System.out.println("price is "+price);
        this.price = price;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", crop='" + crop + '\'' +
                ", price=" + price +
                ", speed=" + speed +
                '}';
    }
}
