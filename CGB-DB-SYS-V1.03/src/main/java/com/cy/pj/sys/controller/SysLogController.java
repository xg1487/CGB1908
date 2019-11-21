package com.cy.pj.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cy.pj.sys.common.vo.JsonResult;
import com.cy.pj.sys.common.vo.PageObject;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.service.SysLogService;

/**
 * 日志控制层对象 核心业务 1)请求数据处理(请求路径，请求方式，请求参数) 2)响应数据处理(数据封装，数据转换)
 * 
 * @author UID
 *
 */
//@Controller
@RequestMapping("/log/")
//@ResponseBody
@RestController // 等价于@Controller和@ResponseBody
public class SysLogController {

	@Autowired
	private SysLogService sysLogService;

	/**
	 * 
	 * @param username
	 * @param pageCurrent
	 * @return 说明：假如对方法参数有更高要求可以使用
	 * @RequestParam注解对参数进行描述 @GetMappering("doFindPageObjects"):表示的是get请求
	 */
	@RequestMapping("doFindPageObjects")
	public JsonResult doFindPageObjects(String username, Integer pageCurrent) {
		PageObject<SysLog> pageObject = sysLogService.findPageObjects(username, pageCurrent);
		return new JsonResult(pageObject);
	}// spring mvc 会借助jackson api将结果转换为json格式的字符串

	@ExceptionHandler(RuntimeException.class)
	public JsonResult doHandleRuntimeException(RuntimeException e) {
		e.printStackTrace();// 也可以写日志
		return new JsonResult(e);// 封装异常信息;

	}

	@RequestMapping("doDeleteObjects")
	public JsonResult doDeleteObjects(Integer... ids) {
		sysLogService.deleteObjects(ids);
		return new JsonResult("delete ok");
	}

}