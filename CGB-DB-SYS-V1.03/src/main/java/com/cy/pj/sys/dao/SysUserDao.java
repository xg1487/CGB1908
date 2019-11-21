package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.vo.SysUserDeptVo;

@Mapper
public interface SysUserDao {
	
	/**依据查询当前页要呈现的数据*/
	List<SysUserDeptVo>findPageObjects(
			@Param("username") String username,
			@Param("startIndex") Integer startIndex,
			@Param("pageSize") Integer pageSize);
	
	/**依据条件查询(用户)总记录数*/
	int getRowCount(@Param("username") String username);
 
	int validById(
			@Param("id") Integer id,
			@Param("valid") Integer valid,
			@Param("modifiedUser") String modifiedUser);

	/**负责将用户信息写入到数据库*/
	int insertObject(SysUser entity);
	
	/**根据id查询用户以及部门的方法*/
	@Select("select * from sys_users where id=#{id}")
	SysUserDeptVo findObjectById(Integer id);
	
	/**添加更新用户自身数据的方法*/
	int updateObject(SysUser entity);
	/*判断用户名是否重复*/
	/*@Select("select count(*) from sys_users where username=#{username}")
	int isExists(String username);*/
	
	@Select("select count(*) from sys_users where ${columnName}=#{columnValue}")
	int siExists(String columnName,String columnValue);

	//登录用户
	/**根据用户名查询用户对象的方法定义*/
	@Select("select * from sys_users where username=#{username}")
	SysUser findUserByUserName(String username);
	
	//修改密码
	int updatePassword(
			@Param("password")String password,
			@Param("salt")String salt,
			@Param("id")Integer id);
	

}
