package com.cy.pj.common.aspec;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
@Service
@Aspect
@Slf4j
public class SysUserAspect {
	
	@Pointcut("bean(sysLogServiceImpl)")//获取具体类
	public void UserPointCut() {}
	
	@Around("UserPointCut()")
	public Object doAdvice(ProceedingJoinPoint jp) throws Throwable {
		try {
			log.info("start:"+System.currentTimeMillis());
		   Object result = jp.proceed();//调用下一个切面方法或目标方法
			log.info("end:"+System.currentTimeMillis());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		
	}

}
