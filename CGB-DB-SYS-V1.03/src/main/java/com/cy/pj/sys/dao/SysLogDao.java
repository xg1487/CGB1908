package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cy.pj.sys.entity.SysLog;
/**Dao:数据访问对象
 * 1)此对象的实现类由mybatis框架创建
 * 2)此对象的实现类中会自动注入SqlSessionFactory对象
 * */
@Mapper
public interface SysLogDao {
	/**负责基于条件查询当前页数据*/
	/**
	 * @param username 查询条件(例如查询哪个用户的日志信息)
	 * @param startIndex 当前页的起始位置
	 * @param pageSize 当前页的页面大小
	 * @return 当前页的日志记录信息
	 */
	List<SysLog> findPageObjects(
			@Param("username")String username,
			@Param("startIndex")Integer startIndex,
			@Param("pageSize")Integer pageSize);



	/**负责基于条件查询总记录数*/
	/**
	 * @param username 查询条件(例如查询哪个用户的日志信息)
	 * @return 总记录数(基于这个结果可以计算总页数) 
	 */
	int getRowCount(@Param("username")String username);

	/**
	 * ids(多个记录id)  用注解写的时候建议写简单的SQL语句
	 * 基于数据执行删除操作*/
	int deleteObjects(@Param("ids")Integer...ids);
	
	int insertObject(SysLog entity);
}
