package com.cy.pj.sys.common.vo;

import java.io.Serializable;
import lombok.Data;
/**
 * VO(值对象)
 * 基于此对象封装树节点信息
 */
@Data
public class Node implements Serializable {

	private static final long serialVersionUID = 1L;
 
	private Integer id;
	private String name;
	private Integer parentId;
	
}
