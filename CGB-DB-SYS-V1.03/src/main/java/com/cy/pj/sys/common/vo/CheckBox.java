package com.cy.pj.sys.common.vo;

import java.io.Serializable;
import lombok.Data;
/**查询角色信息*/
@Data
public class CheckBox implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	@Override
	public String toString() {
		return "CheckBox [id=" + id + ", name=" + name + "]";
	}
	
	
}
