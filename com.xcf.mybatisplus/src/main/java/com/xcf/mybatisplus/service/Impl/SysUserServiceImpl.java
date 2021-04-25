package com.xcf.mybatisplus.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xcf.mybatisplus.mapper.SysUserMapper;
import com.xcf.mybatisplus.model.SysUser;
import com.xcf.mybatisplus.service.SysUserService;

/** 
* @author xcf 
* @Date 创建时间：2021年4月25日 下午2:39:00 
*/
@Service(value = "SysUserServiceMY")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService{
	
	@Autowired
	private SysUserMapper usermapper;
	
	
	@Override
	public List<SysUser> getUser(SysUser model){
		List<SysUser> list=null;
		try {
			list= usermapper.getUser(model);
		} catch (Exception e) {
			// TODO: handle exception
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		return list;
	}

}
