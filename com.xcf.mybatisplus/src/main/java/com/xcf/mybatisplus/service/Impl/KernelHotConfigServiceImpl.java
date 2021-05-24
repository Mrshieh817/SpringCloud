package com.xcf.mybatisplus.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xcf.mybatisplus.mapper.KernelHotConfigMapper;
import com.xcf.mybatisplus.model.KernelHotConfig;
import com.xcf.mybatisplus.model.dto.KernelHotConfigDto;
import com.xcf.mybatisplus.service.KernelHotConfigService;

/** 
* @author xcf 
* @Date 创建时间：2021年5月24日 上午11:19:44 
* 系统热配置表
*/
@DS("other_source")
@Service("KernelHotConfigService")
public class KernelHotConfigServiceImpl extends ServiceImpl<KernelHotConfigMapper, KernelHotConfig> implements KernelHotConfigService{
	
	@Autowired
	private KernelHotConfigMapper configMapper;
	
	/**
	 *  获取配置表的所有信息
	 */
	@Override
	public List<KernelHotConfigDto> getKernelHotConfigList(){
		LambdaQueryWrapper<KernelHotConfig> query=new LambdaQueryWrapper<KernelHotConfig>();
		query.eq(KernelHotConfig::getConfigEnable,1);
	    List<KernelHotConfig> list=configMapper.selectList(query);
	    List<KernelHotConfigDto> listDto=new ArrayList<KernelHotConfigDto>();
	    for (int i = 0; i < list.size(); i++) {
	    	KernelHotConfigDto mmConfigDto=new KernelHotConfigDto();
	    	 BeanUtils.copyProperties(list.get(i),mmConfigDto);
	    	 listDto.add(mmConfigDto);
		}
		return listDto;
	}
}
