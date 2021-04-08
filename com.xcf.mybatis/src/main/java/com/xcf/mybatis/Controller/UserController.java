package com.xcf.mybatis.Controller;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xcf.mybatis.Core.SysUser;
import com.xcf.mybatis.Core.Delay.DelayJob;
import com.xcf.mybatis.Service.Delay.DelayJobService;

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
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private DelayJobService delayjobservice;
	
	@Autowired
	private RedissonClient client;
	
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
		System.out.println("telephone:"+telephone);
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
		System.out.println(code);
		int count = 10;
		int oldcount = code.length();
		if (oldcount < count) {
			for (int i = 0; i < count - oldcount; i++) {
				code = code + "0";
			}
		}
		return code;

	}
	
	@ApiOperation("测试redssion的延迟服务")
	@RequestMapping("/redisson")
	@ResponseBody
	public void redisson() {
		try {
			DelayJob job=new DelayJob();
		    job.setNametest(new Date().toGMTString());
			delayjobservice.submitJob(job, 5, TimeUnit.SECONDS);
		}catch (Exception e) {
			System.out.println("异常："+e.getMessage());
		}finally {
			System.out.println("执行完毕!");
		}
	}
	
	@ApiOperation("测试吃什么")
	@RequestMapping("/testeat")
	@ResponseBody
	public Object testeat() {
		Random random = new Random();
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("1", "烤鱼");
		map.put("2", "火锅");
		map.put("3", "串串");
		Integer count= random.nextInt(3)+1;
		System.out.println("随机:"+map.get(count.toString()));
		return "吃"+map.get(count.toString());
	}
	

}
