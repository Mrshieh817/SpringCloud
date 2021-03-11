package com.xcf.mybatis.Controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.xcf.mybatis.Core.SysUser;
import com.xcf.mybatis.Mapper.SysUserMapper;
import com.xcf.mybatis.Service.SysuserService;
import com.xcf.mybatis.aspect.WebLog;

import io.swagger.annotations.ApiOperation;

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
	@ApiOperation("hehe")
	public Object list() {
	  Object value=sysUserMapper.selectAll().stream().map(SysUser->SysUser.getName()).collect(Collectors.toList());
	  System.out.println(value);
		return value;
	}
	
	@Cacheable(cacheNames = "user",key = "#id")
	@RequestMapping("/get/{id}")
	public List<SysUser> get(@PathVariable(name="id") Integer id)
	{
		SysUser model=new SysUser();
		if (id!=0) {
			model.setId(id);
		}
		return sysuserService.Getuserlist(model);
	}
	
	@WebLog(value = "AOP简单日志记录",description = "看看能不能记录上",params = {"id","name"})
	@RequestMapping("getinfo")
	public Object getinfo(@RequestParam(name="id",defaultValue="0") Integer id){
		return sysuserService.getuserinfobyid(id);
	}
}
