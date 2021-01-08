package com.igxe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.igxe.mapper.cityMapper;
import com.igxe.model.city;
import com.igxe.service.cityService;


@Service
public class cityServiceimpl implements cityService {
	@Autowired
	private cityMapper cityMapper;

	@Override
	public boolean add(city model) {
		try {
			model.setCity("我是2226622");
			Integer countInteger = cityMapper.insert(model);
			if (countInteger > 0) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			throw new IllegalStateException("by exF11111lag");
			
		}

	}

}
