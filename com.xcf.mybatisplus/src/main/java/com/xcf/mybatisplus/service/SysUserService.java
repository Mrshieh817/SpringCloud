package com.xcf.mybatisplus.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xcf.mybatisplus.model.SysUser;
import com.xcf.mybatisplus.model.input.SysUserParam;

/** 
* @author xcf 
* @Date 创建时间：2021年4月25日 下午2:36:56 
*/
public interface SysUserService extends IService<SysUser> {
	/**
	 * 通过XML读取数据
	 * @param model
	 * @return
	 */
	List<SysUser> getUser(SysUserParam model);
	
	Integer getCallproduce();
}
