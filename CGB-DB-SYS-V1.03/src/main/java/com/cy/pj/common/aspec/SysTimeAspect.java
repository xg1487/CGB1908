package com.cy.pj.common.aspec;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
@Order(2)
@Service
@Aspect
public class SysTimeAspect {

	@Pointcut("bean(sysUserServiceImpl)")
	public void doTime() {}
	
	@Before("doTime()")
	public void doBefore(JoinPoint jp) {
		System.out.println("time doBefore()");
	}
	
	@After("doTime()")
	public void doAfter() {
		System.out.println("time doAfter()");
	}
	/**
	 * 核心业务正常结束时执行
	 *  说明：假如after，先执行after,再执行returning
	 */
	@AfterThrowing("doTime()")
	public void doAfterReturning() {
		System.out.println("time doAfterReturning");
	}
	
	@Around("doTime()")
	public Object doAround(ProceedingJoinPoint jp)
	throws Throwable{
		System.out.println("doAround.before");
		
		Object obj=jp.proceed();
		
		System.out.println("doAround.after");
		return obj;
	}
}
