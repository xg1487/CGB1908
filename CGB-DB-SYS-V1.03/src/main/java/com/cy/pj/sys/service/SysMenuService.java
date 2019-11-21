package com.cy.pj.sys.service;

import java.util.List;
import java.util.Map;

import com.cy.pj.sys.common.vo.Node;
import com.cy.pj.sys.entity.SysMenu;

public interface SysMenuService {
	
	/**菜单业务接口方法定义*/
	List<Map<String, Object>> findObjects();
	
	/**根据id删除菜单的方法(没有子菜单的，有子菜单的话不能删除)*/
	int deleteObject(Integer id);
	
	/**基于请求获取数据库对应的菜单表中的所有菜单信息*/
	List<Node>findZtreeMenuNodes();
	
	int saveObject(SysMenu entity);
	
	int updateObject(SysMenu entity);

}
