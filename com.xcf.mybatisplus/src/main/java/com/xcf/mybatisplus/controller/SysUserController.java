package com.xcf.mybatisplus.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xcf.mybatisplus.model.SysUser;
import com.xcf.mybatisplus.model.Vo.KResponse;
import com.xcf.mybatisplus.model.input.SysUserParam;
import com.xcf.mybatisplus.service.SysUserService;

/** 
* @author xcf 
* @Date 创建时间：2021年4月25日 下午2:39:57 
*/
@RestController
@RequestMapping("/user")
public class SysUserController {
	
	@Autowired
	@Qualifier("UserService")
	private SysUserService userService;
	
	
	/**
	 * 根据MybatisPlus原生插件查询
	 * @return
	 */
	@RequestMapping("/getSysUserList")
	public KResponse<Page<SysUser>>  getSysUserList() {
		Page<SysUser> pagesPage=new Page<SysUser>(1,10);
		LambdaQueryWrapper<SysUser> queryWrapper=new LambdaQueryWrapper<SysUser>().isNotNull(SysUser::getId);
		Page<SysUser> listIPage=userService.getBaseMapper().selectPage(pagesPage, queryWrapper);
		return KResponse.data(listIPage);
	}
	
	/**
	 * 根据XML配置获取数据
	 * @param body
	 * @return
	 */
	@RequestMapping("/getUserXml")
	public  KResponse<List<SysUser>>  getUserXml(@RequestBody @Validated SysUserParam body){
		return KResponse.data(userService.getUser(body));
	}
	
	@RequestMapping("/autoAdd")
	@Transactional
	public KResponse<SysUser> autoAdd(){
		boolean bo=true;
		
		try {
			SysUser addSysUser=new SysUser();
			addSysUser.setName("my auto add");
			addSysUser.setPass("10000");
			addSysUser.setUpdateTime(new Date());
			 bo= userService.save(addSysUser);
		} catch (Exception e) {
			// TODO: handle exception
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return bo==true? KResponse.success():KResponse.failed();
	}

}
