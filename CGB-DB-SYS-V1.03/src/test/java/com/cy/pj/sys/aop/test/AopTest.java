package com.cy.pj.sys.aop.test;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cy.pj.sys.common.vo.PageObject;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.service.SysLogService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AopTest {
	
	@Autowired
	private SysLogService sysLogService;
	
	@Test
	public void testLog() {
		PageObject<SysLog> po = sysLogService.findPageObjects("admin", 2);
		System.out.println("rowCount:"+po);
		
	}

}
