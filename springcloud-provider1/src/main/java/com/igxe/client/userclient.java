package com.igxe.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.igxe.hystrix.UserClientFallback;
import com.igxe.model.Usermodel;
import com.igxe.tool.FeignConfiguration;

import feign.Param;

/**
* @author 作者:大飞
* @version 创建时间：2019年4月22日 下午5:09:23
* 类说明
* 
* 特别注明:@FeignClient 里面的名称是spring.applicatoin.name  名称 如果是多项目的话 名称要一样 参考 provider  和provider1
*  @GetMapping("product/test") 这个地址是暴露的api地址 类似一个controller 里面的某个方法
*/
@FeignClient(value="product", configuration = FeignConfiguration.class,fallback = UserClientFallback.class)
public interface userclient {
	
  @GetMapping("product/test")
  Usermodel test();
  
  @RequestMapping("product/client/getxcf")
  Usermodel getxcf(@RequestParam("id") Integer id);
}
