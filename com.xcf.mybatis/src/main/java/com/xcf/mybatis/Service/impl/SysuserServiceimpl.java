package com.xcf.mybatis.Service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xcf.mybatis.Core.SysUser;
import com.xcf.mybatis.Mapper.SysUserMapper;
import com.xcf.mybatis.Service.SysuserService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @author 作者:大飞
 * @version 创建时间：2019年10月14日 下午2:19:02 类说明
 */
@Service(value = "SysuserService")
public class SysuserServiceimpl implements SysuserService {
	@Resource
	private SysUserMapper usermapper;

	@Override
	public List<SysUser> Getuserlist(SysUser model) {
		Example ordersendexample = new Example(SysUser.class);
		Criteria ordersendcc = ordersendexample.createCriteria();
		if (model!=null && model.getId()!=null) {
			ordersendcc.andEqualTo("id", model.getId());
		} else {
			ordersendcc.andNotEqualTo("id", 0);
		}		
		return usermapper.selectByExample(ordersendexample);
	}
}
