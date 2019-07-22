package com.igxe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
* @author 作者:大飞
* @version 创建时间：2019年4月22日 下午2:31:36
* 类说明
*/


@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class provider1Application {
 
    public static void main(String[] args) {
 
        SpringApplication.run(provider1Application.class, args);
    }

}