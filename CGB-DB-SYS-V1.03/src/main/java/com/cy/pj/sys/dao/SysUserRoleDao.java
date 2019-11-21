package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SysUserRoleDao {
	
	/**删除id与角色用户的关系数据*/
	@Delete("delete from sys_user_roles where role_id=#{roleId}")
	int deleteObjectsByRoleId(Integer roleId);

	/**负责将用户与角色关系写入到数据库*/
	int insertObjects(
			@Param("userId") Integer userId,
			@Param("roleIds") Integer[] roleIds);
	
	/**根据用户id查询角色id方法*/
	@Select("select role_id from sys_user_roles where user_id=#{id}")
	List<Integer> findRoleIdsByUserId(Integer id);

	int deleteObjectsByUserId(Integer userId);

}
