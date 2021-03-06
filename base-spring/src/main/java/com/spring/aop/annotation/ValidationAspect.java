package com.spring.aop.annotation;

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
@Component
@Aspect
@Order(1)
public class ValidationAspect {

    //@Before("execution(* com.spring.aop.poxy.*.*(..))")
    @Before("LoggingAspect.declarePointCut()")
    public void beforeMethod(JoinPoint joinPoint)
    {
        String methodName = joinPoint.getSignature().getName();

        Object[] args = joinPoint.getArgs();

        System.out.println("ValidationAspect==> The method "+methodName + "begin with " + Arrays.asList(args) );
    }

}
