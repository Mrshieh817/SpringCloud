package com.xcf.mybatis.Controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.xcf.mybatis.Core.SysUser;
import com.xcf.mybatis.Mapper.SysUserMapper;
import com.xcf.mybatis.Service.SysuserService;

/**
 * @author 作者:大飞
 * @version 创建时间：2019年9月6日 下午1:45:23 类说明
 */

@RestController
@RequestMapping("/sysuser")
public class SysUserController {

	@Autowired
	private SysUserMapper sysUserMapper;
	@Resource
	private SysuserService sysuserService;

	/**
	 * 获取SYSUSER数据
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	public Object list() {
		return sysUserMapper.selectAll();
	}
	
	@RequestMapping("/get/{id}")
	public List<SysUser> get(@PathVariable(name="id") Integer id)
	{
		SysUser model=new SysUser();
		if (id!=0) {
			model.setId(id);
		}
		return sysuserService.Getuserlist(model);
	}
}
