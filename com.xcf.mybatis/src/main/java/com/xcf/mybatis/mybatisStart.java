package com.xcf.mybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import tk.mybatis.spring.annotation.MapperScan;

/**
 * 
 *
 */
@SpringBootApplication
@EnableScheduling     //注解定时任务
@MapperScan("com.xcf.mybatis.Mapper")
@ComponentScan("com.xcf.mybatis")
public class mybatisStart 
{
	public static void main(String[] args) {
		SpringApplication.run(mybatisStart.class, args);
	}
}
