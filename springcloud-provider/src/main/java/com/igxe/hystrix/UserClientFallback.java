package com.igxe.hystrix;

import org.springframework.stereotype.Component;

import com.igxe.client.userclient;
import com.igxe.model.Usermodel;

/**
* @author 作者:大飞
* @version 创建时间：2019年12月7日 下午12:40:11
* 类说明
*/
@Component
public class UserClientFallback implements  userclient {
	@Override
	public  Usermodel test(){
		Usermodel usermodel=new Usermodel();
		usermodel.setName("error1111");
		usermodel.setAdress("error1111");
		return usermodel;
	}
	@Override
	public  Usermodel getxcf(Integer id){
		Usermodel usermodel=new Usermodel();
		usermodel.setName("error1111");
		usermodel.setAdress("error1111");
		return usermodel;
	}
	
	@Override
	public String city() {
		return "fail";
	}
	

}
