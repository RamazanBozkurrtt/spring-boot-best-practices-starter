package org.project.bestpractice.aspect;


import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.project.bestpractice.BestPracticeApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {


    @Pointcut("execution(* org.project.bestpractice.controller..*(..)) || " +
              "execution(* org.project.bestpractice.service..*(..)) || " +
              "execution(* org.project.bestpractice.repository..*(..))")
    public void appPackagePointCut(){

    }

    @Around("appPackagePointCut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        try{
            log.info("Enter : {}.{} with argument[s]={}",
                    className, methodName, joinPoint.getArgs());
            Object result = joinPoint.proceed();

            long elapsedTime = System.currentTimeMillis() - startTime;
            log.info("Exit : {}.{} with result {} (Duration: {} ms)",
                    className, methodName, result, elapsedTime);
            return result;

        }catch(IllegalArgumentException e){
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                    className, methodName);
            throw e;
        }
    }

    @AfterThrowing(pointcut = "appPackagePointCut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("Exception in {}.{}() with cause = {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                e.getCause() != null ? e.getCause() : "NULL");
    }



}
