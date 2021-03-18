package com.igxe.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.igxe.client.userclient;
import com.igxe.mapper.cityMapper;
import com.igxe.model.city;
import com.igxe.service.cityService;

import io.seata.spring.annotation.GlobalTransactional;


@Service
public class cityServiceimpl implements cityService {
	@Autowired
	private cityMapper cityMapper;
	
	@Resource
	private userclient client;

	@Override
	@GlobalTransactional(name = "fsp-create-order",rollbackFor = Exception.class)
	public boolean add(city model) {
		
			model.setCity("我是2226622");
			Integer countInteger = cityMapper.insert(model);
			
			//被调方
			String re2= client.city();
			System.out.println("222222222222:"+re2);
			if (countInteger > 0) {
				return true;
			} else {
				return false;
			}

		

	}

}
