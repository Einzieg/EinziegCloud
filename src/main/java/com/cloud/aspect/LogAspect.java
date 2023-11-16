package com.cloud.aspect;

import com.cloud.service.ILogService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect // 使得该切面类成为一个切面
@Component  // 使得该切面类被Spring扫描到
public class LogAspect {

	private static Long currentTime;

	@Resource
	ILogService logService;

	/**
	 * 设置切入点
	 */
	@Pointcut("execution(public * com.cloud.controller.*.*(..))")
	public void pointcut() {
	}

	/**
	 * 在方法执行时进行通知
	 */
	@Around("pointcut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		currentTime = System.currentTimeMillis();
		Object o = joinPoint.proceed();
		logService.saveLog(joinPoint, "success", currentTime, o);
		log.info("执行方法 " + joinPoint.getSignature().getName() + " 后置环绕通知");
		return o;
	}

	/**
	 * 在方法执行之前进行通知
	 */
	@Before("pointcut()")
	public void before(JoinPoint joinPoint) {
		log.info("执行方法 " + joinPoint.getSignature().getName() + " 前置通知");
	}

	/**
	 * 在方法执行之后返回结果时进行通知
	 */
	@AfterReturning(returning = "result", pointcut = "pointcut()")
	public void afterReturning(JoinPoint joinPoint, Object result) {
		log.info("执行方法 " + joinPoint.getSignature().getName() + " 返回通知，返回值：" + result);
	}

	/**
	 * 在方法执行之后进行通知
	 */
	@After("pointcut()")
	public void after(JoinPoint joinPoint) {
		log.info("执行方法 " + joinPoint.getSignature().getName() + " 后置通知");
	}

	/**
	 * 在方法抛出异常时进行通知
	 */
	@AfterThrowing(throwing = "ex", pointcut = "pointcut()")
	public void afterThrowing(JoinPoint joinPoint, Exception ex) {
		logService.saveLog((ProceedingJoinPoint) joinPoint, "error", currentTime, ex);
		log.error("执行方法 " + joinPoint.getSignature().getName() + " 异常通知，异常：" + ex.getMessage());
	}

}
