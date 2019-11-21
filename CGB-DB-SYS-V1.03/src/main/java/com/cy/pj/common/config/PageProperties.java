package com.cy.pj.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;
/**
 * 基于此类封装分页信息
 * @ConfigurationProperties 注解描述的类可以交给
 * spring容器管理，并且可以从配置文件中读取配置信息
 * 然后赋值给对象属性(但属性需要提供set方法)
 */
@Data
@ConfigurationProperties(prefix = "db.page")
public class PageProperties {
	private Integer pageSize=3;

}
