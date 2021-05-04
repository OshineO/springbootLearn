package com.mfk.ws.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class LogAspect {

    @Pointcut("@annotation(com.mfk.ws.aop.MyLog)")
    public void pointcut () {

    }

    @Before("execution(* com.mfk.ws.aop.TestAop.add*(..)) || pointcut()")
    public void before (JoinPoint joinPoint){
        // 参数值
        Object[] object =  joinPoint.getArgs();
        System.out.println(object);
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        // 参数类型
        Class[] classes = signature.getParameterTypes();
        System.out.println(classes);
        System.out.println("执行了"+method.getName()+"方法");

        MyLog myLog = method.getAnnotation(MyLog.class);
        if (myLog == null) {
            return;
        }
        System.out.println("执行了："+myLog.name());


    }



}
