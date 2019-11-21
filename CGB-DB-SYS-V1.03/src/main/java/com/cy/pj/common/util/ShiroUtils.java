package com.cy.pj.common.util;
import org.apache.shiro.SecurityUtils;
import com.cy.pj.sys.entity.SysUser;
public class ShiroUtils {
	 /**获取登录用户*/
	public static String getUsername() {
		return getUser().getUsername();
	}
	public static SysUser getUser() {
		return (SysUser)
		 SecurityUtils.getSubject().getPrincipal();
	}
}





