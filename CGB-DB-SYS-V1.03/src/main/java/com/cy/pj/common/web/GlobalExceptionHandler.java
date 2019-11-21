package com.cy.pj.common.web;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.cy.pj.sys.common.vo.JsonResult;

/**
 * @ExceptionHandler:用于描述异常处理方法，注解内部
 * 的异常类型，为方法能处理的异常类型
 * @author UID
 *@ControllerAdvice：注解描述的类叫异常处理类，此类中可以定义多个异常
 *处理方法
 *@RestControllerAdvice等价于@ControllerAdvice @ResponseBody
 */
//@ControllerAdvice
//@ResponseBody
@RestControllerAdvice
public class GlobalExceptionHandler {
	//JDK中的自带的日志API
		@ExceptionHandler(RuntimeException.class)
	public JsonResult doHandleRuntimeException(RuntimeException e) {
		e.printStackTrace();//也可以写日志
		return new JsonResult(e);//封装异常信息;
		
	}
		
}
