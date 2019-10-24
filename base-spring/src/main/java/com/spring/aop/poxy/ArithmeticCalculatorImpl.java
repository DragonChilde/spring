package com.spring.aop.poxy;

import org.springframework.stereotype.Component;

/**
 * @author Lee
 * @create 2019/10/18 11:23
 */
@Component
public class ArithmeticCalculatorImpl implements ArithmeticCalculator{
    public int add(int i, int j) {
       int result = i + j;
       return result;
    }

    public int sub(int i, int j) {
        int result = i - j;
        return result;
    }

    public int mul(int i, int j) {
        int result = i * j;
        return result;
    }

    public int div(int i, int j) {
        int result = i  / j;
        return result;
    }
}
