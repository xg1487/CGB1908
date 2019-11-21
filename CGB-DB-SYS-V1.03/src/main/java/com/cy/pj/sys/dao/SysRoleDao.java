package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cy.pj.sys.common.vo.CheckBox;
import com.cy.pj.sys.entity.SysRole;
import com.cy.pj.sys.vo.SysRoleMenuVo;

@Mapper
public interface SysRoleDao {

	/**
	 * 分页查询角色信息
	 * @param name 角色名称
	 * @param startIndex 上一页的结束位置
	 * @param pageSize 每页要查询的记录数
	 * @return
	 */
	List<SysRole> findPageObjects(
			@Param("name")String name,
			@Param("startIndex")Integer startIndex,
			@Param("pageSize")Integer pageSize
			);
	
	/**查询记录总数*/
	int getRowCount(@Param("name")String name);
	
	/**根据id删除角色自身信息*/
	@Delete("delete from sys_roles where id=#{id}")
	int deleteObject(Integer id);
	
	/**添加角色自身信息保存到数据库*/
	int insertObject(SysRole entity);
	
	/**基于角色id查询角色信息的方法*/
	SysRoleMenuVo findObjectById(Integer id);
	
	/**修改角色自身信息*/
	int updateObject(SysRole entity);
	
	/**查询所有角色的id和name，一条记录封装为一个CheckBox*/
	List<CheckBox> findObjects();

}
