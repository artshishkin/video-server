package com.artarkatesoft.videomonitoring.videoserver.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class MethodExecutionTimeAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around("@annotation(com.artarkatesoft.videomonitoring.videoserver.aop.TrackTime)")
    public Object calculateExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long timeTaken = System.currentTimeMillis() - startTime;
        logger.info("Time Taken by {} is {} ms", joinPoint, timeTaken);
        return result;
    }


}
