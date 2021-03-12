package com.igxe.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.igxe.model.Usermodel;

/**
* @author 作者:大飞
* @version 创建时间：2019年4月22日 下午5:11:03
* 类说明
*/

@RestController
@RequestMapping({ "product"})
public class productController {
	
	@GetMapping({"/test"})
	public Usermodel test() {
		Usermodel o = new Usermodel();
		o.setName("我是大飞吖-product2222222222");
		o.setAdress("重庆江北222");
		return o;
	} 
	
	@RequestMapping({"/client/getxcf"})
	public Usermodel getxcf(@RequestParam("id") Integer id) {
		Usermodel o = new Usermodel();
		o.setName("我是大飞吖-我是大飞吖-我是大飞吖-我是大飞吖-我是大飞吖-"+id+"");
		o.setAdress("重庆九龙坡");
		return o;
	} 

}
