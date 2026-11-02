
package com.example.std.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RechargeLoggingAspect {

    @Around("execution(* com.recharge.service..*(..))")
    public Object logExecution(ProceedingJoinPoint joinPoint)
            throws Throwable {

        String methodName = joinPoint.getSignature().getName();

        long startTime = System.currentTimeMillis();

        System.out.println("Started: " + methodName);

        try {

            Object result = joinPoint.proceed();

            System.out.println("Completed: " + methodName);

            return result;

        } catch (Throwable ex) {

            System.out.println("Exception in: " + methodName);
            System.out.println("Error: " + ex.getMessage());

            throw ex;

        } finally {

            long endTime = System.currentTimeMillis();

            long executionTime = endTime - startTime;

            System.out.println(
                    "Execution Time: " + executionTime + " ms");
        }
    }
}
