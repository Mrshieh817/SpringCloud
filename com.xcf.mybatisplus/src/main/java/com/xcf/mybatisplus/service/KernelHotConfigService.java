package com.xcf.mybatisplus.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xcf.mybatisplus.model.KernelHotConfig;
import com.xcf.mybatisplus.model.dto.KernelHotConfigDto;

/**
 * @author xcf
 * @Date 创建时间：2021年5月24日 上午11:19:19
 */
public interface KernelHotConfigService extends IService<KernelHotConfig> {

	/**
	 * 获取配置表的所有信息
	 * @return
	 */
	List<KernelHotConfigDto> getKernelHotConfigList();
}
