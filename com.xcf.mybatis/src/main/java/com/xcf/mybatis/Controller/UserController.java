package com.xcf.mybatis.Controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xcf.mybatis.Core.SysUser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
* @author 作者:大飞
* @version 创建时间：2019年12月25日 下午2:00:24
* /swagger-ui.html
*/
@Api(tags = "用户操作接口")
@Controller("user")
@RequestMapping("/user")
public class UserController {
	
	
	@Value("${aite.canshu}")
	private String canshu;
	
	@ApiOperation(value = "获取otp", notes="通过手机号获取OTP验证码")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "telephone", value = "电话号码", paramType = "query", defaultValue = "10086", required = false, dataType = "String"),
		@ApiImplicitParam(name = "orderId", value = "订单号码", paramType = "query", defaultValue = "10086", required = false, dataType = "String") 
	})
    @RequestMapping(value = "getotp", method=RequestMethod.GET)
    @ResponseBody
    public Object test(@ApiParam(value="telephone",required=false,defaultValue="18716398693")String telephone){
		SysUser model=new SysUser();
		model.setId(1008611);
		//model.setName("中国移动");
		model.setName(canshu);
		model.setPass("1008612501235698696");
		model.setTel("18716398693");
		return model;
	}
	
	@ApiOperation("测试自动补0")
	@RequestMapping("/autoaddzero")
	@ResponseBody
	public Object autoaddzero(@RequestParam(name = "code", required = true) String code) {
		int count = 10;
		int oldcount = code.length();
		if (oldcount < count) {
			for (int i = 0; i < count - oldcount; i++) {
				code = code + "0";
			}
		}
		return code;

	}

}
