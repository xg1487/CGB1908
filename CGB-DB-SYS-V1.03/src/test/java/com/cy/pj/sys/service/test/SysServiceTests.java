package com.cy.pj.sys.service.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cy.pj.sys.common.vo.PageObject;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.service.SysLogService;

@SpringBootTest
public class SysServiceTests {

	@Autowired
	private SysLogService sysLogService;
	@Test
	public void testFindPageObjects() {
		PageObject<SysLog> pageObject = 
				sysLogService.findPageObjects("admin", 3);
		System.out.println(pageObject.getPageSize());
	  System.out.println(pageObject.getPageCount());
	}
	
	
}
