package com.xcf.mybatis.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xcf.mybatis.Mapper.SysUserMapper;

/**
 * @author 作者:大飞
 * @version 创建时间：2019年9月6日 下午1:45:23 类说明
 */

@RestController
@RequestMapping("/sysuser")
public class SysUserController {

	@Autowired
	private SysUserMapper sysUserMapper;

	/**
	 * 获取SYSUSER数据
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	public Object list() {
		return sysUserMapper.selectAll();
	}
}
