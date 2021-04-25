package com.xcf.mybatisplus.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xcf.mybatisplus.model.SysUser;

/** 
* @author xcf 
* @Date 创建时间：2021年4月25日 下午2:34:40 
*/
public interface SysUserMapper extends BaseMapper<SysUser>{
	List<SysUser> getUser(@Param("params") SysUser model);
}
