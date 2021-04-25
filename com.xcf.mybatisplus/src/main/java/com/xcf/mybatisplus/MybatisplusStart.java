package com.xcf.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author xcf
 * @Date 创建时间：2021年4月25日 下午2:01:39
 */
@SpringBootApplication
@ComponentScan("com.xcf.mybatisplus")
@MapperScan("com.xcf.mybatisplus")
public class MybatisplusStart {
	public static void main(String[] args) {
		SpringApplication.run(MybatisplusStart.class, args);

	}

}
