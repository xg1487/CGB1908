package com.cy.pj.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cy.pj.common.util.ShiroUtils;
import com.cy.pj.sys.entity.SysUser;

/**
 * 基于controller对象处理项目中所有页面请求
 * 
 * @author UID
 */
@Controller
@RequestMapping("/")
public class PageController {
	/** 返回首页页面 */
	//@RequestMapping("doIndexUI")
	//public String doIndexUI() {
	//	return "starter";

	//}

	/** 返回分页页面 */
	@RequestMapping("doPageUI")
	public String doPageUI() {
		return "common/page";

	}
	@RequestMapping("doLoginUI")
	public String doLoginUI() {
		return "login";
		
	}
	
	/**返回首页页面    还有显示登陆用户的名字 */
	@RequestMapping("doIndexUI")
	public String doIndexUI(Model model) {
		SysUser user=ShiroUtils.getUser();
	    model.addAttribute("user",user);
	   // SysUser username=ShiroUtils.getUsername();
	   // model.addAttribute("username",username);
		return "starter";
	}
	
	

	/** 返回日志列表页面 */
	/*
	 * @RequestMapping("log/log_list") public String doLogUI() { return
	 * "sys/log_list";
	 * 
	 * }
	 * 
	 *//** 返回菜单列表页面 *//*
						 * @RequestMapping("menu/menu_list") public String doMenuUI() { return
						 * "sys/menu_list";
						 * 
						 * }
						 */

	/**
	 * 
	 * @param moduleUI
	 * @return
	 */
	@RequestMapping("{module}/{moduleUI}")
	public String doModuleUI(@PathVariable String moduleUI) {
		return "sys/" + moduleUI;

	}
	

}
