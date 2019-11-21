package com.cy.pj.common.aspec;

import java.lang.reflect.Method;
import java.util.Date;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.cy.pj.common.annotation.RequiredLog;
import com.cy.pj.common.util.IPUtils;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.service.SysLogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
@Component
@Slf4j
@Aspect
//@Order(3)
/**用于输出业务开始执行时间，以及业务结束执行时间。*/
public class SysLogAspect {
	/**
	 * 通过@Pointcut注解定义切入点(切入扩展功能的点)
	 */
	//@Pointcut("bean(sysUserServiceImpl)")//开头第一个要小写
	@Pointcut("@annotation(com.cy.pj.common.annotation.RequiredLog)")
	public void logPointCut() {}
	
	/**@Around 注解描述的方法为一个通知方法(功能扩展)，此通知
	 * 我们称之为环绕通知
	 * @param jp 连接点(封装了目标方法信息的一个对象)
	 * @return 目标方法的执行结果
	 * @throws Throwable
	 */
	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint jp)
	throws Throwable{
		try {
			long t1=System.currentTimeMillis();
			
		    Object result=jp.proceed();//调用下一个切面方法或目标方法
		    
		    long t2=System.currentTimeMillis();
			 saveUserLog(jp,(t2-t1));
			return result;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	
	}
	@Autowired
	 private SysLogService sysLogService;
	 private void saveUserLog(ProceedingJoinPoint jp,
			 long time)throws Exception {
		 //1.获取用户行为日志
		 //1.1获取目标方法对象(class,jp)
		 //1.1.1获取方法签名(通过连接点获取)
		 MethodSignature ms=(MethodSignature)jp.getSignature();
		 //1.1.2获取目标类类型
		 Class<?> targetCls=
		 jp.getTarget().getClass();
		 //1.1.3获取目标方法对象
		 Method targetMethod=
		 targetCls.getDeclaredMethod(ms.getName(),
		 ms.getParameterTypes());
		 //1.2获取目标方法对象上RequiredLog注解中定义的操作名
		 String operation="operation";
		 RequiredLog requiredLog=
		 targetMethod.getAnnotation(RequiredLog.class);
		 if(requiredLog!=null) {
			 operation=requiredLog.value();
		 }
		 //1.3获取目标方法对象对应的类名,方法名
		 String targetClsMethod=
		 targetCls.getName()+"."+targetMethod.getName();
		 //1.4获取方法执行的实际参数
		 String params=
		 new ObjectMapper()
		 .writeValueAsString(jp.getArgs());
		 //2.封装日志
		 SysLog log=new SysLog();
		 log.setIp(IPUtils.getIpAddr());
		 log.setUsername("admin");
		 log.setOperation(operation);
		 log.setMethod(targetClsMethod);//com.cy.pj.sys.service.impl.SysUserServiceImpl.findPageObjects
		 log.setParams(params);
		 log.setTime(time);
		 log.setCreatedTime(new Date());
		 //3.将日志存储到数据库
		 sysLogService.saveObject(log);
	 }
}


