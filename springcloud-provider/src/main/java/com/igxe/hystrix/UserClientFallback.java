package com.igxe.hystrix;

import com.igxe.client.userclient;
import com.igxe.model.Usermodel;

import lombok.RequiredArgsConstructor;

/**
* @author 作者:大飞
* @version 创建时间：2019年12月7日 下午12:40:11
* 类说明
*/
@RequiredArgsConstructor
public class UserClientFallback implements  userclient {
   private final Throwable throwable;
	
	@Override
	public  Usermodel test(){
		Usermodel usermodel=new Usermodel();
		usermodel.setName("test1方法");
		usermodel.setAdress(throwable.getMessage());
		return usermodel;
	}
	@Override
	public  Usermodel getxcf(Integer id){
		Usermodel usermodel=new Usermodel();
		usermodel.setName("getxcf方法");
		usermodel.setAdress(throwable.getMessage());
		return usermodel;
	}
	
	@Override
	public String city() {
		return "fail";
	}
	

}
