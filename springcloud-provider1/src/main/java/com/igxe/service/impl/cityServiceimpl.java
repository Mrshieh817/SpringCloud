package com.igxe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.igxe.mapper.cityMapper;
import com.igxe.model.city;
import com.igxe.service.cityService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class cityServiceimpl implements cityService {
	@Autowired
	private cityMapper cityMapper;

	@Override
	@Transactional
	public boolean add(city model) {

		model.setCity("ee111114444444444444444444444444444444444444444444444444444441rre");
		Integer countInteger = cityMapper.insert(model);
		if (countInteger > 0) {
			return true;
		} else {
			return false;
		}

	}

}
