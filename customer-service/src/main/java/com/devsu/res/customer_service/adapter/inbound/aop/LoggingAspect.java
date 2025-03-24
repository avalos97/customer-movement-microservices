package com.devsu.res.customer_service.adapter.inbound.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Before("within(com.devsu.res.customer_service.adapter.inbound.controller..*)")
    public void logBefore(JoinPoint joinPoint) {
        log.info("▶️ Inicio de método: {} con argumentos: {}", joinPoint.getSignature(), joinPoint.getArgs());
    }
    
    @AfterReturning(pointcut = "within(com.devsu.res.customer_service.adapter.inbound.controller..*)", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        log.info("✅ Fin de método: {} con resultado: {}", joinPoint.getSignature(), result);
    }
}
