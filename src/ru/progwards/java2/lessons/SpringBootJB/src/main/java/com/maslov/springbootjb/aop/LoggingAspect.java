package com.maslov.springbootjb.aop;

import lombok.extern.java.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect // пометка о том, что данный класс относится к AOP
@Component // пометка о том, что этот класс является bean
@Log // пометка о том, что данный класс будет заниматься сбором информации - логированием
public class LoggingAspect {

    // аннотация сообщает, что данный метод будет выполняться всегда, когда какой-либо метод из пакета com.maslov.springbootjb.controller будет выполнен
    @Around("execution(* com.maslov.springbootjb.controller..*(..)))")
    public Object profileControllerMethods(ProceedingJoinPoint pjp) throws Throwable {
        // вытаскиваем объект, который будет содержать всю необходимую информацию об отрабатывающем методе.
        MethodSignature ms = (MethodSignature) pjp.getSignature();
        // вытаскиваем имя класса, который в данный момент отрабатывает
        String className = ms.getDeclaringType().getSimpleName();
        // вытаскиваем имя метода, который отрабатывает
        String method = ms.getName();
        // помещаем всю информацию в переменную log, которая создана при помощи аннотации @Log
        log.info(className + "." + method + " is working...");
        // создаём экземпляр объекта для замера времени (из пакета org.springframework.util)
        StopWatch sw = new StopWatch();
        // запускаем секундомер
        sw.start();
        // запускаем работу самого метода
        Object result = pjp.proceed();
        // останавливаем секундомер
        sw.stop();
        log.info("Using time is:" + sw.getTotalTimeMillis() + " milliseconds");
        // возвращаем результат
        return result;
    }
}
