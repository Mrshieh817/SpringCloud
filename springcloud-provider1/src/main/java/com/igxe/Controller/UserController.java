package com.igxe.Controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.igxe.client.userclient;
import com.igxe.model.Usermodel;
import com.igxe.model.city;
import com.igxe.service.cityService;

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

	@GetMapping({ "/test" })
	public Usermodel test(HttpServletRequest request) {
		Usermodel o = new Usermodel();
		o.setName("我是大飞吖2222");
		o.setAdress("重庆江北2222==获取session共享:" + request.getSession().getAttribute("xcf"));
		return o;
	}

	@GetMapping({ "/test1" })
	public Usermodel test1() {

		return client.test();
	}

	@GetMapping({ "/test2" })
	public Usermodel test2(@RequestParam("id") Integer id) {

		return client.getxcf(id);
	}

	@RequestMapping("city")
	public String city() {
		city model = new city();
		model.setCountryId(2);
		model.setLastUpdate(new Date());
		boolean bo = cityService.add(model);
		return bo == true ? "success" : "fail";
	}
}
