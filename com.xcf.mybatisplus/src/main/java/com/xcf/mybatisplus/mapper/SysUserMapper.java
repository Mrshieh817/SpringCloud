package com.xcf.mybatisplus.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xcf.mybatisplus.model.SysUser;
import com.xcf.mybatisplus.model.input.SysUserParam;

/** 
* @author xcf 
* @Date 创建时间：2021年4月25日 下午2:34:40 
*/
public interface SysUserMapper extends BaseMapper<SysUser>{
	/**
	 * 获取XML数据信息
	 * @param model
	 * @return
	 */
	List<SysUser> getUser(Page<?> pa, @Param("params") SysUserParam model);
	
	/**
	 * 调用存储过程
	 * @return
	 */
	Integer getCallproduce();
}
