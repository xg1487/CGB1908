package com.cy.pj.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cy.pj.common.config.PageProperties;
import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.sys.common.vo.PageObject;
import com.cy.pj.sys.dao.SysLogDao;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.service.SysLogService;
/**业务层实现类
 * BD：日志业务对象
 * */
@Service
public class SysLogServiceImpl implements SysLogService  {

	@Autowired
	private SysLogDao sysLogDao;

	@Autowired
	private PageProperties pageProperties;

	
	@Override
	public PageObject<SysLog> findPageObjects(String username, Integer pageCurrent) {
		// 1.验证参数合法性
		//1.1验证pageCurrent的合法性
		//不合法抛出IllegalArgumentException异常
		if(pageCurrent==null || pageCurrent<1)
			throw new IllegalArgumentException("当前页码不正确");
		//2.基于条件查询总记录数
		//2.1)执行查询
		int rowCount = sysLogDao.getRowCount(username);
		//2.2)验证查询结果，假如结果为0不再执行如下操作
		if(rowCount==0) 
			throw new ServiceException("系统没找到对应记录");
		//3.基于条件查询当前页记录(pageSize定义为2)
		//3.1)定义pageSize
		Integer pageSize = pageProperties.getPageSize();
		System.out.println("pageSize="+pageSize);
		//3.2)计算startIndex
		int startIndex=(pageCurrent-1)*pageSize;
		//3.3)执行当前数据的查询操作
		List<SysLog> records = sysLogDao.findPageObjects(username, startIndex, pageSize);
		//4.对分页信息以及当前记录进行封装
		//4.1)构建PageObject对象
		PageObject<SysLog> pageObject = new PageObject<>();
		//4.2)封装数据
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageSize(pageSize);
		pageObject.setRowCount(rowCount);
		pageObject.setRecords(records);
		pageObject.setPageCount((rowCount-1)/pageSize+1);
		//返回封装数据
		return pageObject;
	}

	@Override
	public int deleteObjects(Integer...ids) {
		//1.判定参数合法性
		if(ids==null||ids.length==0)
			throw new IllegalArgumentException("请选择一个");
		//2.执行删除操作
		int rows;
		try{
			rows=sysLogDao.deleteObjects(ids);
		}catch(Throwable e){
			e.printStackTrace();
			//发出报警信息(例如给运维人员发短信)
			throw new ServiceException("系统故障，正在恢复中...");
		}
		//4.对结果进行验证
		if(rows==0)
			throw new ServiceException("记录可能已经不存在");
		//5.返回结果
		return rows;
	}

	//如果没有添加propagation =Propagation.REQUIRES_NEW，那么则无法执行(会有回滚事务)
	@Transactional(
			propagation =Propagation.REQUIRES_NEW)
	@Async
	@Override
	public void saveObject(SysLog entity) {
		//获取线程名 @Async注解：表示AOP异步操作的实现
		System.out.println("SysLogServiceImpl.save"+Thread.currentThread().getName());
      sysLogDao.insertObject(entity);
      //模拟耗时
      try {
		//Thread.sleep(5000);
	} catch (Exception e) {
		e.printStackTrace();
	}
	 
		
	}



}
