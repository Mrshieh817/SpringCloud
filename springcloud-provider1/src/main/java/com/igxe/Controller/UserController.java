package com.igxe.Controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.igxe.client.userclient;
import com.igxe.model.Usermodel;

/**
 * @author 作者:大飞
 * @version 创建时间：2019年4月22日 下午4:12:39 类说明
 */

@RestController
@RequestMapping({ "/user" })
public class UserController {

	@Resource
	private userclient client;
	
	
	@GetMapping({"/test"})
	public Usermodel test() {
		Usermodel o = new Usermodel();
		o.setName("我是大飞吖");
		o.setAdress("重庆江北");
		return o;
	} 
	
	@GetMapping({"/test1"})
	public Usermodel test1() {
		
		return client.test();
	} 
	
	@GetMapping({"/test2"})
	public Usermodel test2(Integer id) {
		
		return client.getxcf(id);
	} 
}
