package com.spring.di.bean;

import java.util.List;

/**
 * @author Lee
 * @create 2019/10/14 11:05
 */
public class PersonList {

    private String  name;

    private List<Car> cars ;    //  Car [] cars;   Set<Car> cars ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    @Override
    public String toString() {
        return "PersonList{" +
                "name='" + name + '\'' +
                ", cars=" + cars +
                '}';
    }
}
