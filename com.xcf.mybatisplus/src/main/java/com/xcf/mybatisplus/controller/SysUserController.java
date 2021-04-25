package com.xcf.mybatisplus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xcf.mybatisplus.model.SysUser;
import com.xcf.mybatisplus.model.Vo.KResponse;
import com.xcf.mybatisplus.service.SysUserService;

/** 
* @author xcf 
* @Date 创建时间：2021年4月25日 下午2:39:57 
*/
@RestController
@RequestMapping("/user")
public class SysUserController {
	
	@Autowired
	@Qualifier("SysUserServiceMY")
	private SysUserService userService;
	
	
	/**
	 * 根据MybatisPlus原生插件查询
	 * @return
	 */
	@RequestMapping("/getSysUserList")
	public KResponse<Page<SysUser>>  getSysUserList() {
		Page<SysUser> pagesPage=new Page<SysUser>(1,2);
		LambdaQueryWrapper<SysUser> queryWrapper=new LambdaQueryWrapper<SysUser>().gt(SysUser::getId, 0);
		Page<SysUser> listIPage=userService.getBaseMapper().selectPage(pagesPage, queryWrapper);
		return KResponse.data(listIPage);
	}
	
	@RequestMapping("/getUserXml")
	public  KResponse<List<SysUser>>  getUserXml(@RequestBody @Validated SysUser body){
		return KResponse.data(userService.getUser(body));
	}

}
