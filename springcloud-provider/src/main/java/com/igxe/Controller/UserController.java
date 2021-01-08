package com.igxe.Controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.igxe.client.userclient;
import com.igxe.model.Usermodel;
import com.igxe.model.city;
import com.igxe.service.cityService;
import com.igxe.tool.CookieUtil;

/**
 * @author 作者:大飞
 * @version 创建时间：2019年4月22日 下午4:12:39 类说明
 */

@RestController
@RequestMapping({ "/user" })
public class UserController {

	@Resource
	private userclient client;
	
	@Autowired
	private cityService cityService;
	
	
	@GetMapping({"/test"})
	public Usermodel test(HttpServletRequest request,HttpServletResponse response) {
		request.getSession().setAttribute("xcf", "woshifafei");
		Cookie cookie=new Cookie("ffcoo", "cookeis啊啊啊啊");
		response.addCookie(cookie);
		CookieUtil.set(response, "ffcoo", "cookeis啊啊啊啊",1000*60*60);
		Usermodel o = new Usermodel();
		o.setName("我是大飞吖11111"+request.getHeader("xcf"));
		o.setAdress("重庆江北1111");
		return o;
	} 
	
	@GetMapping({"/test1"})
	public Usermodel test1() {
		System.out.println("request is coming...");
		try {
			 //Thread.sleep(1000*1);
		} catch (Exception e) {
			System.out.println("线程被打断... " + e.getMessage());
		}
		return client.test();
	} 
	
	@RequestMapping({"/test2"})
	public Usermodel test2(@RequestParam("id")Integer id) {
		
		return client.getxcf(id);
	} 
	
	@RequestMapping("/city")
	public String city() {
		city model = new city();
		model.setCountryId(2);
		model.setLastUpdate(new Date());
		//发起方
		boolean bo = cityService.add(model);
		//被调方
		String re2= client.city();
		
		
		return bo == true ? "success+"+re2+"" : "fail"+re2;
	}
}
