package com.xcf.gateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
* @author xcf 
* @Date 创建时间：2021年6月9日 下午4:52:54 
*/
@RestController
@RequestMapping("/test")
@Api(tags = {"测试标记"})
public class TestController {
	
	@RequestMapping("/va")
	@ApiOperation("测试")
	public Object va() {
		return "6666666";
	}

}
