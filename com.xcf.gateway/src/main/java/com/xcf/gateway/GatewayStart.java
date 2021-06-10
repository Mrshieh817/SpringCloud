package com.xcf.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @author xcf
 * @Date 创建时间：2021年4月25日 下午2:01:39
 */
@EnableSwagger2WebFlux
@SpringBootApplication
public class GatewayStart {
	public static void main(String[] args) {
		SpringApplication.run(GatewayStart.class, args);
	}

}
