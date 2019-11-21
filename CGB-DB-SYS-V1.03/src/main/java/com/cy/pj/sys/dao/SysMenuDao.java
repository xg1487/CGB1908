package com.cy.pj.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cy.pj.sys.common.vo.Node;
import com.cy.pj.sys.entity.SysMenu;

@Mapper
public interface SysMenuDao {

	/**查询所有菜单以及上级菜单信息
	 * @return
	 * 一行记录映射为一个map，多行记录用list来存储
	 */
	List<Map<String,Object>> findObjects();
	
	/**基于id查询是否有子菜单
	 */
	int getChildCount(Integer id);
	
	/**
	 * 根据id删除菜单的方法(没有子菜单的，有子菜单的话不能删除)
	 */
	int deleteObject(Integer id);
	
	/** 基于请求获取数据库对应的菜单表中的所有菜单信息
	 */
	List<Node>findZtreeMenuNodes();
	
	/**
	 * 在SysMenuDao中添加插入数据的方法，用于将菜单信息写入到数据库
	 */
	int insertObject(SysMenu entity);
	
	/**修改菜单方法，用于实现数据库中菜单信息的修改*/
	int updateObject(SysMenu entity);
	
	/**基于菜单id找权限标识*/
	List<String>findPermissions(
			     @Param("menuIds")Integer[]menuIds);
}
