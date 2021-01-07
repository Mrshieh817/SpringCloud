package com.igxe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import tk.mybatis.spring.annotation.MapperScan;


/**
* @author 作者:大飞
* @version 创建时间：2019年4月22日 下午2:31:36
* 类说明
*/


@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
@MapperScan("com.igxe.mapper")
@ComponentScan("com.igxe")


//下面的注解是TX-LCN的
//@EnableDistributedTransaction
//@EnableTransactionManagement
public class provider1Application {
 
    public static void main(String[] args) {
 
        SpringApplication.run(provider1Application.class, args);
    }

}