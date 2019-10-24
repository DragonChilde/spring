package com.spring.aop.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;


/**
 * 日志切面
 */
@Component  //标识为一个组件
@Aspect      //标识为一个切面
@Order(2)
public class LoggingAspect {

    @Pointcut("execution(* com.spring.aop.poxy.*.*(..))")
    public void declarePointCut(){}

    /**
     * 前置通知: 在目标方法(连接点)执行之前执行.
     */
    @Before("execution(public int com.spring.aop.poxy.ArithmeticCalculatorImpl.add(int,int))")
    public void beforeMethod(JoinPoint joinPoint)
    {
        //获取方法的名字
        String methodName = joinPoint.getSignature().getName();
        //获取参数
        Object[] paramsArray = joinPoint.getArgs();

        System.out.println("LoggingAspect==> The method is "+methodName+",params is "+ Arrays.asList(paramsArray));
    }

    /**
     * 后置通知: 在目标方法执行之后执行， 不管目标方法有没有抛出异常.  不能获取方法的结果
     *    * com.atguigu.spring.aspectJ.annotation.*.*(..)
     *    * : 任意修饰符 任意返回值
     *    * : 任意类
     *    * : 任意方法
     *    ..: 任意参数列表
     *
     * 连接点对象: JoinPoint
     */
    //@After("execution(* com.spring.aop.poxy.*.*(..))")
    @After("declarePointCut()")
    public void afterMethod(JoinPoint joinPoint)
    {
        //获取方法的名字
        String methodName = joinPoint.getSignature().getName();

        System.out.println("LoggingAspect==> The method "+methodName+" is endding");
    }

    /**
     * 返回通知: 在目标方法正常执行结束后执行.  可以获取到方法的返回值.
     *
     * 获取方法的返回值: 通过returning 来指定一个名字， 必须要与方法的一个形参名一致.
     */

    //@AfterReturning(value = "execution(* com.spring.aop.poxy.*.*(..))",returning = "result")
    @AfterReturning(value = "declarePointCut()", returning = "result")
    public void afterReturningMethod(JoinPoint joinPoint,Object result)
    {
        //方法的名字
        String methodName = joinPoint.getSignature().getName();

        System.out.println("LoggingAspect==> The method "+methodName+" is afterReturningMethod,return result is "+result);
    }

    /**
     * 异常通知: 在目标方法抛出异常后执行.
     *
     * 获取方法的异常: 通过throwing来指定一个名字， 必须要与方法的一个形参名一致.
     *
     * 可以通过形参中异常的类型 来设置抛出指定异常才会执行异常通知.
     *
     */
    @AfterThrowing(value = "execution(* com.spring.aop.poxy.*.*(..))",throwing = "ex")
    public void afterThrowingMethod(JoinPoint joinPoint, Exception ex)
    {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("LoggingAspect==> The method "+methodName+" is afterThrowingMethod,throw exception is "+ex);
    }


    /**
     * 环绕通知: 环绕着目标方法执行. 可以理解是 前置 后置 返回  异常 通知的结合体，更像是动态代理的整个过程.
     */
    @Around("execution(* com.spring.aop.poxy.*.*(..))")
    public Object roundMethod(ProceedingJoinPoint pjp)
    {
        //执行目标方法
        try {
            //前置
            String name = pjp.getSignature().getName();
            System.out.println("round before is "+name);

            //返回
            Object result = pjp.proceed();
            System.out.println("round return is "+ result);
            return result;
        }catch (Throwable e){
            //异常通知
            e.printStackTrace();
        }finally {
            // 后置
            System.out.println("round endding!");
        }
        return null;
    }
}
