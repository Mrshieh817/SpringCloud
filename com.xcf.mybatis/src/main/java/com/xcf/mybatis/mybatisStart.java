package com.xcf.mybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import tk.mybatis.spring.annotation.MapperScan;

/**
 * 
 *
 */
@SpringBootApplication
@EnableScheduling     //注解定时任务
@EnableAsync
@MapperScan("com.xcf.mybatis.Mapper")
@ComponentScan("com.xcf.mybatis")
@EnableCaching //REDIS作为二级缓存
public class mybatisStart 
{
	public static void main(String[] args) {
		SpringApplication.run(mybatisStart.class, args);
	}
	
	//因为Scheduled在运行的时候，首先只会默认运行一个,必须等随机一个方法运行完后其他的才能相应的开始,如果设置了任务运行池,则可以在开始的时候同时运行。
	@Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(50);
        return taskScheduler;
    }
}
