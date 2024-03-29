package com.cy.pj.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * @Target 注解用于描述自己定义的注解
 *    可以对哪些元素进行描述。由于该注解用的值是数组定义的，可以定义多元素
 *  @Retention 注解描述自己定义的注解何时有效  
 * @author UID
 */
@Target(value= {ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredLog {
	String value() default "";
	//String operation() default "operation";

}//所有的注解都默认继承自Annotation
