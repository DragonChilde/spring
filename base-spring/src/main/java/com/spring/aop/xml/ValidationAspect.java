package com.spring.aop.xml;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 验证切面
 *
 */

public class ValidationAspect {

    public void beforeMethod(JoinPoint joinPoint)
    {
        String methodName = joinPoint.getSignature().getName();

        Object[] args = joinPoint.getArgs();

        System.out.println("ValidationAspect==> The method "+methodName + "begin with " + Arrays.asList(args) );
    }

}
