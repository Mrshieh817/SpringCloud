package com.xcf.mybatisplus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSort;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.xcf.mybatisplus.model.KernelHotConfig;
import com.xcf.mybatisplus.model.Vo.KResponse;
import com.xcf.mybatisplus.model.dto.KernelHotConfigDto;
import com.xcf.mybatisplus.model.input.KernelHotConfigParam;
import com.xcf.mybatisplus.service.KernelHotConfigService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
* @author xcf 
* @Date 创建时间：2021年5月24日 上午11:47:10 
* 系统热配置表
*/
@RestController
@RequestMapping("/config")
@Api(value ="系统热配置",tags = {"系统热配置"})
public class KernelHotConfigController {
	
	@Autowired
	@Qualifier(value ="KernelHotConfigService")
	private KernelHotConfigService configService;
	
	/**
	 * 获取配置表的所有信息
	 * @return
	 */
	@PostMapping("/getKernelHotConfigList")
	@ApiOperationSupport(author="谢承飞",order = 2)
	@ApiOperation("获取配置表的所有信息")
	public KResponse<List<KernelHotConfigDto>>  getKernelHotConfigList(){
		return KResponse.data(configService.getKernelHotConfigList());
	}
	
	
	/**
	 * 存储配置表信息
	 * @return
	 */
	@PostMapping("/save")
	@ApiOperation("存储配置表信息")
	@ApiOperationSupport(author="谢承飞",order = 1)
	public KResponse<Boolean> save(@RequestBody @Validated KernelHotConfigParam body){
		KernelHotConfig modelConfig=new KernelHotConfig();
		modelConfig.setConfigKey(body.getConfigKey());
		modelConfig.setConfigValue(body.getConfigValue());
		modelConfig.setConfigEnable(body.getConfigEnable());
		modelConfig.setConfigDescription(body.getConfigDescription());
		modelConfig.setConfigFlag(body.getConfigFlag());
		return KResponse.data(configService.save(modelConfig));
	}
}
