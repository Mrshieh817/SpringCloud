package com.xcf.mybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
public class mybatisStart 
{
	public static void main(String[] args) {
		SpringApplication.run(mybatisStart.class, args);
	}
	
	@Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(50);
        return taskScheduler;
    }
}
