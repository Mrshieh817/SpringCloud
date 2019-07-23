package com.igxe.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.igxe.model.Usermodel;
import com.igxe.tool.CookieUtil;

/**
* @author 作者:大飞
* @version 创建时间：2019年4月22日 下午5:11:03
* 类说明
*/

@RestController
@RequestMapping({ "product"})
public class productController {
	@GetMapping({"/test"})
	public Usermodel test(HttpServletRequest request) {
		Usermodel o = new Usermodel();
		o.setName("我是大飞吖-product111111111,Header:"+request.getHeader("xcf")+"cookies:"+CookieUtil.getValue(request,"ffcoo"));
		o.setAdress("重庆江北");
		return o;
	} 

}
