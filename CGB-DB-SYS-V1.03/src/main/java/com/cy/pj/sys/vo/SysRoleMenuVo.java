package com.cy.pj.sys.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
/** VO:通过此对象封装角色以及角色对应的菜单id
 * @author UID
 */
public class SysRoleMenuVo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	  private String name;
	  private String note;
	  private List<Integer>menuIds;

}
