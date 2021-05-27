package com.xcf.mybatis.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xcf.mybatis.Core.SysUser;

import tk.mybatis.mapper.common.Mapper;

/**
* @author 作者:大飞
* @version 创建时间：2019年9月6日 下午1:44:30
* 类说明
*/

public interface SysUserMapper extends Mapper<SysUser> {
	/**
	 * 根据id查询用户信息
	 * @param id
	 * @return
	 */
  List<SysUser> getuserinfobyid(@Param("id") Integer id);
  
  int deleteSysUserById(@Param("id")String id);
}
