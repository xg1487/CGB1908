package com.cy.pj.sys.dao;


import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**创建角色关系表*/
@Mapper
public interface SysRoleMenuDao {
	
	/**
	 * 创建基于菜单id删除角色和菜单的关系数据
	 */
	int deleteObjectsByMenuId(Integer menuId);
	
	/**删除id角色与菜单关系数据*/
	@Delete("delete from sys_role_menus where role_id=#{roleId}")
	int deleteObjectsByRoleId(Integer roleId);

	/**添加角色与菜单的关系数据*/
	int insertObjects(@Param("roleId")Integer roleId,
			          @Param("menuIds")Integer[] menuIds);
	/**
	 * 基于用户的角色id查找菜单的角色id
	 * @param roleIds
	 * @return
	 */
	List<Integer>findMenuIdsByRoleIds(
			         @Param("roleIds")Integer[]roleIds);
	
}
