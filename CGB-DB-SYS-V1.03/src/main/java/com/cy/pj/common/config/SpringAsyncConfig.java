package com.cy.pj.common.config;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Configuration
public class SpringAsyncConfig implements AsyncConfigurer{

	private int corePoolSize=16;
	private int maximumPoolSize=128;
	private int keepAliveTime=30;
	private int queueCapactity=150;
	
	private ThreadFactory threadFactory=new ThreadFactory() {
		//CAS算法
		private AtomicInteger at=new AtomicInteger(10);//初始值
	    @Override
		public Thread newThread(Runnable r) {
			return new Thread(r,"db-async-thread-"+at.getAndIncrement());//自增	
		}
		
	};
	
	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maximumPoolSize);
		executor.setKeepAliveSeconds(keepAliveTime);
		executor.setQueueCapacity(queueCapactity);
		executor.setThreadFactory(threadFactory);
		executor.setRejectedExecutionHandler((Runnable r, 
				 ThreadPoolExecutor exe) -> {//自定义表达式，拉姆达表达式（JDK1.8之后）
				                 log.warn("当前任务线程池队列已满.");
				         });
				         executor.initialize();
				         return executor;

	}
	 @Override
	    public AsyncUncaughtExceptionHandler 
	getAsyncUncaughtExceptionHandler() {
	        return new AsyncUncaughtExceptionHandler() {
	            @Override
	            public void handleUncaughtException(Throwable ex ,
	 Method method , Object... params) {
	                log.error("线程池执行任务发生未知异常.", ex);
	            }
	        };
	    }
	}
