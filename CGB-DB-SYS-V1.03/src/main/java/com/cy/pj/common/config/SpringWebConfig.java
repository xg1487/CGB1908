package com.cy.pj.common.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cy.pj.common.web.TimeAccessInterceptor;

@Configuration
public class SpringWebConfig implements WebMvcConfigurer{
	//注册filter对象
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Bean
		public FilterRegistrationBean  newFilterRegistrationBean() {
			//1.构建过滤器的注册器对象
			FilterRegistrationBean fBean=
		    new FilterRegistrationBean();
			//2.注册过滤器对象
			DelegatingFilterProxy filter=
			new DelegatingFilterProxy("shiroFilterFactory");
			fBean.setFilter(filter);
			//3.进行过滤器配置
			//配置过滤器的生命周期管理(可选)由ServletContext对象负责
			//fBean.setEnabled(true);//默认值就是true
			fBean.addUrlPatterns("/*");
			//....
			return fBean;
		}
		@Override
		public void addInterceptors(InterceptorRegistry registry) {
			//注册拦截器，并设置拦截路径
			registry.addInterceptor(new TimeAccessInterceptor())
			.addPathPatterns("/user/doLogin");
			
		}

}
