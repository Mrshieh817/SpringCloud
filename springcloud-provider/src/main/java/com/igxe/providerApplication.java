package com.igxe;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import tk.mybatis.spring.annotation.MapperScan;


/**
* @author 作者:大飞
* @version 创建时间：2019年4月22日 下午2:31:36
* 类说明
*/


@EnableEurekaClient
@EnableFeignClients("com.igxe.*")
@MapperScan("com.igxe.mapper")
@ComponentScan("com.igxe")
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//Seate设置数据源需要
@EnableAutoDataSourceProxy //Seate设置数据源需要
@EnableCircuitBreaker
@EnableHystrix
public class providerApplication {
 
    public static void main(String[] args) {
 
        SpringApplication.run(providerApplication.class, args);
    }

}