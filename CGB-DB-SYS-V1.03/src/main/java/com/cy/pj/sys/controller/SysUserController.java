package com.cy.pj.sys.controller;

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cy.pj.common.util.ShiroUtils;
import com.cy.pj.sys.common.vo.JsonResult;
import com.cy.pj.sys.common.vo.PageObject;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.service.SysUserService;
import com.cy.pj.sys.vo.SysUserDeptVo;

@RestController
@RequestMapping("/user")
public class SysUserController {
	@Autowired
	private SysUserService sysUserService;

	@RequestMapping("doFindPageObjects")
	public JsonResult doFindPageObjects(String username,
			Integer pageCurrent) {
		PageObject<SysUserDeptVo> pageObjects =
				sysUserService.findPageObjects(username, pageCurrent);
		return new JsonResult(pageObjects);

	}

	@RequestMapping("doValidById")
	public JsonResult doValidById(Integer id,Integer valid) {
		sysUserService.validById(id, valid, "admin");
		return new JsonResult("update ok");

	}

	@RequestMapping("doSaveObject")
	public JsonResult doSaveObject(SysUser entity,Integer[] roleIds) {
		sysUserService.saveObject(entity, roleIds);
		return new JsonResult("save ok");
	}

	@RequestMapping("doFindObjectById")
	public JsonResult doFindObjectById(Integer id) {
		Map<String, Object> map = 
				sysUserService.findObjectById(id);
		return new JsonResult(map);

	}

	@RequestMapping("doUpdateObject")
	public JsonResult doUpdateObject(
			SysUser entity,Integer[] roleIds){
		sysUserService.updateObject(entity,
				roleIds);
		return new JsonResult("update ok");
	}
	
	
	/*@RequestMapping("isExists")
	public JsonResult isExists(String username) {
	 boolean flag = sysUserService.isExists(username);
		return new JsonResult(flag);	
	}*/
	
	@RequestMapping("isExists")
	public JsonResult isExists(String columnName, String columnValue) {
		boolean flag = sysUserService.isExists(columnName, columnValue);
		return new JsonResult(flag);
	}
	
	@RequestMapping("doLogin")
	public JsonResult doLogin(
			String username,
			String password,
			boolean isRememberMe) {
		//1.获取subject对象
		Subject subject=SecurityUtils.getSubject();
		//2.通过subject提交用户信息，交给shiro框架进行认证操作
		//2.1对用户进行封装
		UsernamePasswordToken token = new UsernamePasswordToken(
				username,//身份信息
				password);//凭证信息
		if(isRememberMe) {
			token.setRememberMe(true);
		}
		//2.2对用户信息进行身份认证
		subject.login(token);
		//分析
		//1)token会传给shrio的SecurityManager
		//2)SecurityManager将token传递给认证管理器
		//3)认证管理器会将token传递给realm
		//3.返回结果
		return new JsonResult("login ok");
	}
	
	@ExceptionHandler(ShiroException.class)
	@ResponseBody
	public JsonResult doHandleShiroException(
			ShiroException e) {
		JsonResult r=new JsonResult();
		r.setState(0);
		if(e instanceof UnknownAccountException) {
			r.setMessage("账户不存在");
		}else if(e instanceof LockedAccountException) {
			r.setMessage("账户已被禁用");
		}else if(e instanceof IncorrectCredentialsException) {
			r.setMessage("密码不正确");
		}else if(e instanceof AuthorizationException) {
			r.setMessage("没有此操作权限");
		}else {
			r.setMessage("系统维护中");
		}
		e.printStackTrace();
		return r;
	}
	
	//显示登陆用户的第二种方案
	@RequestMapping("doGetLoginUser")
	public JsonResult doGetLoginUser() {
		return new JsonResult(ShiroUtils.getUser());
		
	}
	
	//修改密码
	@RequestMapping("doUpdatePassword")
	public JsonResult doUpdatePassword(
			           String pwd,
			           String newPwd,
			           String cfgPwd) {
		sysUserService.updatePassword(pwd, newPwd, cfgPwd);
						return new JsonResult("update ok");
		
	}
	
	

}
