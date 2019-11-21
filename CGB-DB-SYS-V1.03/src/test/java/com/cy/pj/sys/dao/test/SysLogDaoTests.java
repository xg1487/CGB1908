package com.cy.pj.sys.dao.test;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cy.pj.sys.dao.SysLogDao;
import com.cy.pj.sys.entity.SysLog;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysLogDaoTests {
 //对数据层方法进行测试
	@Autowired
	private SysLogDao sysLogDao;
	
	@Test
	public void testFindPageObjects() {
		List<SysLog> list = sysLogDao.
				findPageObjects("admin", 0, 3);
		System.out.println("list="+list.size());
	}
	
	@Test
	public void testGetRowCount() {
		int rows = sysLogDao.getRowCount("admin");
		System.out.println("rows="+rows);
	}
	@Test
	public void testDeleteObjects() {
		int row = sysLogDao.deleteObjects(10);
		System.out.println("delete.rows="+row);
	}
}
