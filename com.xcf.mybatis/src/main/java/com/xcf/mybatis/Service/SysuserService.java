package com.xcf.mybatis.Service;

import java.util.List;

import com.xcf.mybatis.Core.SysUser;

/**
* @author 作者:大飞
* @version 创建时间：2019年10月14日 下午2:15:26
* 类说明
*/

public interface SysuserService {
	List<SysUser> Getuserlist(SysUser model);
}
