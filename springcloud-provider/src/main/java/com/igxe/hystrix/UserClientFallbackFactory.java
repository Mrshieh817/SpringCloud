package com.igxe.hystrix;
/** 
* @author xcf 
* @Date 创建时间：2021年3月29日 上午10:38:07 
*/

import org.springframework.stereotype.Component;

import com.igxe.client.userclient;

import feign.hystrix.FallbackFactory;

@Component
public class UserClientFallbackFactory implements FallbackFactory<userclient> {
	@Override
	public userclient create(Throwable cause) {
		return new UserClientFallback(cause);
	}

}
